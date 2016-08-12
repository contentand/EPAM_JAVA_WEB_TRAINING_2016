package com.daniilyurov.training.project.web.i18n;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;


public class ConcreteLocalizerTest {

    Locale supportedLocale = Locale.GERMAN;
    Locale unsupportedLocale = Locale.CHINESE;
    Locale defaultLocale = Locale.ENGLISH;
    String basename = "localization";
    String[] supportedLanguages = {"en", "ru", "de"};

    @Test // tests: adjustLocale(Locale locale)
    public void supportedLocalePassed_returnsUnchanged() throws Exception {
        // execute
        Locale adjustedLocale = new ConcreteLocalizer(basename).adjustLocale(supportedLocale);

        // verify
        assertEquals(supportedLocale, adjustedLocale);
    }

    @Test // tests: adjustLocale(Locale locale)
    public void unsupportedLocalePassed_returnsDefault() throws Exception {
        // execute
        Locale adjustedLocale = new ConcreteLocalizer(basename).adjustLocale(unsupportedLocale);

        // verify
        assertEquals(defaultLocale, adjustedLocale);
    }

    @Test // tests: getBundle(Locale locale)
    public void supportedLocalePassed_returnsAdjustedResourceBundle() throws Exception {
        // execute
        ResourceBundle bundle = new ConcreteLocalizer(basename).getBundle(supportedLocale);

        // verify
        assertNotNull(bundle);
        assertEquals("Hallo!", bundle.getString("hello"));
    }

    @Test(expected = IllegalArgumentException.class) // tests: getBundle(Locale locale)
    public void unsupportedLocalePassed_throwsIllegalArgumentException() throws Exception {
        // execute
        ResourceBundle bundle = new ConcreteLocalizer(basename).getBundle(unsupportedLocale);
    }

    @Test // tests: getBaseName()
    public void always_returnsCorrectBaseName() throws Exception {
        // execute
        String result = new ConcreteLocalizer(basename).getBaseName();

        // verify
        assertEquals(basename, result);
    }

    @Test // tests: getSupportedLanguages()
    public void always_returnsCorrectArrayOfSupportedLanguages() throws Exception {
        // execute
        String[] result = new ConcreteLocalizer(basename).getSupportedLanguages();

        // verify
        assertTrue(Arrays.equals(supportedLanguages, result));
    }

    @Test // tests: getLocalName(T element, Locale locale)
    public void testGetLocalName() throws Exception {

    }

    @Test // tests: getLocalFirstName(T element, Locale locale)
    public void testGetLocalFirstName() throws Exception {

    }

    @Test // tests: getLocalLastName(T element, Locale locale)
    public void testGetLocalLastName() throws Exception {

    }

    @Test // tests: getLocalDescription(T element, Locale locale)
    public void testGetLocalDescription() throws Exception {

    }
}