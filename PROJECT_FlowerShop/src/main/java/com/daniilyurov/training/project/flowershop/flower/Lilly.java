package com.daniilyurov.training.project.flowershop.flower;

import com.daniilyurov.training.project.flowershop.abstraction.Flower;

public class Lilly extends Flower {
    public Lilly(int stemLengthInCentimeters, int daysSinceCut, int hoursSinceCut) {
        super(stemLengthInCentimeters, daysSinceCut, hoursSinceCut);
        setType("Lilly");
        setUnitPriceInCents(12_00);
    }
}
