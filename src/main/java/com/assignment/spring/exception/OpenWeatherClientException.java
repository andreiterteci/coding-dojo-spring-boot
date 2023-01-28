package com.assignment.spring.exception;

public class OpenWeatherClientException extends RuntimeException {

    public OpenWeatherClientException(String message) {
        super(message);
    }
}
