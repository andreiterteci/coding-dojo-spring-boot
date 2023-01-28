CREATE TABLE country
(
    id   VARCHAR(100) NOT NULL PRIMARY KEY,
    name VARCHAR(3)   NOT NULL
);

CREATE TABLE city
(
    id         VARCHAR(100) NOT NULL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    country_id VARCHAR(100) NOT NULL,
    CONSTRAINT FK_CITY_COUNTRY FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE TABLE weather
(
    id          VARCHAR(100)  NOT NULL PRIMARY KEY,
    temperature NUMERIC(5, 2) NOT NULL,
    city_id     VARCHAR(100)  NOT NULL,
    created_at  TIMESTAMP,
    CONSTRAINT FK_CITY_TEMP FOREIGN KEY (city_id) REFERENCES city (id)
);

CREATE INDEX IF NOT EXISTS country_name_idx ON country (name);
CREATE INDEX IF NOT EXISTS city_name_idx ON city (name);