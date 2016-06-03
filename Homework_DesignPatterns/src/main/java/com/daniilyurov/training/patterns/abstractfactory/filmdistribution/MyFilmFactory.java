package com.daniilyurov.training.patterns.abstractfactory.filmdistribution;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films.FateIronyFilm;
import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films.Film;
import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films.ShrekFilm;
import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films.TheLordOfTheRingsFilm;

public class MyFilmFactory implements FilmFactory {

    @Override
    public Film createFilm(Film.Name filmName, Localization.Language language) {
        switch (filmName) {
            case THE_LORD_OF_THE_RINGS:
                return new TheLordOfTheRingsFilm(language);
            case SHREK:
                return new ShrekFilm(language);
            case FATE_IRONY:
                return new FateIronyFilm(language);
            default:
                throw new IllegalArgumentException("Such film is not supported.");
        }
    }
}
