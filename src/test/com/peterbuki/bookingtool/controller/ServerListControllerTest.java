package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.Application;
import com.peterbuki.bookingtool.model.ServerDto;
import com.peterbuki.bookingtool.service.ServerService;
import com.peterbuki.bookingtool.util.Utility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerListControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ServerService serverService;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void findByHostname_hostNotFound() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/findByHostname", "hostname=unknown"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No server was found by name 'unknown'", response.getBody());
    }

    @Test
    public void findByHostname_hostWithPrettyFormatting() {

        serverService.add(Utility.generateServer("test1"));

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/findByHostname", "hostname=test1"),
                HttpMethod.GET, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedBody =
                        "+----------------------------------Test v1.0-----------------------------------+\n" +
                        "|                        IP: 10.11.12.0 Hostname: test1                        |\n" +
                        "|                    Team: A-Team Contact: info@contact.com                    |\n" +
                        "|                   Cluster: Main Cluster Usage: CI cluster                    |\n" +
                        "|                       Component: Acme Release: v12.0.1                       |\n" +
                        "|                          Booking from null to null                           |\n" +
                        "+------------------------------------------------------------------------------+\n";
        assertEquals(expectedBody, response.getBody());
    }


    @Test
    public void updateCurrentHost_NullHost() {
        ServerDto server = new ServerDto();
        server.setUsage("New usage");
        HttpEntity<ServerDto> entity = new HttpEntity<>(server, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/updateUsageByHostname"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Missing hostname", response.getBody());
    }

    @Test
    public void updateCurrentHost_UnknownHost() {
        ServerDto server = Utility.generateServer("test2");
        server.setUsage("New usage");

        HttpEntity<ServerDto> entity = new HttpEntity<>(server, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/updateUsageByHostname"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(String.format("Host '%s' not found in the database.", server.getHostname()), response.getBody());
    }

    @Test
    public void updateCurrentHost_HostUpdated() {
        ServerDto server = Utility.generateServer("test4");
        serverService.add(server);

        server.setUsage("New usage");
        HttpEntity<ServerDto> entity = new HttpEntity<>(server, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/updateUsageByHostname"),
                HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                String.format("Updated host '%s' usage to '%s'.", server.getHostname(), server.getUsage()),
                response.getBody());

        ServerDto fromDb = serverService.findByHostname(server.getHostname());
        assertEquals("New usage", fromDb.getUsage());
    }


    private String createURLWithPort(String uri) {
        return createURLWithPort(uri, "");
    }

    private String createURLWithPort(String uri, String params) {
        return String.format("http://localhost:%d/%s?%s", port, uri, params);
    }


}