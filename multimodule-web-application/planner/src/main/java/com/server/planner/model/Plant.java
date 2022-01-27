package com.server.planner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plant {

    private Long id;
    private String city;
    private String planning;
    private Integer progress;
    private Boolean completed;

    @Override
    public String toString() {
        return "Planning [id=" + id + ", city=" + city +", planning=" + planning
                + ", progress=" + progress + ", completed=" + completed + "]";
    }

    public void endProgress() {
        this.planning = this.city.toUpperCase().charAt(0) > 'M'
                ? this.planning.toUpperCase() : this.planning.toLowerCase();
        this.progress = 100;
        this.completed = Boolean.TRUE;
    }

    public void increaseProgress() {
        this.setProgress(this.progress + 25);
    }

    public void updatePlanning(String value) {
        this.planning = Objects.nonNull(this.planning) ? planning.concat(value) : this.city.concat(value);
        this.increaseProgress();
    }

    public void initProgressPlant() {
        this.completed = Boolean.FALSE;
        this.progress = 0;
    }
}
