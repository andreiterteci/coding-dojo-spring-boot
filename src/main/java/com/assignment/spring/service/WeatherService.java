package com.assignment.spring.service;

import com.assignment.spring.entity.City;
import com.assignment.spring.entity.Country;
import com.assignment.spring.entity.Weather;
import com.assignment.spring.mapper.WeatherMapper;
import com.assignment.spring.model.WeatherResponseModel;
import com.assignment.spring.model.openweather.OpenWeatherResponse;
import com.assignment.spring.repository.CityRepository;
import com.assignment.spring.repository.CountryRepository;
import com.assignment.spring.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;
    private final RestTemplate restTemplate;
    @Value("${openweather.apiKey}")
    private final String apiKey;
    @Value("${openweather.endpoints.currentWeather}")
    private final String apiUrl;

    @Transactional
    public WeatherResponseModel retrieveWeatherAndStoreResponse(final String city) {
        log.info("[WeatherService] - Retrieving weather for city {}", city);
        Map<String, String> urlParams = new HashMap<>();
        urlParams.put("city", city);
        urlParams.put("apiKey", apiKey);
        OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(apiUrl, OpenWeatherResponse.class, urlParams);
        Weather savedWeather = storeData(openWeatherResponse);
        return WeatherMapper.toModel(savedWeather);
    }

    private Weather storeData(final OpenWeatherResponse response) {
        log.debug("[WeatherService] - Storing OpenWeather response {}", response);
        final City city = retrieveOrCreateCity(response);
        return weatherRepository.save(WeatherMapper.toEntity(response, city));
    }

    private City retrieveOrCreateCity(OpenWeatherResponse response) {
        return cityRepository.findByNameIgnoreCase(response.getName()).orElseGet(() -> {
            log.debug("[WeatherService] - Storing a new city: {}", response.getName());
            final Country country = retrieveOrCreateCountry(response);
            return City.builder()
                    .name(response.getName())
                    .country(country)
                    .build();
        });
    }

    private Country retrieveOrCreateCountry(OpenWeatherResponse response) {
        return countryRepository.findByName(response.getSys().getCountry())
                .orElseGet(() -> {
                            log.debug("[WeatherService] - Storing a new country: {}", response.getSys().getCountry());
                            return Country.builder()
                                    .name(response.getSys().getCountry())
                                    .build();
                        }
                );
    }
}
