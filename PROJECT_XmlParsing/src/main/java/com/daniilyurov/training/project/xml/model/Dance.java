package com.daniilyurov.training.project.xml.model;

import com.daniilyurov.training.project.xml.ThreadUnsafe;

import java.util.List;

//TODO: comment
@ThreadUnsafe
public class Dance {
    public static transient final String DANCE = "dance";

    private List<Performance> performances;

    public static final class Element {
        public static transient final String
                PERFORMANCE = "performance";
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
    }

    @Override
    public String toString() {
        return performances.stream().map(Performance::toString).reduce((s1, s2) -> s1 + "\n" + s2).get();
    }
}
