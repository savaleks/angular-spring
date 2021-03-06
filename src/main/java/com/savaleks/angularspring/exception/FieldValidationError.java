package com.savaleks.angularspring.exception;

import java.awt.*;

public class FieldValidationError {

    private String field;
    private String message;
    private TrayIcon.MessageType type;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TrayIcon.MessageType getType() {
        return type;
    }

    public void setType(TrayIcon.MessageType type) {
        this.type = type;
    }
}
