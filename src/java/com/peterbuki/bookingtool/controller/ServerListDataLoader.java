package com.peterbuki.bookingtool.controller;

import com.peterbuki.bookingtool.model.Server;
import com.peterbuki.bookingtool.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServerListDataLoader {
    private ServerService serverService;
    private String url;

    @Autowired
    public void setServerService(ServerService serverService)
    {
        this.serverService = serverService;
    }

    @Value("${loader.url}")
    public void setLoaderUrl(String url)
    {
        this.url = url;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadTestData() {

        Server server = new Server();
        server.setHostname("host1");
        server.setId(1);
        serverService.add(server);

        server.setHostname("something");
        server.setId(2);
        serverService.add(server);

        server.setHostname("else");
        server.setId(3);
        serverService.add(server);
    }


}
