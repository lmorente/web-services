package es.andaluces.ourCode.controllers;

import es.andaluces.ourCode.exceptions.DeleteUserException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityExistsException;
import java.util.NoSuchElementException;

@ControllerAdvice
class HandlerController {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public  ResponseEntity<String> handleNotFound() {
        return new ResponseEntity<>("Element has not been found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> handleEntiyExists() {
        return new ResponseEntity<>("Element already exists", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteUserException.class)
    public ResponseEntity<String> handleDeleteUser() {
        return new ResponseEntity<>("User cannot be deleted", HttpStatus.BAD_REQUEST);
    }
}