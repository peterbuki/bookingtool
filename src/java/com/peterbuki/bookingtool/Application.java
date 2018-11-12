package com.peterbuki.bookingtool;

import com.peterbuki.bookingtool.service.ServerService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private ServerService serverService;

    @Theme("valo")
    @SpringUI(path = "/hello")
    public static class VaadinUI extends UI {


        @Override
        protected void init(VaadinRequest request) {
            setContent(new Label("Hello!"));
        }

    }
}