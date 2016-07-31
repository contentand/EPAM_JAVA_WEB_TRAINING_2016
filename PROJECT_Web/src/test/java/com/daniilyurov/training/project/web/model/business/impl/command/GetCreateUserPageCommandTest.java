package com.daniilyurov.training.project.web.model.business.impl.command;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_IS_REGISTRATION_PAGE;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_SUBJECT_LIST;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;
import static org.junit.Assert.*;

public class GetCreateUserPageCommandTest extends AbstractCommandTest {

    @Test
    public void shouldReturnPageWithSubjectListAndHeaderAdjustment() throws Exception {
        List<Map.Entry<String, String>> subjects = new ArrayList<>();

        when(subjectService.getMapWithSubjectIdsAndTheirLocalNames(eq(localization))).thenReturn(subjects);

        String intent = new GetCreateUserPageCommand().executeAsGuest(provider);

        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_REGISTRATION_PAGE), eq(true));
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_SUBJECT_LIST), eq(subjects));

        assertEquals(GET_USER_REGISTRATION_FORM, intent);
    }
}