package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import com.peterbuki.bookingtool.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerListController {

    @Value("${bookingtool.info}")
    public static String HEADER_INFO = "Test v1.0";

    @Autowired
    private ServerService serverService;

    @Value("${loader.url}")
    private String url;

    @RequestMapping("/findByHostname")
    public String findByHostname(@RequestParam(value = "hostname") String hostname,
                                 @RequestParam(value = "columns", defaultValue = "80") int columns) {
        Server server = serverService.findByHostname(hostname);
        return Utility.serverFormatter(server, columns);
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
