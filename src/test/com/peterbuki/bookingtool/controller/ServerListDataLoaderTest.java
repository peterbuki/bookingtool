package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class ServerListDataLoaderTest {

    @Test
    public void setUp() throws Exception{
        ServerService serverService = Mockito.mock(ServerService.class);

        ServerListDataLoader loader = new ServerListDataLoader();
        loader.setServerService(serverService);

        startHttpServer();

        loader.loadTestData();
        Mockito.verify(serverService, Mockito.times(3)).add(any(Server.class));
    }



    public static void startHttpServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "";
            t.getResponseHeaders().add("Content-Type", "text/csv");
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }



}