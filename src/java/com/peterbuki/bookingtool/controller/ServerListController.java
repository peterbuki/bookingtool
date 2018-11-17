package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import com.peterbuki.bookingtool.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;

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
            produces="text/plain;charset=UTF-8")
    public ResponseEntity<String> findByHostname(@RequestParam(value = "hostname") String hostname,
                                                 @RequestParam(value = "columns", defaultValue = "80") int columns) {
        ResponseEntity<String> result;
        try {
            Server server = serverService.findByHostname(hostname);
            result = new ResponseEntity<>(Utility.serverFormatter(server, columns), HttpStatus.OK);
        } catch (NoResultException e) {
            result = new ResponseEntity<>(
                    String.format("No server was found by name '%s'", hostname),
                    HttpStatus.NOT_FOUND) ;
        }
        return result;
    }

    @RequestMapping("/count")
    public Long countServers() {
        return serverService.count();
    }

    @RequestMapping("/url")
    public String getUrl() {
        return url;
    }

    @RequestMapping("/update")
    public String updateCurrentHost(Server server) {
        return "Aloha!";
    }


}
