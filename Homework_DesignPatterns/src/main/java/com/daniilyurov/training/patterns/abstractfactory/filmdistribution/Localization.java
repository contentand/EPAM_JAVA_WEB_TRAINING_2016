package com.daniilyurov.training.patterns.abstractfactory.filmdistribution;

public class Localization {
    private String language;
    private String voice;
    private String subtitles;

    public Localization(String language, String voice, String subtitles) {
        this.language = language;
        this.voice = voice;
        this.subtitles = subtitles;
    }

    public void playVoice() {
        System.out.printf("Playing %s voice: %s\n", language, voice);
    }
    public void playSubtitles() {
        System.out.printf("Playing %s subtitles: %s\n", language, subtitles);
    }

    public enum Language {
        ENG("English"), GER("German"), RUS("Russian"), UKR("Ukrainian");

        public String name;
        Language(String name) {
            this.name = name;
        }
    }
}
