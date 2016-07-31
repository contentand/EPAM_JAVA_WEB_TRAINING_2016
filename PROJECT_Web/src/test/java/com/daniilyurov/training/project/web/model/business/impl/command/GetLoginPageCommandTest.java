package com.daniilyurov.training.project.web.model.business.impl.command;

import org.junit.Test;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_LOGIN_PAGE;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_IS_LOGIN_PAGE;
import static org.junit.Assert.*;

public class GetLoginPageCommandTest extends AbstractCommandTest {

    @Test
    public void shouldReturnLoginPageWithProperHeaderAdjustment() throws Exception {

        String intent = new GetLoginPageCommand().executeAsGuest(provider);

        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_LOGIN_PAGE), eq(true));

        assertEquals(GET_LOGIN_PAGE, intent);
    }
}