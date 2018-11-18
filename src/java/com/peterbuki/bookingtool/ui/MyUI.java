package com.peterbuki.bookingtool.ui;

import com.peterbuki.bookingtool.model.ServerDto;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Theme("valo")
@SpringUI(path = "/ui")
public class MyUI extends UI {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    protected void init(VaadinRequest request) {
        VerticalSplitPanel mainPanel = new VerticalSplitPanel();
        mainPanel.setSplitPosition(50, Unit.PIXELS);

        mainPanel.setFirstComponent(new Label("Hello world!"));
        DtoLayout serverLayout = new DtoLayout(new ServerDto());
        serverLayout.setApplicationContext(applicationContext);
        mainPanel.setSecondComponent(serverLayout);

        setContent(mainPanel);


    }



}