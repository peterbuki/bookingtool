package com.peterbuki.bookingtool.ui;

import com.peterbuki.bookingtool.model.Dto;
import com.peterbuki.bookingtool.service.Service;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class DtoLayout<T extends Dto> extends CustomComponent {
    @Autowired
    void setApplicationContext(ApplicationContext ctx) {
        // TODO: should use ServiceLocator pattern
        service = (Service<T>) ctx.getBean((dto.getServiceClass()));
    }

    private Service<T> service;
    private T dto;

    private List<TextField> fields = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(CustomTextField.class);

    public DtoLayout(T dto) {
        this.dto = dto;

        FormLayout rootComponent = new FormLayout();

        for (Field field : dto.getClass().getDeclaredFields()) {

            TextField textField = new TextField(StringUtils.capitalize(field.getName()));
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

        updateDtoWithFieldValues();
        dto = service.findByExample(dto);
        updateFieldsWithDtoValues();
    }

    private void updateFieldsWithDtoValues() {
        for (TextField field : fields) {
            Method getter = null;
            try {
                getter = dto.getClass().getMethod("get" + StringUtils.capitalize(field.getCaption()));
                String value = (String) getter.invoke(dto);
                field.setValue(null == value ? "" : value);
            } catch (ClassCastException e) {
                logger.warn("No getter with proper type", e);
            } catch (NoSuchMethodException e) {
                logger.warn("Missing getter", e);
            } catch (IllegalAccessException e) {
                logger.warn("Yikes!", e);
            } catch (InvocationTargetException e) {
                logger.warn("Auch!", e);
            }
        }
    }

    private void updateDtoWithFieldValues() {
        for (TextField field : fields) {
            try {
                Method setter = dto.getClass().getMethod("set" + StringUtils.capitalize(field.getCaption()), String.class);
                setter.invoke(dto, field.getValue());
                logger.debug(String.format("Set field '%s' to '%s'", field.getCaption(), field.getValue()));
            } catch (ClassCastException e) {
                logger.warn("No setter with proper type", e);
            } catch (NoSuchMethodException e) {
                logger.warn("Missing setter", e);
            } catch (IllegalAccessException e) {
                logger.warn("Yikes!", e);
            } catch (InvocationTargetException e) {
                logger.warn("Auch!", e);
            }
        }
    }


}
