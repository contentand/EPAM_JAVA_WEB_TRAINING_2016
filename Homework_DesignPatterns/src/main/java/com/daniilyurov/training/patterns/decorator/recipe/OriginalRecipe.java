package com.daniilyurov.training.patterns.decorator.recipe;

import java.time.LocalDate;

/**
 *
 */

// Final to ensure class state immutable.
public final class OriginalRecipe implements Recipe {
    private String prescription;
    private LocalDate dateIssued;
    private int daysValid;

    public OriginalRecipe(String prescription, LocalDate dateIssued, int daysValid) {
        this.prescription = prescription;
        this.dateIssued = dateIssued;
        this.daysValid = daysValid;
    }

    @Override
    public String getPrescription() {
        return prescription;
    }

    @Override
    public LocalDate getDateIssued() {
        return dateIssued;
    }

    @Override
    public int getDaysValid() {
        return daysValid;
    }
}
