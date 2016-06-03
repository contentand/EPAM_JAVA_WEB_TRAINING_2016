package com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.Localization;

public class TheLordOfTheRingsFilm extends Film {

    public TheLordOfTheRingsFilm(Localization.Language language) {
        super(language);
        super.name = "The Lord of the Rings";
        System.out.println("Loaded film " + name);
    }

    @Override
    public boolean setLanguage(Localization.Language language) {
        switch (language) {
            case ENG:
                currentLocalization = new Localization(language.name, "VOICE", "The world has changed...");
                return true;
            case RUS:
                currentLocalization = new Localization(language.name, "ГОЛОС", "Мир изменился...");
                return true;
            default:
                return false;
        }
    }
}
