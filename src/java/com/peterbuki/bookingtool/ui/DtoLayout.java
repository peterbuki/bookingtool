package com.peterbuki.bookingtool.ui;

import com.peterbuki.bookingtool.model.Dto;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class DtoLayout<T extends Dto> extends CustomComponent {


    private List<CustomTextField> fields = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(CustomTextField.class);

    public DtoLayout(T t) {
        FormLayout rootComponent = new FormLayout();

        for (Field field : t.getClass().getDeclaredFields()) {

            CustomTextField textField = new CustomTextField(StringUtils.capitalize(field.getName()));
            fields.add(textField);
            rootComponent.addComponent(textField);
        }
        // add buttons
        rootComponent.addComponent(new Button("Search", listener -> search()));
        rootComponent.addComponent(new Button("Save", listener -> save()));
        setCompositionRoot(rootComponent);
    }

    private void save() {
        logger.debug("Save button pressed!");
    }

    private void search() {
        logger.info("Search button clicked!");
    }

}
