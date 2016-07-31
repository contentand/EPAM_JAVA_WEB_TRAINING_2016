package com.daniilyurov.training.project.web.i18n;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConcreteLocalizer implements Localizer {

    private String baseName;
    private String applicationDefaultLanguage;
    private String supportedLanguages[];

    public ConcreteLocalizer(String baseName) {
        this.baseName = baseName;
        this.supportedLanguages = new String[]{"en", "ru", "de"};
        this.applicationDefaultLanguage = "en";

        if (ResourceBundle.getBundle(baseName, new Locale(applicationDefaultLanguage)) == null) {
            throw new IllegalStateException("Unable to read resource with base name " + baseName);
        }
    }

    @Override
    public Locale adjustLocale(Locale locale) {
        if (!isSupported(locale)) {
            locale = new Locale(applicationDefaultLanguage);
        }
        return locale;
    }

    @Override
    public ResourceBundle getBundle(Locale locale) {
        if (!isSupported(locale)) throw new IllegalArgumentException();
        return ResourceBundle.getBundle(baseName, locale);
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    @Override
    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    @Override
    public <T extends NameLocalizable> String getLocalName(T element, Locale locale) {
        if (element == null) throw new NullPointerException();

        String language = locale.getLanguage();
        language = isSupported(language) ? language : applicationDefaultLanguage;
        if (language.startsWith("en")) {
            return element.getEnName();
        }
        if (language.startsWith("de")) {
            return element.getDeName();
        }
        if (language.startsWith("ru")) {
            return element.getRuName();
        }
        return element.getEnName(); // in case default has not worked.
    }

    @Override
    public <T extends FirstLastNameLocalizable> String getLocalFirstName(T element, Locale locale) {
        if (element == null) throw new NullPointerException();
        String language = locale.getLanguage();
        language = isSupported(language) ? language : applicationDefaultLanguage;
        if (language.startsWith("en")) {
            return element.getLatinFirstName();
        }
        if (language.startsWith("de")) {
            return element.getLatinFirstName();
        }
        if (language.startsWith("ru")) {
            return element.getCyrillicFirstName();
        }
        return element.getLatinFirstName(); // in case default has not worked.
    }

    @Override
    public <T extends FirstLastNameLocalizable> String getLocalLastName(T element, Locale locale) {
        if (element == null) throw new NullPointerException();
        String language = locale.getLanguage();
        language = isSupported(language) ? language : applicationDefaultLanguage;
        if (language.startsWith("en")) {
            return element.getLatinLastName();
        }
        if (language.startsWith("de")) {
            return element.getLatinLastName();
        }
        if (language.startsWith("ru")) {
            return element.getCyrillicLastName();
        }
        return element.getLatinLastName(); // in case default has not worked.
    }

    @Override
    public <T extends DescriptionLocalizable> String getLocalDescription(T element, Locale locale) {

        if (element == null) throw new NullPointerException();
        String language = locale.getLanguage();
        language = isSupported(language) ? language : applicationDefaultLanguage;
        if (language.startsWith("en")) {
            return element.getEnDescription();
        }
        if (language.startsWith("de")) {
            return element.getDeDescription();
        }
        if (language.startsWith("ru")) {
            return element.getRuDescription();
        }
        return element.getEnDescription(); // in case default has not worked.
    }

    // Private helper methods are listed below

    private boolean isSupported(Locale locale) {

        if (locale == null) return false;
        String language = locale.getLanguage();
        return isSupported(language);

    }

    private boolean isSupported(String language) {
        return Arrays.asList(supportedLanguages).contains(language);
    }
}
