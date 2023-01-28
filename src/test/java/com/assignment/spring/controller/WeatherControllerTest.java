package com.assignment.spring.controller;

import com.assignment.spring.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class WeatherControllerTest {
    private static final String NEW_YORK = "New York";

    @Mock
    private WeatherService weatherService;

    private WeatherController weatherController;

    @BeforeEach
    public void setUp() {
        weatherController = new WeatherController(weatherService);
    }

    @Test
    public void shouldStoreResponseWithoutSavingNewCityAndCountry() {
        weatherController.retrieveWeatherByCity(NEW_YORK);
        verify(weatherService, timeout(1)).retrieveWeatherAndStoreResponse(NEW_YORK);
    }
}
