package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.ServerDto;
import com.peterbuki.bookingtool.service.ServerService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

public class ServerListDataLoaderTest {

    private ServerService serverService;
    private ServerListDataLoader loader;
    private ArgumentCaptor<List> argument;
    private static HttpServer server;
    private ServerDto expectedServer;

    @Before
    public void setUp() throws Exception {
        startHttpServer();
        serverService = Mockito.mock(ServerService.class);

        loader = new ServerListDataLoader();
        loader.setServerService(serverService);
        argument = ArgumentCaptor.forClass(List.class);

        buildExpectedServer();
    }

    @After
    public void tearDown() throws Exception {
        server.stop(0);
    }

    @Test
    @Ignore
    public void testLoadTestDataWithCommaInContact() throws Exception {
        loader.setLoaderUrl("http://localhost:46765/contactcomma");
        loader.loadTestData();
        Mockito.verify(serverService).addAll(argument.capture());
        List<ServerDto> resultServers = (List<ServerDto>) argument.getValue();
        System.out.println(resultServers.get(0));
        assertEquals(1, resultServers.size());
        assertEquals(expectedServer, resultServers.get(0));
    }

    @Test
    public void testLoadTestDataWithCommaInUsage() throws Exception {
        loader.setLoaderUrl("http://localhost:46765/usagecomma");
        loader.loadTestData();
        Mockito.verify(serverService).addAll(argument.capture());
        List<ServerDto> resultServers = (List<ServerDto>) argument.getValue();
        assertEquals(1, resultServers.size());
        assertEquals(expectedServer, resultServers.get(0));
    }

    @Test
    public void testLoadTestData() throws Exception {
        loader.setLoaderUrl("http://localhost:46765/oneline");
        loader.loadTestData();

        Mockito.verify(serverService).addAll(argument.capture());
        List<ServerDto> resultServers = (List<ServerDto>) argument.getValue();

        assertEquals(2, resultServers.size());
        ServerDto resultServer = resultServers.get(1);

        assertEquals(expectedServer, resultServer);
    }

    private void buildExpectedServer() {
        expectedServer = new ServerDto();
        expectedServer.setType("Dell PowerEdge");
        expectedServer.setHostname("server2");
        expectedServer.setIp("10.10.10.1");
        expectedServer.setContact("Collins");
        expectedServer.setTeam("B-Team");
        expectedServer.setStart(LocalDateTime.of(2018, 9, 1, 11, 23, 45));
        expectedServer.setEnd(LocalDateTime.of(2018, 11, 30, 23, 59, 0));
        expectedServer.setCluster("Big Cluster");
        expectedServer.setComponent("Barf!");
        expectedServer.setRelease("v1.2.03");
        expectedServer.setUsage("Testing");
    }


    public static void startHttpServer() throws Exception {
        server = HttpServer.create(new InetSocketAddress(46765), 0);
        server.createContext("/oneline", new SuccessfulLoadHandler());
        server.createContext("/contactcomma", new ContactContainsCommaHandler());
        server.createContext("/usagecomma", new UsageContainsCommaHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class SuccessfulLoadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Type, Host, IP Address, Contact, Team, Start, End, Cluster, Component, Release, Usage\n" +
                    "HP DL380 Gen7, server1, 172.16.1.31, Phil, A-Team, 2017-09-01 00:00:00, 2018-11-30 23:59:00, Big Cluster, bARK!, v1.2.04, Usage is very high!\n" +
                    "Dell PowerEdge, server2, 10.10.10.1, Collins, B-Team, 2018-09-01 11:23:45, 2018-11-30 23:59:00, Big Cluster, Barf!, v1.2.03, Testing\n";
            t.getResponseHeaders().add("Content-Type", "text/csv");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ContactContainsCommaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Type, Host, IP Address, Contact, Team, Start, End, Cluster, Component, Release, Usage\n" +
                    "Dell PowerEdge, server2, 10.10.10.1, Phil, Collins, B-Team, 2018-09-01 11:23:45, 2018-11-30 23:59:00, Big Cluster, Barf!, v1.2.03, Testing\n";
            t.getResponseHeaders().add("Content-Type", "text/csv");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class UsageContainsCommaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Type, Host, IP Address, Contact, Team, Start, End, Cluster, Component, Release, Usage\n" +
                    "Dell PowerEdge, server2, 10.10.10.1, Collins, B-Team, 2018-09-01 11:23:45, 2018-11-30 23:59:00, Big Cluster, Barf!, v1.2.03, Testing, etc, etc\n";
            t.getResponseHeaders().add("Content-Type", "text/csv");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


}