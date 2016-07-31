package com.daniilyurov.training.project.web.model.business.impl.command;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.daniilyurov.training.project.web.i18n.Value.SUC_SUCCESSFUL_LOGOUT;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_MAIN_PAGE;

public class DoLogoutCommandTest extends AbstractCommandTest {

    @Test
    public void ordinaryAuthorizedUsersCanLogout() throws Exception {

        String intent = new DoLogoutCommand().executeAsApplicant(provider);
        verify(sessionManager, times(1)).invalidate();
        verify(outputTool, times(1)).setSuccessMsg(eq(SUC_SUCCESSFUL_LOGOUT));
        assertEquals(GET_MAIN_PAGE, intent);

    }

    @Test
    public void administratorsCanLogout() throws Exception {

        String intent = new DoLogoutCommand().executeAsAdministrator(provider);
        verify(sessionManager, times(1)).invalidate();
        verify(outputTool, times(1)).setSuccessMsg(eq(SUC_SUCCESSFUL_LOGOUT));
        assertEquals(GET_MAIN_PAGE, intent);

    }


}