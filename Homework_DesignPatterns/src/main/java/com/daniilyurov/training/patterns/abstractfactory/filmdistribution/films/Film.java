package com.daniilyurov.training.patterns.abstractfactory.filmdistribution.films;

import com.daniilyurov.training.patterns.abstractfactory.filmdistribution.Localization;

public abstract class Film {
    protected Localization currentLocalization;
    protected String name;

    public Film(Localization.Language language) {
        if (!setLanguage(language)) {
            throw new IllegalArgumentException("Language is not supported!");
        };
    }

    public abstract boolean setLanguage(Localization.Language language);

    public boolean setVoice(Localization.Language language) {
        System.out.println("Changing voice to " + language.name);
        return setLanguage(language);
    }
    public boolean setSubtitles(Localization.Language language){
        System.out.println("Changing subtitles to " + language.name);
        return setLanguage(language);
    }

    public void play() {
        System.out.println("Playing " + name + "...");
        currentLocalization.playVoice();
        currentLocalization.playSubtitles();
    }

    public enum Name {
        THE_LORD_OF_THE_RINGS, FATE_IRONY, SHREK;
    }
}
