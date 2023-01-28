Weather App
---

This is a simple application displaying the current temperature in a given city, 
by fetching the result from [OpenWeather](https://openweathermap.org/). 

## Running the application

### Prerequisites
- **Java 11**
- **Maven 3.6.0**
- **PostgreSQL**
- **Docker**

### Environment Variables

In order to use the application you will need to set up a few environment variable:
- **OPENWEATHER_API_KEY**: the API key you will receive after creating
an account on [OpenWeather](https://openweathermap.org/).

If you do not want to set any environment variables, you will need to navigate to 
```src/main/resources/application.yml``` and ```docker-compose.yml``` and edit the following lines by replacing _YOUR_API_KEY_ with your actual api key:

```apiKey: YOUR_API_KEY```

```OPENWEATHER_API_KEY: YOUR_API_KEY```

### Running locally
In oder to run the application locally, you will need to follow this steps:
- Clone the github repository
- Create your PostgreSQL database and user

```
CREATE DATABASE lease_plan;
CREATE USER lease_plan WITH ENCRYPTED PASSWORD 'lease_plan';
ALTER DATABASE lease_plan OWNER TO lease_plan;
GRANT ALL PRIVILEGES ON DATABASE lease_plan TO lease_plan;
```

- Build the project

```
mvn clean install
```

- Run the application. Make sure that you are using the **local** profile
```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

If you are using a Windows system, you might need to use " when passing the arguments: 

```
mvn spring-boot:run -D"spring-boot.run.profiles"=local
```

Alternatively, you can import the project into a code editor (e.g. IntelliJ, Eclipse, etc.) and run it from there.

### Running using docker-compose

If you do not want to set up the project locally, you can use the docker compose file to run the application.
All you have to do is navigate to the root folder of the project and run the command:

```docker-compose up```

This will pull a postgreSQL database image, create the database and the roles, build the app and start it. 
It also has a volume, so data will be preserved if the process is stopped.

*Note: You will still need to set up the OPENWEATHER_API_KEY environment variable*

## Usage

After starting the app, it will run by default on your localhost on port 8080.
In order to access the weather endpoint, you can go to the following URL:

```
http://localhost:8080/weather?city=DESIRED_CITY
```

As an example, if you want to check the weather in Bucharest, you can go to:

```
http://localhost:8080/weather?city=Bucharest
```

Example response from the API:
```json
{
  "city":"Bucharest",
  "country":"RO",
  "temperature":273.54
}
```
### Restrictions

- The *city* parameter cannot be null or blank
- The *city* parameter must be a known city

If those conditions are not met, the API will send an error response. Example:
```json
{
  "status":"NOT_FOUND",
  "timestamp":"27-01-2023 01:59:25",
  "message":"Couldn't find the provided city. Make sure the name is typed correctly."
}
```
```json
{
  "status":"BAD_REQUEST",
  "timestamp":"27-01-2023 02:00:11",
  "message":"retrieveWeatherByCity.city: City cannot be null or blank!"
}
```

## Running tests

The application contains both unit and integration tests, as well as JaCoCo coverage reports.
You can run the tests by navigating to the root folder of the project and executing the following command:
```mvn clean verify```

This command will build the application, run tests and create JaCoCo coverage reports.

After the execution, you can check the coverage reports by navigating to
```/target/site/jacoco/index.html```

## Endpoint Documentation

The API endpoints are documented using [Swagger UI](http://localhost:8080/swagger-ui.html).

## Changelog

- Updated Spring Boot Version to 2.7.0
- Split the code into multiple layers, following the MVC pattern, to improve overall organization and maintainability of the codebase.
- Refactored the code to follow clean code principles to make the code more readable and maintainable.
- Added lombok to reduce boilerplate code and make the code more readable.
- Created multiple profile configurations (local, prod) to allow easy switching between different environments and use different configurations for each.
- Replaced hardcoded API keys with configurations to improve security and not expose sensitive data in the codebase.
- Configured database connections for each profile.
- Normalized database for a better data organization and performance.
- Added indexes to improve query performance.
- Replaced integer ids with random strings to improve security and not expose predictable id values.
- Implemented Flyway to manage and version the database.
- Implemented database auditing (created_at) for the weather table to track changes to data.
- Created a separate layer to return data to the UI (WeatherResponseModel) to improve security and not expose unnecessary information.
- Improved error handling and validation to ensure that the application can handle unexpected inputs and provide useful error messages to the users.
- Added unit tests and integration tests with Jacoco coverage to ensure that the application was thoroughly tested and that code coverage was tracked.
- Implemented custom exceptions to improve error handling and provide more meaningful error messages.
- Implemented logging to allow easy debugging and monitoring of the application.
- Implemented Swagger to improve documentation and make it easy for developers to test the API.
- Implementing a Dockerfile and docker-compose allows for easy deployment and scaling of the application, as well as a simplified development process by eliminating the need for developers to locally install all the necessary programs and dependencies
- Updated readme to improve documentation and help new developers understand how to work with the codebase.

## Other improvements to consider

- Adding Spring Security: By implementing Spring Security, the application can be secured against common web attacks such as cross-site scripting (XSS) and SQL injection. Additionally, Spring Security can be used to implement authentication and authorization mechanisms, further securing the application and controlling access to certain resources.
- Creating Custom Validator for POST and PUT Requests: By creating a custom validator for POST and PUT requests, the application can have more fine-grained control over the validation process and ensure that only valid data is accepted by the API.
- Implementing caching: By implementing caching, the application can improve performance by storing frequently-used data in memory and reducing the number of times that it needs to fetch data from the database. This can help to reduce the load on the database and improve the overall responsiveness of the application.