package com.server.planner.connection;

import com.server.planner.connection.config.TopoConnectionProperties;
import com.server.planner.connection.response.TopoResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class TopoRestClientService {

    private final TopoConnectionProperties properties;

    public TopoRestClientService(TopoConnectionProperties properties) {
        this.properties = properties;
    }

    @Async
    public CompletableFuture<String> getLandscape(String city) {
        return CompletableFuture.completedFuture(Objects.requireNonNull(new RestTemplate()
                        .getForObject(String.format(this.properties.getUrl(), city), TopoResponse.class))
                .getLandscape());
    }
}
