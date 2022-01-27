package com.server.planner.services.producer;

import com.server.planner.model.Plant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PlannerProducerImpl implements PlannerProducer {

    private final List<Plant> plants;

    public PlannerProducerImpl() {
        this.plants = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public Plant sendNotification() {
        Plant notification = null;
        if(!this.plants.isEmpty())
            notification = this.plants.remove(0);
        return notification;
    }

    @Override
    public void createNotification(final Plant plant) {
        this.plants.add(Plant.builder()
                        .id(plant.getId())
                        .city(plant.getCity())
                        .planning(plant.getPlanning())
                        .progress(plant.getProgress())
                        .completed(plant.getCompleted()).build());
    }
}
