package com.assignment.spring.exception;

public class OpenWeatherServerException extends RuntimeException {

    public OpenWeatherServerException(String message) {
        super(message);
    }
}

