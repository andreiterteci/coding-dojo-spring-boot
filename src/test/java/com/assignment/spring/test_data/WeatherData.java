package com.assignment.spring.test_data;

import com.assignment.spring.entity.City;
import com.assignment.spring.entity.Country;
import com.assignment.spring.model.openweather.Main;
import com.assignment.spring.model.openweather.OpenWeatherResponse;
import com.assignment.spring.model.openweather.Sys;

public class WeatherData {

    public static OpenWeatherResponse getOpenWeatherResponse(final String city, final String country) {
        OpenWeatherResponse openWeatherResponse = new OpenWeatherResponse();
        openWeatherResponse.setName(city);
        openWeatherResponse.setSys(new Sys());
        openWeatherResponse.getSys().setCountry(country);
        openWeatherResponse.setMain(new Main());
        openWeatherResponse.getMain().setTemp(250.51);
        return openWeatherResponse;
    }

    public static City getCity(final String name, final String country) {
        return City.builder()
                .name(name)
                .country(getCountry(country))
                .build();
    }

    public static Country getCountry(final String name) {
        return Country.builder()
                .name(name)
                .build();
    }
}
