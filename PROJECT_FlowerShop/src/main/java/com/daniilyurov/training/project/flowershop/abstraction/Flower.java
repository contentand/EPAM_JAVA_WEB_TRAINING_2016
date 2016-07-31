package com.daniilyurov.training.project.flowershop.abstraction;

import com.daniilyurov.training.project.flowershop.exception.InvalidFileException;
import com.daniilyurov.training.project.flowershop.flower.Lilly;
import com.daniilyurov.training.project.flowershop.flower.Poppy;
import com.daniilyurov.training.project.flowershop.flower.Rose;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

public abstract class Flower {

    private static Map<String, Class<? extends Flower>> flowerAssortmentMap = getFlowerAssortmentMap();

    private LocalDateTime dateTimeCut;
    private int unitPriceInCents;
    private int stemLengthInCentimeters;
    private String type;

    public Flower(int stemLengthInCentimeters, int daysSinceCut, int hoursSinceCut) {
        setStemLengthInCentimeters(stemLengthInCentimeters);
        setTimeCut(daysSinceCut, hoursSinceCut);
    }

    private static Map<String, Class<? extends Flower>> getFlowerAssortmentMap() {
        Map<String, Class<? extends Flower>> result = new HashMap<>();
        result.put("Lilly", Lilly.class);
        result.put("Poppy", Poppy.class);
        result.put("Rose", Rose.class);
        return result;
    }

    // Information Expert Pattern
    public static int getPrice(Collection<Flower> flowers) {
        int price = 0;
        for (Flower flower : flowers) {
            price += flower.getUnitPriceInCents();
        }
        return price;
    }

    // Information Expert Pattern
    public static Comparator<Flower> getAscendingFreshnessComparator() {
        return (flower1, flower2) -> {
            if (flower1.getDateTimeCut().isAfter(flower2.getDateTimeCut())) {
                return 1;
            }
            if (flower1.getDateTimeCut().isBefore(flower2.getDateTimeCut())) {
                return -1;
            }
            return 0;
        };
    }

    // Information Expert Pattern
    public static Flower getFirstFlowerMatchingStemLengthBounds(int lowerBoundInclusive, int upperBoundInclusive,
                                                                Collection<Flower> flowers) {
        for (Flower flower : flowers) {
            if ((flower.stemLengthInCentimeters <= upperBoundInclusive)
                    && (flower.stemLengthInCentimeters >= lowerBoundInclusive)) {
                return flower;
            }
        }
        return null;
    }

    public static Flower getFlowerInstance(StringTokenizer tokens) throws InvalidFileException {
        String flowerType = tokens.nextToken();
        try {
            int stemLengthInCentimeters = Integer.parseInt(tokens.nextToken());
            int daysSinceCut = Integer.parseInt(tokens.nextToken());
            int hoursSinceCut = Integer.parseInt(tokens.nextToken());

            Class<? extends Flower> cl = flowerAssortmentMap.get(flowerType);
            if (cl == null) {
                throw new InvalidFileException();
            }
            Constructor constructor = cl.getConstructor(int.class, int.class, int.class);
            return (Flower) constructor.newInstance(stemLengthInCentimeters, daysSinceCut, hoursSinceCut);

        } catch (NumberFormatException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                InvocationTargetException e) {
            throw new InvalidFileException();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDateTimeCut() {
        return dateTimeCut;
    }

    public void setTimeCut(long daysSinceCut, long hoursSinceCut) {
        this.dateTimeCut = LocalDateTime.now().minusDays(daysSinceCut).minusHours(hoursSinceCut);
    }

    public int getUnitPriceInCents() {
        return unitPriceInCents;
    }

    public void setUnitPriceInCents(int unitPriceInCents) {
        this.unitPriceInCents = unitPriceInCents;
    }

    public int getStemLengthInCentimeters() {
        return stemLengthInCentimeters;
    }

    public void setStemLengthInCentimeters(int stemLengthInCentimeters) {
        this.stemLengthInCentimeters = stemLengthInCentimeters;
    }
}
