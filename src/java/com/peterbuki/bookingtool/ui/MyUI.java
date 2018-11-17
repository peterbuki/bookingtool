package com.peterbuki.bookingtool.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

public class MyUI {

    @Theme("valo")
    @SpringUI(path = "/ui")
    public static class VaadinUI extends UI {
        @Override
        protected void init(VaadinRequest request) {
            setContent(new Label("Hello World!"));
        }

    }
}
