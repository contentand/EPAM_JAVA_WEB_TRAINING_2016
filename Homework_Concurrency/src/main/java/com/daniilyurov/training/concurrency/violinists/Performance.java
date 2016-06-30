package com.daniilyurov.training.concurrency.violinists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Performance {

    public static List<Violinist> perform(int violinists, int violins, int bows, int seconds) {
        if (violinists <= violins + bows) {
            throw new IllegalArgumentException("There should be more violinists than violins and bows together.");
        }

        Box box = new Box(violins, bows);
        List<Violinist> violinistList = new ArrayList<>();

        IntStream.rangeClosed(1, violinists).forEach(i -> {
            Violinist violinist = new Violinist(box, i);
            violinistList.add(violinist);
            violinist.start();
        });

        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return violinistList;
    }

}
