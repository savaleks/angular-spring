package com.savaleks.angularspring.exception;

import com.savaleks.angularspring.dto.UserDTO;

public class CustomErrorType extends UserDTO {

    private String errorMessage;

    // initialized inside the constructor of this class
    public CustomErrorType(final String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
