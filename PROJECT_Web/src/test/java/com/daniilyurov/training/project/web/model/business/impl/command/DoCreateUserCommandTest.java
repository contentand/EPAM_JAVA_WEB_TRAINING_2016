package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import org.junit.Test;

import static com.daniilyurov.training.project.web.i18n.Value.SUC_USER_CREATED;
import static org.junit.Assert.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.*;

public class DoCreateUserCommandTest extends AbstractCommandTest {

    @Test
    public void invalidInfoInputShouldRedirect() throws Exception {

        // setup
        doThrow(ValidationException.class).when(userValidator).parseValidUserInstance();

        // execute
        DoCreateUserCommand command = new DoCreateUserCommand();
        command.setOutputToolFactory(outputToolFactory, validatorFactory);
        command.setDependencies(servicesFactory);
        String responseKey = command.executeAsGuest(request);

        // verify
        assertEquals(GET_USER_REGISTRATION_FORM, responseKey);

    }

    @Test
    public void invalidResultInfoInputShouldRedirect() throws Exception {

        // setup
        doThrow(ValidationException.class).when(resultValidator).parseResultsForUser(any());

        // execute
        DoCreateUserCommand command = new DoCreateUserCommand();
        command.setOutputToolFactory(outputToolFactory, validatorFactory);
        command.setDependencies(servicesFactory);
        String responseKey = command.executeAsGuest(request);

        // verify
        assertEquals(GET_USER_REGISTRATION_FORM, responseKey);

    }

    @Test(expected = DaoException.class)
    public void throwsDaoExceptionIfPersistenceFails() throws Exception {

        // setup
        doThrow(DaoException.class).when(userService).persist(any(), any());

        // execute
        DoCreateUserCommand command = new DoCreateUserCommand();
        command.setOutputToolFactory(outputToolFactory, validatorFactory);
        command.setDependencies(servicesFactory);
        command.executeAsGuest(request);

    }

    @Test
    public void notifiesAboutSuccessAndSendsToLoginPage() throws Exception {

        // execute
        DoCreateUserCommand command = new DoCreateUserCommand();
        command.setOutputToolFactory(outputToolFactory, validatorFactory);
        command.setDependencies(servicesFactory);
        String responseKey = command.executeAsGuest(request);

        // verify
        verify(outputTool, times(1)).setSuccessMsg(SUC_USER_CREATED);
        assertEquals(GET_LOGIN_PAGE, responseKey);

    }
}