package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.junit.Test;

import java.util.Locale;
import java.util.Optional;

import static com.daniilyurov.training.project.web.model.business.impl.Key.REDIRECT_TO_WHERE_HE_CAME_FROM;
import static org.junit.Assert.*;

public class DoChangeLanguageCommandTest extends AbstractCommandTest {

    @Test
    public void shouldPersistSystemReadjustedLanguageForAuthorizedUsers() throws Exception {

        // setup
        User user = new User();
        when(outputTool.setLocale(any())).thenReturn(Locale.CHINESE);
        when(userValidator.getCurrentUser()).thenReturn(Optional.of(user));

        // execute
        DoChangeLanguageCommand command = new DoChangeLanguageCommand();
        command.setDependencies(servicesFactory, validatorFactory, outputToolFactory);
        String responseKey = command.execute(request);

        // verify
        verify(userService, times(1)).updateLocalePreferencesForUser(eq(user), eq(Locale.CHINESE));
        assertEquals(REDIRECT_TO_WHERE_HE_CAME_FROM, responseKey);

    }

    @Test
    public void shouldShouldAskSystemToChangeLanguage() throws Exception {

        // setup
        when(userValidator.parseValidLocale()).thenReturn(Locale.FRENCH);
        when(userValidator.getCurrentUser()).thenReturn(Optional.empty());

        // execute
        DoChangeLanguageCommand command = new DoChangeLanguageCommand();
        command.setDependencies(servicesFactory, validatorFactory, outputToolFactory);
        String responseKey = command.execute(request);

        // verify
        verify(outputTool, times(1)).setLocale(eq(Locale.FRENCH));
        assertEquals(REDIRECT_TO_WHERE_HE_CAME_FROM, responseKey);
    }
}