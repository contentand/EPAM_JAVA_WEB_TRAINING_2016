package com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.Localization;

public class ShrekFilm extends Film {

    public ShrekFilm(Localization.Language language) {
        super(language);
        super.name = "Shrek";
        System.out.println("Loaded film " + super.name);
    }

    @Override
    public boolean setLanguage(Localization.Language language) {
        switch (language) {
            case ENG:
                currentLocalization = new Localization(language.name, "VOICE", "Once upon a time...");
                return true;
            case GER:
                currentLocalization = new Localization(language.name, "STIMME", "Es war einmal...");
                return true;
            default:
                return false;
        }
    }

}
