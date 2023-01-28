package com.assignment.spring.service;

import com.assignment.spring.entity.Weather;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.model.WeatherResponseModel;
import com.assignment.spring.model.openweather.OpenWeatherResponse;
import com.assignment.spring.repository.CityRepository;
import com.assignment.spring.repository.CountryRepository;
import com.assignment.spring.repository.WeatherRepository;
import com.assignment.spring.test_data.WeatherData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class WeatherServiceTest {

    private static final String NEW_YORK = "New York";
    private static final String US = "United States";
    @Mock
    private WeatherRepository weatherRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private RestTemplate restTemplate;

    private WeatherService weatherService;

    private Weather expectedWeather;
    private OpenWeatherResponse openWeatherResponse;

    @BeforeEach
    public void setUp() {
        weatherService = new WeatherService(weatherRepository, cityRepository, countryRepository, restTemplate, "test_key", "test_url");

        expectedWeather = new Weather();
        expectedWeather.setCity(WeatherData.getCity(NEW_YORK, US));

        openWeatherResponse = WeatherData.getOpenWeatherResponse(NEW_YORK, US);

        when(restTemplate.getForObject(anyString(), any(), anyMap()))
                .thenReturn(openWeatherResponse);
        when(weatherRepository.save(WeatherMapper.toEntity(openWeatherResponse, expectedWeather.getCity())))
                .thenReturn(expectedWeather);
        when(cityRepository.findByNameIgnoreCase(openWeatherResponse.getName()))
                .thenReturn(Optional.of(WeatherData.getCity(NEW_YORK, US)));
        when(countryRepository.findByName(openWeatherResponse.getSys().getCountry()))
                .thenReturn(Optional.of(WeatherData.getCountry(US)));
    }

    @Test
    public void shouldStoreResponseWithoutSavingNewCityAndCountry() {
        WeatherResponseModel actualWeatherResponseModel = weatherService.retrieveWeatherAndStoreResponse(NEW_YORK);
        WeatherResponseModel expectedWeatherResponseModel = WeatherMapper.toModel(expectedWeather);
        assertEquals(expectedWeatherResponseModel, actualWeatherResponseModel);
    }

    @Test
    public void shouldStoreResponseWithSavingNewCityAndCountry() {
        when(cityRepository.findByNameIgnoreCase(openWeatherResponse.getName()))
                .thenReturn(Optional.empty());
        when(countryRepository.findByName(openWeatherResponse.getSys().getCountry()))
                .thenReturn(Optional.empty());
        WeatherResponseModel actualWeatherResponseModel = weatherService.retrieveWeatherAndStoreResponse(NEW_YORK);
        WeatherResponseModel expectedWeatherResponseModel = WeatherMapper.toModel(expectedWeather);
        assertEquals(expectedWeatherResponseModel, actualWeatherResponseModel);
        assertNull(expectedWeather.getCity().getId());
        assertNull(expectedWeather.getCity().getCountry().getId());
    }

    @Test
    public void shouldHandleExceptionThrownWhenCallingApi() {
        when(restTemplate.getForObject(anyString(), any(), anyMap()))
                .thenThrow(HttpClientErrorException.class);
        Assertions.assertThrows(HttpClientErrorException.class,
                () -> weatherService.retrieveWeatherAndStoreResponse(NEW_YORK));
    }
}

