package com.server.planner.connection.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "weather")
public class WeatherConnectionProperties {

    private String host;
    private Integer port;
}
