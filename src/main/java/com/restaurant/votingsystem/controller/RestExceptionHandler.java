package com.restaurant.votingsystem.controller;

import com.restaurant.votingsystem.util.exception.ErrorInfo;
import com.restaurant.votingsystem.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorInfo resourceNotFoundException(NotFoundException exception, HttpServletRequest request) {
        return new ErrorInfo(request.getRequestURL(), exception.getMessage());
    }
}
