package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import org.junit.Test;

import java.util.Locale;
import java.util.Optional;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.REDIRECT_TO_WHERE_HE_CAME_FROM;
import static org.junit.Assert.*;

public class DoChangeLanguageCommandTest extends AbstractCommandTest {

    @Test(expected = ValidationException.class)
    public void shouldThrowExceptionIfNoOrEmptyLanguageParameterThrown() throws Exception {

        when(userValidator.parseValidLocale()).thenThrow(ValidationException.class);
        new DoChangeLanguageCommand().safeExecute(provider);

    }

    @Test
    public void shouldPersistSystemReadjustedLanguageForAuthorizedUsers() throws Exception {

        when(sessionManager.setLocale(any())).thenReturn(Locale.CHINESE);
        when(sessionManager.getUserId()).thenReturn(Optional.ofNullable(77L));
        String intent = new DoChangeLanguageCommand().safeExecute(provider);
        verify(userService, times(1)).updateLocalePreferencesForUser(eq(77L), eq(Locale.CHINESE));
        assertEquals(REDIRECT_TO_WHERE_HE_CAME_FROM, intent);

    }

    @Test
    public void shouldShouldAskSystemToChangeLanguage() throws Exception {

        when(userValidator.parseValidLocale()).thenReturn(Locale.FRENCH);
        when(sessionManager.getUserId()).thenReturn(Optional.empty());
        String intent = new DoChangeLanguageCommand().safeExecute(provider);
        verify(sessionManager, times(1)).setLocale(eq(Locale.FRENCH));
        assertEquals(REDIRECT_TO_WHERE_HE_CAME_FROM, intent);

    }
}