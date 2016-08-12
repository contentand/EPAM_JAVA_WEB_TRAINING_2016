package com.daniilyurov.training.project.web.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The implementing class should contain information about
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

    /**
     * @return base name of properties responsible for localization.
     */
    String getBaseName();

    /**
     * @return an array of languages supported by the system.
     */
    String[] getSupportedLanguages();

    /**
     * @param element the name property of which should be localized
     * @param locale to adjust the name to
     * @param <T> any class that extends NameLocalizable interface
     * @return name property adjusted to locale passed
     */
    <T extends NameLocalizable> String getLocalName(T element, Locale locale);

    /**
     * @param element the firstName property of which should be localized
     * @param locale to adjust the firstName to
     * @param <T> any class that extends FirstLastNameLocalizable interface
     * @return firstName property adjusted to locale passed
     */
    <T extends FirstLastNameLocalizable> String getLocalFirstName(T element, Locale locale);

    /**
     * @param element the lastName property of which should be localized
     * @param locale to adjust the lastName to
     * @param <T> any class that extends FirstLastNameLocalizable interface
     * @return lastName property adjusted to locale passed
     */
    <T extends FirstLastNameLocalizable> String getLocalLastName(T element, Locale locale);

    /**
     * @param element the description property of which should be localized
     * @param locale to adjust the description to
     * @param <T> any class that extends DescriptionLocalizable interface
     * @return description property adjusted to locale passed
     */
    <T extends DescriptionLocalizable> String getLocalDescription(T element, Locale locale);

}
