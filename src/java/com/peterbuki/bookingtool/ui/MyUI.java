package com.peterbuki.bookingtool.ui;

import com.peterbuki.bookingtool.model.Server;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;

@Theme("valo")
@SpringUI(path = "/ui")
public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        VerticalSplitPanel mainPanel = new VerticalSplitPanel();
        mainPanel.setSplitPosition(50, Unit.PIXELS);

        mainPanel.setFirstComponent(new Label("Hello world!"));
        mainPanel.setSecondComponent(new DtoLayout(new Server()));

        setContent(mainPanel);


    }



}