package com.daniilyurov.training.project.flowershop.abstraction;

import com.daniilyurov.training.project.flowershop.accessory.Jewellery;
import com.daniilyurov.training.project.flowershop.accessory.Stripe;
import com.daniilyurov.training.project.flowershop.accessory.Wrap;
import com.daniilyurov.training.project.flowershop.exception.InvalidFileException;

import java.util.Collection;
import java.util.StringTokenizer;

public abstract class Accessory {
    private int unitaryPriceInCents;

    // Information Expert Pattern
    public static int getPrice(Collection<Accessory> accessories) {
        int price = 0;
        for (Accessory accessory : accessories) {
            price += accessory.getUnitaryPriceInCents();
        }
        return price;
    }

    public static Accessory getAccessoryInstance(StringTokenizer tokens) throws InvalidFileException{
        String accessoryType = tokens.nextToken();
        switch (accessoryType) {
            case "Jewellery":
                return new Jewellery();
            case "Stripe":
                return new Stripe();
            case "Wrap":
                return new Wrap();
            default:
                throw new InvalidFileException();
        }
    }

    public double getUnitaryPriceInCents() {
        return unitaryPriceInCents;
    }

    public void setUnitaryPriceInCents(int unitaryPriceInCents) {
        this.unitaryPriceInCents = unitaryPriceInCents;
    }
}
