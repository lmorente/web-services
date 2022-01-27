package com.server.planner.services.producer;

import com.server.planner.model.Plant;

public interface PlannerProducer {

    Plant sendNotification();

    void createNotification(Plant plant);
}
