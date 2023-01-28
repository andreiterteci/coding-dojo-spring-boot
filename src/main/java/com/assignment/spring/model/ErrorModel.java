package com.assignment.spring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorModel {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private String message;

    private ErrorModel() {
        timestamp = LocalDateTime.now();
    }

    public ErrorModel(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }
}
