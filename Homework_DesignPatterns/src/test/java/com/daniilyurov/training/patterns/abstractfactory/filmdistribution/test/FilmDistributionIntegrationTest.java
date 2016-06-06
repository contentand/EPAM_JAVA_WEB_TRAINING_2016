package com.daniilyurov.training.patterns.abstractfactory.filmdistribution.test;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.FilmFactory;
import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.Localization;
import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.MainFilmFactory;
import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films.Film;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.ExpectedException;

public class FilmDistributionIntegrationTest {
    public FilmFactory factory;

    @Rule // For evaluating console logs
    public SystemOutRule outRule = new SystemOutRule().enableLog().mute();
    @Rule // For evaluating exceptions thrown
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        factory = new MainFilmFactory();
    }

    @Test
    public void testLoadFilm_LoadsSuccessfully() {
        factory.createFilm(Film.Name.SHREK, Localization.Language.ENG);
        assertEquals("Loaded film Shrek\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

        factory.createFilm(Film.Name.THE_LORD_OF_THE_RINGS, Localization.Language.ENG);
        assertEquals("Loaded film The Lord of the Rings\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

        factory.createFilm(Film.Name.FATE_IRONY, Localization.Language.RUS);
        assertEquals("Loaded film Ирония судьбы\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();
    }

    @Test
    public void playAfterCreation_PlaysTheLanguageSet() {
        Film film;

        film = factory.createFilm(Film.Name.SHREK, Localization.Language.ENG);
        outRule.clearLog();

        film.play();
        assertEquals("Playing Shrek...\n" +
                "Playing English voice: VOICE\n" +
                "Playing English subtitles: Once upon a time...\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();
    }

    @Test
    public void changeVoiceLanguage_bothSubtitleAndVoiceLanguagesChange() {
        Film film;

        film = factory.createFilm(Film.Name.THE_LORD_OF_THE_RINGS, Localization.Language.ENG);
        outRule.clearLog();

        film.setVoice(Localization.Language.RUS);
        assertEquals("Changing voice to Russian\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

        film.play();
        assertEquals("Playing The Lord of the Rings...\n" +
                "Playing Russian voice: ГОЛОС\n" +
                "Playing Russian subtitles: Мир изменился...\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();
    }

    @Test
    public void setLanguageUnsupportedByFilm_throwsException() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Language is not supported!");
        factory.createFilm(Film.Name.SHREK, Localization.Language.RUS);
    }

    @After
    public void cleanup() {
        outRule.clearLog();
    }
}
