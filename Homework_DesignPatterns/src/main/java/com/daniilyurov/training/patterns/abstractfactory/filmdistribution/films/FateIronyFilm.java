package com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.Localization;

public class FateIronyFilm extends Film {
    public FateIronyFilm(Localization.Language language) {
        super(language);
        super.name = "Ирония судьбы";
        System.out.println("Loaded film " + name);
    }

    @Override
    public boolean setLanguage(Localization.Language language) {
        switch (language) {
            case UKR:
                currentLocalization = new Localization(language.name, "ГОЛОС", "Со мною вот что происходит,...");
                return true;
            case RUS:
                currentLocalization = new Localization(language.name, "ГОЛОС", "Зі мной ось що трапляється,...");
                return true;
            default:
                return false;
        }
    }
}
