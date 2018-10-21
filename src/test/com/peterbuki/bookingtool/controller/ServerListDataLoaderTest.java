package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class ServerListDataLoaderTest {

    @Test
    public void testLoadTestData() throws Exception{
        ServerService serverService = Mockito.mock(ServerService.class);

        ServerListDataLoader loader = new ServerListDataLoader();
        loader.setServerService(serverService);

        ArgumentCaptor<List> argument = ArgumentCaptor.forClass(List.class);

        startHttpServer();

        loader.setLoaderUrl("http://localhost:46765/test");

        loader.loadTestData();
        Mockito.verify(serverService).addAll(argument.capture());

        List<Server> resultServers = (List<Server>) argument.getValue();
        System.out.println("Count: " + resultServers.size() + resultServers.get(0));

    }



    public static void startHttpServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(46765), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "Type, Host, IP Address, Contact, Team, Start, End, Cluster, Component, Release, Usage\n" +
            "HP DL380 Gen7, server1, 172.16.1.31, Phil, A-Team, 2017-09-01 00:00:00, 2018-11-30 23:59:00, Big Cluster, bARK!, v1.2.04, Usage is very high!\n";
            t.getResponseHeaders().add("Content-Type", "text/csv");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }



}