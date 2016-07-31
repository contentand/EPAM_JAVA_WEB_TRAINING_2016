package com.daniilyurov.training.project.web.model.business.impl.command;

import org.junit.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;


public class GetMainPageCommandTest extends AbstractCommandTest {


    @Test
    public void guestsShouldGetFacultyInfoButNoApplicantInfo() throws Exception {
        Set info = new HashSet<>();

        when(sessionManager.getUserId()).thenReturn(Optional.empty());
        //noinspection unchecked
        when(facultyService.getSetOfFacultyInfo(applicationService, localization, null))
                .thenReturn(info);

        String intent = new GetMainPageCommand().executeAsGuest(provider);

        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_MAIN), eq(true));
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_FACULTY_LIST), eq(info));
        verify(outputTool, never()).set(eq(ATTRIBUTE_EXCEEDED_STUDY_LIMIT), any());

        assertEquals(GET_MAIN_PAGE, intent);
    }

    @Test
    public void applicantsShouldAdditionallyGetInfoIfTheyExceededParallelStudyLimit_1() throws Exception {
        Set info = new HashSet<>();
        when(sessionManager.getUserId()).thenReturn(Optional.ofNullable(90L));
        //noinspection unchecked
        when(facultyService.getSetOfFacultyInfo(applicationService, localization, 90L))
                .thenReturn(info);
        when(applicationValidator.hasExceededParallelStudyLimit(eq(90L))).thenReturn(false);

        String intent = new GetMainPageCommand().executeAsApplicant(provider);
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_MAIN), eq(true));
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_FACULTY_LIST), eq(info));
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_EXCEEDED_STUDY_LIMIT), eq(false));

        assertEquals(GET_MAIN_PAGE, intent);
    }

    @Test
    public void applicantsShouldAdditionallyGetInfoIfTheyExceededParallelStudyLimit_2() throws Exception {
        Set info = new HashSet<>();
        when(sessionManager.getUserId()).thenReturn(Optional.ofNullable(99L));
        //noinspection unchecked
        when(facultyService.getSetOfFacultyInfo(applicationService, localization, 99L))
                .thenReturn(info);
        when(applicationValidator.hasExceededParallelStudyLimit(eq(99L))).thenReturn(true);

        String intent = new GetMainPageCommand().executeAsApplicant(provider);
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_MAIN), eq(true));
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_FACULTY_LIST), eq(info));
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_EXCEEDED_STUDY_LIMIT), eq(true));

        assertEquals(GET_MAIN_PAGE, intent);
    }

    @Test
    public void administratorsShouldGetFacultyInfoButNoApplicantInfo() throws Exception {

        when(sessionManager.getUserId()).thenReturn(Optional.ofNullable(91L));

        String intent = new GetMainPageCommand().executeAsAdministrator(provider);
        verify(outputTool, times(1)).set(eq(ATTRIBUTE_IS_MAIN), eq(true));

        assertEquals(GET_MAIN_PAGE, intent);
    }
}