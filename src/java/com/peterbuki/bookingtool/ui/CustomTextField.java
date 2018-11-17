package com.peterbuki.bookingtool.ui;

import com.vaadin.ui.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomTextField extends TextField {

    private static final Logger logger = LoggerFactory.getLogger(CustomTextField.class);

    private boolean dirty = false;
    private String hint;

    public CustomTextField(String hint) {
        super(hint);

        this.hint = hint;
        setValue(hint);
        // TODO: set style to gray
        addFocusListener(focus -> focusListener());
        addBlurListener(blur -> blurListener());
    }

    private void blurListener() {
        logger.debug("BlurListener: " + getValue());
        if ("".equals(getValue())) {
            setValue(hint);
        } else {
            dirty = true;
        }
    }

    private void focusListener() {
        logger.debug("FocusListener: " + getValue());
        if (!dirty) {
            setValue("");
        }
    }

}
