package com.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.AssertFalse;

@Getter
@AllArgsConstructor
public class BadRequestException extends RuntimeException {
    private String message;

}
