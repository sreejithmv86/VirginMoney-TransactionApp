package com.virginmoney.coding.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionController {

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<Object> exception(CategoryNotFoundException exception) {
        return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidInputDateException.class)
    public ResponseEntity<Object> exception(InvalidInputDateException exception) {
        return new ResponseEntity<>("Invalid Input date provided", HttpStatus.BAD_REQUEST);
    }
}
