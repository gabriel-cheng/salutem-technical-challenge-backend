package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidAdditionalIngredientException extends Exception {

    public InvalidAdditionalIngredientException(String message) {
        super(message);
    }

}
