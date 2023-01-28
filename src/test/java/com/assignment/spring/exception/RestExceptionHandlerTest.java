package com.assignment.spring.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ConstraintViolationException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestExceptionHandlerTest {

    private final RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    public void testHandleOpenWeatherClientException() {
        OpenWeatherClientException ex = new OpenWeatherClientException("Invalid API key");
        ResponseEntity<Object> response = restExceptionHandler.handleOpenWeatherClientException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testHandleOpenWeatherServerException() {
        OpenWeatherServerException ex = new OpenWeatherServerException("Internal Server Error");
        ResponseEntity<Object> response = restExceptionHandler.handleOpenWeatherServerException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testValidationError() {
        ConstraintViolationException ex = new ConstraintViolationException(new HashSet<>());
        ResponseEntity<Object> response = restExceptionHandler.handleValidationError(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
