package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import org.junit.Test;

import java.util.Optional;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_CURRENT_USER_INFO;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_IS_USER_INFO_PAGE;
import static org.junit.Assert.*;

public class GetCurrentUserInfoPageCommandTest extends AbstractCommandTest {

    @Test
    public void shouldReturnPageWithProperHeaderAdjustment() throws Exception {

        when(resultsService.getAllOf(any())).thenReturn(new Result[]{});
        when(sessionManager.getUserId()).thenReturn(Optional.of(78L));
        String intent = new GetCurrentUserInfoPageCommand().executeAsApplicant(provider);
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_USER_INFO_PAGE), eq(true));
        assertEquals(GET_CURRENT_USER_INFO, intent);

    }



}