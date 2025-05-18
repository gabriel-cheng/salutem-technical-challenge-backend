package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidItemCodeException extends Exception {

    public InvalidItemCodeException(String message) {
        super(message);
    }

}
