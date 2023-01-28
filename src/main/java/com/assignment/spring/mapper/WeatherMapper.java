package com.assignment.spring.mapper;

import com.assignment.spring.entity.City;
import com.assignment.spring.entity.Weather;
import com.assignment.spring.model.WeatherResponseModel;
import com.assignment.spring.model.openweather.OpenWeatherResponse;

public final class WeatherMapper {

    private WeatherMapper() {
    }

    public static Weather toEntity(final OpenWeatherResponse response, final City city) {
        return Weather.builder()
                .city(city)
                .temperature(response.getMain().getTemp())
                .build();
    }

    public static WeatherResponseModel toModel(final Weather weather) {
        return WeatherResponseModel.builder()
                .city(weather.getCity().getName())
                .country(weather.getCity().getCountry().getName())
                .temperature(weather.getTemperature())
                .build();
    }
}
