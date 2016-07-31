package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import org.junit.Test;

import static com.daniilyurov.training.project.web.i18n.Value.SUC_USER_CREATED;
import static org.junit.Assert.*;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;

public class DoCreateUserCommandTest extends AbstractCommandTest {

    @Test
    public void invalidInfoInputShouldRedirect() throws Exception {

        when(userValidator.parseValidUserInstance()).thenThrow(ValidationException.class);
        String intent = new DoCreateUserCommand().executeAsGuest(provider);
        assertEquals(GET_USER_REGISTRATION_FORM, intent);

    }

    @Test
    public void invalidResultInfoInputShouldRedirect() throws Exception {

        when(resultValidator.parseResultsForUser(any())).thenThrow(ValidationException.class);
        String intent = new DoCreateUserCommand().executeAsGuest(provider);
        assertEquals(GET_USER_REGISTRATION_FORM, intent);

    }

    @Test(expected = DaoException.class)
    public void throwsDaoExceptionIfPersistenceFails() throws Exception {

        doThrow(DaoException.class).when(userService).persist(any(), any());
        new DoCreateUserCommand().executeAsGuest(provider);

    }

    @Test
    public void notifiesAboutSuccessAndSendsToLoginPage() throws Exception {

        String intent = new DoCreateUserCommand().executeAsGuest(provider);
        verify(outputTool, times(1)).setSuccessMsg(SUC_USER_CREATED);
        assertEquals(GET_LOGIN_PAGE, intent);

    }
}