package com.daniilyurov.training.project.flowershop.flower;

import com.daniilyurov.training.project.flowershop.abstraction.Flower;

public class Rose extends Flower {
    public Rose(int stemLengthInCentimeters, int daysSinceCut, int hoursSinceCut) {
        super(stemLengthInCentimeters, daysSinceCut, hoursSinceCut);
        setType("Rose");
        setUnitPriceInCents(15_90);
    }
}
