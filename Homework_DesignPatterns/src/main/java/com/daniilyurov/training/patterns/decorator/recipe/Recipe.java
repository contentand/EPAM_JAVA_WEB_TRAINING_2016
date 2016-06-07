package com.daniilyurov.training.patterns.decorator.recipe;

import java.time.LocalDate;

/**
 *
 */
public interface Recipe {
    String getPrescription();
    LocalDate getDateIssued();
    int getDaysValid();
}
