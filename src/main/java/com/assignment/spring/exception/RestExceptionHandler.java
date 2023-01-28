package com.assignment.spring.exception;

import com.assignment.spring.model.ErrorModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CityNotFoundException.class)
    protected ResponseEntity<Object> handleCityNotFoundException(CityNotFoundException ex) {
        log.error("Encountered CityNotFoundException: {}", ex.getMessage());
        return buildErrorResponse(new ErrorModel(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(OpenWeatherClientException.class)
    protected ResponseEntity<Object> handleOpenWeatherClientException(OpenWeatherClientException ex) {
        log.error("Encountered OpenWeatherClientException: {}", ex.getMessage());
        return buildErrorResponse(new ErrorModel(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(OpenWeatherServerException.class)
    protected ResponseEntity<Object> handleOpenWeatherServerException(OpenWeatherServerException ex) {
        log.error("Encountered OpenWeatherServerException: {}", ex.getMessage());
        return buildErrorResponse(new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleValidationError(ConstraintViolationException ex) {
        log.error("Encountered ConstraintViolationException: {}", ex.getMessage());
        return buildErrorResponse(new ErrorModel(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private ResponseEntity<Object> buildErrorResponse(final ErrorModel apiError) {
        return ResponseEntity.status(apiError.getStatus())
                .body(apiError);
    }
}
