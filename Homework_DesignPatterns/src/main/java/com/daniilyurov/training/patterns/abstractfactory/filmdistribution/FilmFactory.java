package com.daniilyurov.training.patterns.abstractfactory.filmdistribution;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films.Film;

public interface FilmFactory {
    Film createFilm(Film.Name filmName, Localization.Language language);
}
