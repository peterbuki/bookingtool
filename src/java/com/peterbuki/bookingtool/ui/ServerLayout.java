package com.peterbuki.bookingtool.ui;

import com.peterbuki.bookingtool.model.ServerDto;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;

public class ServerLayout extends CustomComponent {

    public ServerLayout(ServerDto server) {
        FormLayout rootComponent = new FormLayout();

        CustomTextField type = new CustomTextField("Type");
        CustomTextField host = new CustomTextField("Hostname");
        CustomTextField ip = new CustomTextField("IP Address");
        CustomTextField contact = new CustomTextField("Contact");
        CustomTextField team = new CustomTextField("Team");
        CustomTextField start = new CustomTextField("Start");
        CustomTextField end = new CustomTextField("End");
        CustomTextField cluster = new CustomTextField("Cluster");
        CustomTextField component = new CustomTextField("Component");
        CustomTextField release = new CustomTextField("Release");
        CustomTextField usage = new CustomTextField("Usage");

        rootComponent.addComponents(type, host, ip, contact, team, start, end, cluster, component, release, usage);
        setCompositionRoot(rootComponent);
    }

}
