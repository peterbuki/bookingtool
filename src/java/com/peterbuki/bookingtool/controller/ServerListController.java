package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerListController {

    @Autowired
    private ServerService serverService;

    @Value("${loader.url}")
    private String url;

    @RequestMapping("/findByHostname")
    public Server findByHostname(@RequestParam(value="hostname") String hostname) {
        return serverService.findByHostname(hostname);
    }

    @RequestMapping("/count")
    public Long countServers() {
        return serverService.count();
    }

    @RequestMapping("/url")
    public String getUrl()
    {
        return url;
    }



}
