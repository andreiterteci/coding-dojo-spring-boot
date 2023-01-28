package com.assignment.spring.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class RestTemplateResponseErrorHandlerTest {

    @Mock
    private ClientHttpResponse httpResponse;

    private final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler = new RestTemplateResponseErrorHandler();

    @Test
    public void testHasError() throws IOException {
        when(httpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        assertTrue(restTemplateResponseErrorHandler.hasError(httpResponse));

        when(httpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
        assertFalse(restTemplateResponseErrorHandler.hasError(httpResponse));
    }

    @Test
    public void testHandleError_clientError() throws IOException {
        when(httpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(httpResponse.getStatusText()).thenReturn("Bad Request");
        assertThrows(OpenWeatherClientException.class, () -> restTemplateResponseErrorHandler.handleError(httpResponse));
    }

    @Test
    public void testHandleError_serverError() throws IOException {
        when(httpResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThrows(OpenWeatherServerException.class, () -> restTemplateResponseErrorHandler.handleError(httpResponse));
    }
}
