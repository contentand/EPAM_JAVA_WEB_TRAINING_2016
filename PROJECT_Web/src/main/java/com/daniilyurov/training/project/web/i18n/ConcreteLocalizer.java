package com.daniilyurov.training.project.web.i18n;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides simple implementation of Localizer interface.
 * It imperatively supports only English, Russian and German languages.
 * It sets the default language to be English.
 *
 * It contains information about
 * localization basename and languages supported.
 * It provides methods for obtaining ResourceBundles adjusted
 * to a particular locale.
 *
 * It also provides a number of methods that can localize
 * values of classes that extend NameLocalizable, FirstLastNameLocalizable
 * and DescriptionLocalizable.
 *
 * @author Daniil Yurov
 */
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

    /**
     * Returns the same locale if it is supported by
     * the application as described in deployment descriptor
     * or returns application default locale.
     *
     * @param locale locale to verify
     * @return the same locale if it is supported or application default.
     */
    @Override
    public Locale adjustLocale(Locale locale) {
        if (!isSupported(locale)) {
            locale = new Locale(applicationDefaultLanguage);
        }
        return locale;
    }

    /**
     * Returns Resource Bundle with application
     * @return resource bundle adjusted to a particular locale
     * @throws IllegalArgumentException if locale is not supported
     */
    @Override
    public ResourceBundle getBundle(Locale locale) {
        if (!isSupported(locale)) throw new IllegalArgumentException();
        return ResourceBundle.getBundle(baseName, locale);
    }

    /**
     * @return base name of properties responsible for localization.
     */
    @Override
    public String getBaseName() {
        return baseName;
    }

    /**
     * @return an array of languages supported by the system.
     */
    @Override
    public String[] getSupportedLanguages() {
        return supportedLanguages;
    }

    /**
     * @param element the name property of which should be localized
     * @param locale to adjust the name to
     * @param <T> any class that extends NameLocalizable interface
     * @return name property adjusted to locale passed
     */
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

    /**
     * @param element the firstName property of which should be localized
     * @param locale to adjust the firstName to
     * @param <T> any class that extends FirstLastNameLocalizable interface
     * @return firstName property adjusted to locale passed
     */
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

    /**
     * @param element the lastName property of which should be localized
     * @param locale to adjust the lastName to
     * @param <T> any class that extends FirstLastNameLocalizable interface
     * @return lastName property adjusted to locale passed
     */
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

    /**
     * @param element the description property of which should be localized
     * @param locale to adjust the description to
     * @param <T> any class that extends DescriptionLocalizable interface
     * @return description property adjusted to locale passed
     */
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
