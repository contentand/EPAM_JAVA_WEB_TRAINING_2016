package com.daniilyurov.training.project.web.i18n;

import java.util.Locale;

/**
 * Indicates methods used to localize pieces of information.
 */
public interface Localize {
    /**
     * @param element to localize
     * @param <T> any that extends NameLocalizable
     * @return local name
     */
    <T extends NameLocalizable> String getLocalName(T element);

    /**
     * @param element to localize
     * @param <T> any that extends FirstLastNameLocalizable
     * @return local first name
     */
    <T extends FirstLastNameLocalizable> String getLocalFirstName(T element);

    /**
     * @param element to localize
     * @param <T> any that extends FirstLastNameLocalizable
     * @return local last name
     */
    <T extends FirstLastNameLocalizable> String getLocalLastName(T element);

    /**
     * @param element to localize
     * @param <T> any that extends DescriptionLocalizable
     * @return local description
     */
    <T extends DescriptionLocalizable> String getLocalDescription(T element);

    /**
     * @return locale currently set to localize elements
     */
    Locale getLocale();
}
