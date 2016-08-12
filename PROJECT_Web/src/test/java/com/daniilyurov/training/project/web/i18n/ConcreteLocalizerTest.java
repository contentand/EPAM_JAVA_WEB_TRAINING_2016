package com.daniilyurov.training.project.web.i18n;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;


public class ConcreteLocalizerTest extends Mockito {

    Locale supportedGermanLocale = Locale.GERMAN;
    Locale unsupportedLocale = Locale.CHINESE;
    Locale defaultLocale = Locale.ENGLISH;
    String basename = "localization";
    String[] supportedLanguages = {"en", "ru", "de"};

    @Test // tests: adjustLocale(Locale locale)
    public void supportedLocalePassed_returnsUnchanged() throws Exception {
        // execute
        Locale adjustedLocale = new ConcreteLocalizer(basename).adjustLocale(supportedGermanLocale);

        // verify
        assertEquals(supportedGermanLocale, adjustedLocale);
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
        ResourceBundle bundle = new ConcreteLocalizer(basename).getBundle(supportedGermanLocale);

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

    @Test(expected = NullPointerException.class) // tests: getLocalName(T element, Locale locale)
    public void nullElementPassedForLocalName_throwsNullPointerException() throws Exception {
        // execute
        new ConcreteLocalizer(basename).getLocalName(null, supportedGermanLocale);
    }

    @Test(expected = NullPointerException.class) // tests: getLocalFirstName(T element, Locale locale)
    public void nullElementPassedForLocalFirstName_throwsNullPointerException() throws Exception {
        // execute
        new ConcreteLocalizer(basename).getLocalFirstName(null, supportedGermanLocale);
    }

    @Test(expected = NullPointerException.class) // tests: getLocalLastName(T element, Locale locale)
    public void nullElementPassedForLocalLastName_throwsNullPointerException() throws Exception {
        // execute
        new ConcreteLocalizer(basename).getLocalLastName(null, supportedGermanLocale);
    }

    @Test(expected = NullPointerException.class) // tests: getLocalDescription(T element, Locale locale)
    public void nullElementPassedForLocalDescription_throwsNullPointerException() throws Exception {
        // execute
        new ConcreteLocalizer(basename).getLocalDescription(null, supportedGermanLocale);
    }

    @Test // tests: getLocalName(T element, Locale locale)
    public void validElement_returnsLocalName() throws Exception {

        // setup
        NameLocalizable element = mock(NameLocalizable.class);
        when(element.getDeName()).thenReturn("Erdkunde");

        // execute
        String result = new ConcreteLocalizer(basename)
                .getLocalName(element, supportedGermanLocale);

        // verify
        assertEquals("Erdkunde", result);
    }

    @Test // tests: getLocalFirstName(T element, Locale locale)
    public void validElement_returnsLocalFirstName() throws Exception {

        // setup
        FirstLastNameLocalizable element = mock(FirstLastNameLocalizable.class);
        when(element.getLatinFirstName()).thenReturn("Schmidt");

        // execute
        String result = new ConcreteLocalizer(basename)
                .getLocalFirstName(element, supportedGermanLocale);

        // verify
        assertEquals("Schmidt", result);

    }

    @Test // tests: getLocalLastName(T element, Locale locale)
    public void validElement_returnsLocalLastName() throws Exception {
        // setup
        Locale supportedRussianLocale = new Locale("ru");
        FirstLastNameLocalizable element = mock(FirstLastNameLocalizable.class);
        when(element.getCyrillicLastName()).thenReturn("Смирнов");

        // execute
        String result = new ConcreteLocalizer(basename)
                .getLocalLastName(element, supportedRussianLocale);

        // verify
        assertEquals("Смирнов", result);
    }

    @Test // tests: getLocalDescription(T element, Locale locale)
    public void validElement_returnsLocalDescription() throws Exception {

        // setup
        DescriptionLocalizable element = mock(DescriptionLocalizable.class);
        when(element.getDeDescription()).thenReturn("Beschreibung");

        // execute
        String result = new ConcreteLocalizer(basename)
                .getLocalDescription(element, supportedGermanLocale);

        // verify
        assertEquals("Beschreibung", result);
    }
}