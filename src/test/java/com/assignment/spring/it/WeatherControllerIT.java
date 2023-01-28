package com.assignment.spring.it;


import com.assignment.spring.model.WeatherResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class WeatherControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    private PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:11.5")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa");

    @BeforeEach
    public void setUp() {
        postgres.start();
    }

    @AfterEach
    public void tearDown() {
        postgres.stop();
    }

    @Test
    public void shouldRetrieveWeatherByCity() {
        String city = "London";
        ResponseEntity<WeatherResponseModel> response = restTemplate.getForEntity("http://localhost:" + port + "/weather?city=" + city, WeatherResponseModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertEquals(response.getBody().getCity(), city);
    }

    @Test
    public void shouldReturnBadRequestForEmptyCity() {
        String city = "";
        ResponseEntity<WeatherResponseModel> response = restTemplate.getForEntity("http://localhost:" + port + "/weather?city=" + city, WeatherResponseModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    public void shouldReturnBadRequestForUnknownCity() {
        String city = "test_city";
        ResponseEntity<WeatherResponseModel> response = restTemplate.getForEntity("http://localhost:" + port + "/weather?city=" + city, WeatherResponseModel.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
    }
}


