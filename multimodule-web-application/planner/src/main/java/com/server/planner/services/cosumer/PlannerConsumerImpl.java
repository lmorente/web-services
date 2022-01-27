package com.server.planner.services.cosumer;

import com.server.planner.connection.TopoRestClientService;
import com.server.planner.connection.WeatherGrpcService;
import com.server.planner.model.Plant;
import com.server.planner.services.producer.PlannerProducer;
import com.server.planner.services.producer.PlannerProducerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class PlannerConsumerImpl implements PlannerConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PlannerConsumerImpl.class);

    private final TopoRestClientService topoRestClientService;
    private final WeatherGrpcService weatherGrpcService;
    private final PlannerProducer plannerProducer;

    public PlannerConsumerImpl(TopoRestClientService topoRestClientService, WeatherGrpcService weatherGrpcService,
                               PlannerProducerImpl plannerProducer) {
        this.topoRestClientService = topoRestClientService;
        this.weatherGrpcService = weatherGrpcService;
        this.plannerProducer = plannerProducer;
    }

    @Override
    public void createPlant(Plant plant) {
        this.initProcess(plant); //0%

        final CompletableFuture<Void> weather = this.weatherGrpcService.getWeather(plant.getCity())
                .thenAccept(plant::updatePlanning)
                .thenRun(() -> this.getNotification(plant));//50-75%

        final CompletableFuture<Void> lands = this.topoRestClientService.getLandscape(plant.getCity())
                .thenAccept(plant::updatePlanning)
                .thenRun(() -> this.getNotification(plant));//50%-75%

        this.allCallEndProcess(plant);//25%

        CompletableFuture.allOf(lands, weather).join();
        this.endProcess(plant);//100%
    }

    private void initProcess(Plant plant) {
        plant.initProgressPlant();
        this.getNotification(plant);
    }

    private void endProcess(Plant plant) {
        sleep();
        plant.endProgress();
        this.getNotification(plant);
    }

    private void allCallEndProcess(Plant plant) {
        plant.increaseProgress();
        this.getNotification(plant);
    }

    private void getNotification(Plant plant) {
        this.plannerProducer.createNotification(plant);
    }

    private void sleep() {
        try {
            Thread.sleep((long) (Math.random() * (3000 - 1000) + 1000));
        } catch (InterruptedException e) {
            logger.error("Sleep error: {}", e.getMessage());
        }
    }
}
