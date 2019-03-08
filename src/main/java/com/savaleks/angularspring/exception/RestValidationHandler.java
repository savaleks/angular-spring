package com.savaleks.angularspring.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestValidationHandler {

    private MessageSource messageSource;

    @Autowired
    public RestValidationHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // method to handle validation error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FieldValidationErrorDetails> handleValidationError(
            MethodArgumentNotValidException notValidException, HttpServletRequest request){
        FieldValidationErrorDetails fieldValidationErrorDetails = new FieldValidationErrorDetails();
        fieldValidationErrorDetails.setError_timeStamp(new Date().getTime());
        fieldValidationErrorDetails.setError_status(HttpStatus.BAD_REQUEST.value());
        fieldValidationErrorDetails.setError_title("Field Validation Error");
        fieldValidationErrorDetails.setError_details("Input Field Validation Failed");
        fieldValidationErrorDetails.setError_developer_Message(notValidException.getClass().getName());
        fieldValidationErrorDetails.setError_path(request.getRequestURI());

        BindingResult result = notValidException.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        for (FieldError error : fieldErrors) {
            FieldValidationError fError = processFieldError(error);
            List<FieldValidationError> fieldValidationErrorList = fieldValidationErrorDetails.getErrors().get(error.getField());
            if (fieldValidationErrorList == null){
                fieldValidationErrorList = new ArrayList<FieldValidationError>();
            }
            fieldValidationErrorList.add(fError);
            fieldValidationErrorDetails.getErrors().put(error.getField(), fieldValidationErrorList);
        }
        return new ResponseEntity<FieldValidationErrorDetails>(fieldValidationErrorDetails, HttpStatus.BAD_REQUEST);
    }

    // method to process field error
    private FieldValidationError processFieldError(final FieldError error){
        FieldValidationError fieldValidationError = new FieldValidationError();
        if (error != null){
            // retrieving messages from properties file based on currentLocale
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = messageSource.getMessage(error.getDefaultMessage(),null, currentLocale);

            fieldValidationError.setField(error.getField());
            fieldValidationError.setType(TrayIcon.MessageType.ERROR);
            fieldValidationError.setMessage(msg);
        }
        return fieldValidationError;
    }
}
