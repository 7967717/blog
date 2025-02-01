package org.example.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.blog.error.NotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ErrorController {

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(NotSupportedException.class)
    public ResponseEntity<String> handleNotSupportedException(
            NotSupportedException exception, HttpServletRequest request) {
        final var message = exception.getMessage();
        log.error("Could not process request: {} because the operation is not yet implemented. Exception message: {}", request, message);
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(message);
    }

}
