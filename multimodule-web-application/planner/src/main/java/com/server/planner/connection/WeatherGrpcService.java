package com.server.planner.connection;

import com.server.planner.connection.config.WeatherConnectionProperties;
import es.codeurjc.mastercloudapps.planner.grpc.GetWeatherRequest;
import es.codeurjc.mastercloudapps.planner.grpc.WeatherServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;

@Component
public class WeatherGrpcService {

    private final WeatherConnectionProperties properties;
    private final WeatherServiceGrpc.WeatherServiceBlockingStub client;

    public WeatherGrpcService(WeatherConnectionProperties properties) {
        this.properties = properties;
        this.client = this.createClient();
    }

    private WeatherServiceGrpc.WeatherServiceBlockingStub createClient() {
        return WeatherServiceGrpc.newBlockingStub(ManagedChannelBuilder
                        .forAddress(this.properties.getHost(), this.properties.getPort())
                        .usePlaintext()
                        .build());
    }

    @Async
    public CompletableFuture<String> getWeather(String city) {
        return CompletableFuture.completedFuture(this.client.getWeather(GetWeatherRequest.newBuilder()
                .setCity(city)
                .build())
                .getWeather());
    }
}
