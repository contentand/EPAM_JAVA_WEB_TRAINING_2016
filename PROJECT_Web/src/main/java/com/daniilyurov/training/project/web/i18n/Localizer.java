package com.daniilyurov.training.project.web.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public interface Localizer {

    /**
     * Returns the same locale if it is supported by
     * the application as described in deployment descriptor
     * or returns application default locale.
     *
     * @param localeToAdjust locale to verify
     * @return the same locale if it is supported or application default.
     */
    Locale adjustLocale(Locale localeToAdjust);

    /**
     * Returns Resource Bundle with application
     * @return resource bundle adjusted to a particular locale
     * @throws IllegalArgumentException if locale is not supported
     */
    ResourceBundle getBundle(Locale locale);

    String getBaseName();

    String[] getSupportedLanguages();

    <T extends NameLocalizable> String getLocalName(T element, Locale locale);

    <T extends FirstLastNameLocalizable> String getLocalFirstName(T element, Locale locale);

    <T extends FirstLastNameLocalizable> String getLocalLastName(T element, Locale locale);

    <T extends DescriptionLocalizable> String getLocalDescription(T element, Locale locale);

}
