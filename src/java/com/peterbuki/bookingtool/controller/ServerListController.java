package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.ServerDto;
import com.peterbuki.bookingtool.service.ServerService;
import com.peterbuki.bookingtool.util.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class ServerListController {
    private static final Logger logger = LoggerFactory.getLogger(ServerListDataLoader.class);

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
            ServerDto server = serverService.findByHostname(hostname);
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
    public ResponseEntity<String> updateUsageByHostname(@RequestBody ServerDto server) {

        if (server.getHostname() == null) {
            logger.warn("Update failed, missing hostname.");
            return new ResponseEntity<>("Missing hostname", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int rowsUpdated = serverService.update(server);
        if (rowsUpdated > 0) {
            logger.debug(
                    String.format("Updated host '%s', set usage to '%s'.",
                            server.getHostname(),
                            server.getUsage()));
            return new ResponseEntity<>(
                    String.format("Updated host '%s' usage to '%s'.", server.getHostname(), server.getUsage()),
                    HttpStatus.OK);
        } else {
            logger.error("No rows updated");
            return new ResponseEntity<>(String.format("Host '%s' not found in the database.", server.getHostname()),
                    HttpStatus.NOT_FOUND);
        }
    }
}


