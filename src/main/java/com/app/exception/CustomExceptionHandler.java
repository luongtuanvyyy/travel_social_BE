package com.app.exception;

import com.app.payload.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResponse handleInvalidDataType(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid data type for request parameter: " + ex.getName();
        return new APIResponse(errorMessage);
    }
}