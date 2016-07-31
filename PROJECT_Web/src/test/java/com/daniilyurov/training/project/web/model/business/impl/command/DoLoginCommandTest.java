package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Role;
import com.daniilyurov.training.project.web.model.business.impl.Intent;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Locale;

import static org.junit.Assert.*;

public class DoLoginCommandTest extends AbstractCommandTest{



    @Test
    public void testSuccessfulLogin() throws Exception {

        String NAME = "Jörg";

        User validUser = new User();
        validUser.setId(777L);
        validUser.setCyrillicFirstName(NAME);
        validUser.setRole(Role.APPLICANT.name());
        validUser.setLocale(Locale.GERMANY);

        when(userValidator.parseAuthenticatedUser()).thenReturn(validUser);
        when(localization.getLocalFirstName(validUser)).thenReturn(NAME);

        ArgumentCaptor<Long> id = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> role = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Locale> locale = ArgumentCaptor.forClass(Locale.class);
        ArgumentCaptor<String> name = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Value> value = ArgumentCaptor.forClass(Value.class);

        String intent = new DoLoginCommand().safeExecute(provider);

        verify(sessionManager).setLocale(locale.capture());
        verify(sessionManager).setRole(role.capture());
        verify(sessionManager).setUserId(id.capture());
        verify(outputTool).setMsg(value.capture(), name.capture());
        verifyNoMoreInteractions(sessionManager, outputTool);

        assertEquals(Locale.GERMAN.getLanguage(), locale.getValue().getLanguage());
        assertEquals(Role.APPLICANT.name(), role.getValue());
        assertEquals(777L, id.getValue().longValue());
        assertEquals(Value.WELCOME, value.getValue());
        assertEquals(NAME, name.getValue());
        assertEquals(Intent.GET_MAIN_PAGE, intent);
    }

    @Test
    public void testFailedLogin() throws Exception {
        when(userValidator.parseAuthenticatedUser()).thenThrow(ValidationException.class);
        ArgumentCaptor<Value> value = ArgumentCaptor.forClass(Value.class);
        String intent = new DoLoginCommand().safeExecute(provider);
        assertEquals(Intent.REDIRECT_TO_WHERE_HE_CAME_FROM, intent);
    }
}