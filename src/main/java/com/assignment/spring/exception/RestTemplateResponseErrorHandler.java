package com.assignment.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 *  This handler is not necessary for handling the errors thrown by the API.
 *  Instead, we could have defined handlers in the RestExceptionHandler for the Client and Server Exceptions.
 *  The advantage of this implementation is that it provides a lot of flexibility,
 *  allowing us to treat exceptions differently for each case, based on the status code of the response (e.g. 401, 403, 500 etc.)
 **/
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new CityNotFoundException("Couldn't find the provided city. Make sure the name is typed correctly.");
            }
            throw new OpenWeatherClientException("Received a client error while fetching data from OpenWeather: " + httpResponse.getStatusText());
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw new OpenWeatherServerException("Received a server error while fetching data from OpenWeather");
        }
    }
}
