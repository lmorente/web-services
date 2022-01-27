package com.server.planner;

import com.server.planner.model.Plant;
import com.server.planner.services.cosumer.PlannerConsumer;
import com.server.planner.services.producer.PlannerProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
@EnableAsync
public class PlannerServerApplication {

    private static final Logger logger = LoggerFactory.getLogger(PlannerServerApplication.class);

    @Autowired
    private PlannerConsumer plannerConsumer;

    @Autowired
    private PlannerProducer plannerProducer;

    public static void main(String[] args) {
        SpringApplication.run(PlannerServerApplication.class, args);
    }

    @Bean
    public Supplier<Plant> producer() {
        return () -> {
            final Plant response = this.plannerProducer.sendNotification();
            if (Objects.nonNull(response)) {
                this.logger.info("Producer: {}", response);
                return response;
            }
            return null;
        };
    }

    @Bean
    public Consumer<Plant> consumer() {
        return plant -> this.plannerConsumer.createPlant(plant);
    }
}
