package com.assignment.spring.controller;

import com.assignment.spring.model.ErrorModel;
import com.assignment.spring.model.WeatherResponseModel;
import com.assignment.spring.service.WeatherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
@Validated
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    @ApiOperation(value = "Retrieve the weather for a given city", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Weather fetched successfully", response = WeatherResponseModel.class),
            @ApiResponse(code = 400, message = "Invalid city name provided", response = ErrorModel.class),
            @ApiResponse(code = 404, message = "City not found", response = ErrorModel.class),
            @ApiResponse(code = 500, message = "An error occurred while fetching data from OpenWeather", response = ErrorModel.class)
    })
    public ResponseEntity<WeatherResponseModel> retrieveWeatherByCity(@RequestParam("city") @NotBlank(message = "City cannot be null or blank!") final String city) {
        return ResponseEntity.ok(weatherService.retrieveWeatherAndStoreResponse(city));
    }
}
