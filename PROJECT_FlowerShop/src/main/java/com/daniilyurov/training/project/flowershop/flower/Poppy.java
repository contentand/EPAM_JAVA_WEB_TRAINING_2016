package com.daniilyurov.training.project.flowershop.flower;

import com.daniilyurov.training.project.flowershop.abstraction.Flower;

public class Poppy extends Flower {
    public Poppy(int stemLengthInCentimeters, int daysSinceCut, int hoursSinceCut) {
        super(stemLengthInCentimeters, daysSinceCut, hoursSinceCut);
        setType("Poppy");
        setUnitPriceInCents(9_00);
    }
}
