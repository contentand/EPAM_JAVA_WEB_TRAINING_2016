package com.daniilyurov.training.patterns.decorator.recipe;

import java.time.LocalDate;

/**
 * Decorator
 */
public class ProlongedRecipe implements Recipe {
    private OriginalRecipe originalRecipe;
    private int extraDays;

    public ProlongedRecipe(Recipe recipe, int extraDays) {
        if (recipe instanceof ProlongedRecipe) {
            ProlongedRecipe predecessor = (ProlongedRecipe) recipe;
            this.originalRecipe = predecessor.originalRecipe;
            this.extraDays += predecessor.extraDays + extraDays;
        } else if (recipe instanceof OriginalRecipe) {
            this.originalRecipe = (OriginalRecipe) recipe;
            this.extraDays = extraDays;
        } else {
            throw new ClassCastException("Provided Recipe implementation is not supported. Please review the code.");
        }
    }

    @Override
    public String getPrescription() {
        return originalRecipe.getPrescription();
    }

    @Override
    public LocalDate getDateIssued() {
        return originalRecipe.getDateIssued();
    }

    @Override
    public int getDaysValid() {
        return originalRecipe.getDaysValid() + extraDays;
    }

    public OriginalRecipe getOriginalRecipe() {
        return originalRecipe;
    }
}
