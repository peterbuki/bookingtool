package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import com.peterbuki.bookingtool.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class ServerListController {

    @Value("${bookingtool.info}")
    public static String HEADER_INFO = "Test v1.0";

    @Autowired
    private ServerService serverService;

    @Value("${loader.url}")
    private String url;

    @RequestMapping(
            value = "/findByHostname",
            method = RequestMethod.GET,
            produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> findByHostname(@RequestParam(value = "hostname") String hostname,
                                                 @RequestParam(value = "columns", defaultValue = "80") int columns) {
        ResponseEntity<String> result;
        try {
            Server server = serverService.findByHostname(hostname);
            result = new ResponseEntity<>(Utility.serverFormatter(server, columns), HttpStatus.OK);
        } catch (NoResultException e) {
            result = new ResponseEntity<>(
                    String.format("No server was found by name '%s'", hostname),
                    HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @RequestMapping("/count")
    public Long countServers() {
        return serverService.count();
    }

    @RequestMapping("addTestData")
    public void addTestData() throws UnknownHostException {
        serverService.add(Utility.generateServer(InetAddress.getLocalHost().getHostName()));
    }

    @RequestMapping("/url")
    public String getUrl() {
        return url;
    }

    @RequestMapping(value = "/updateUsageByHostname", method = RequestMethod.POST)
    public ResponseEntity<String> updateCurrentHost(Server server) {

        if (server.getHostname() == null) {
            return new ResponseEntity<>("Missing hostname", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            serverService.updateByHostname(server);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
