CREATE TABLE IF NOT EXISTS timezones (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    zone_id          VARCHAR(50)  NOT NULL UNIQUE,
    display_name     VARCHAR(100) NOT NULL,
    region           VARCHAR(50)  NOT NULL,
    utc_offset_minutes INT        NOT NULL,
    observes_dst     BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS cities (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    name         VARCHAR(100) NOT NULL,
    country_code CHAR(2)      NOT NULL,
    country_name VARCHAR(100) NOT NULL,
    population   BIGINT,
    timezone_id  INT          NOT NULL,
    CONSTRAINT fk_city_timezone FOREIGN KEY (timezone_id) REFERENCES timezones (id)
);

CREATE TABLE IF NOT EXISTS dst_transitions (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    timezone_id INT      NOT NULL,
    year        INT      NOT NULL,
    dst_start   DATETIME NOT NULL,
    dst_end     DATETIME NOT NULL,
    CONSTRAINT fk_dst_timezone FOREIGN KEY (timezone_id) REFERENCES timezones (id)
);
