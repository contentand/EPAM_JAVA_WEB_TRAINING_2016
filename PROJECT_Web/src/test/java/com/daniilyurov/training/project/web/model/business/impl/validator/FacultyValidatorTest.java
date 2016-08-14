package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;


public class FacultyValidatorTest extends GenericValidator {

    FacultyValidator validator;

    @Before
    public void setup() {
        validator = new FacultyValidator(input, output, services);
    }

    @Test
    public void parseExistingFacultyFromUrl_valid_returnsFaculty() throws Exception {
        // setup
        Faculty faculty = mock(Faculty.class);
        when(input.getIdFromUri()).thenReturn("123");
        when(facultyService.getById(123L)).thenReturn(faculty);

        // execute
        Faculty result = validator.parseExistingFacultyFromUrl();

        // verify
        assertEquals(faculty, result);
    }

    @Test(expected = ValidationException.class)
    public void parseExistingFacultyFromUrl_invalid_throwsValidationException() throws Exception {
        // setup
        when(input.getIdFromUri()).thenReturn("");

        // execute
        validator.parseExistingFacultyFromUrl();
    }

    @Test(expected = ValidationException.class)
    public void parseExistingFacultyFromUrl_facultyDoesNotExist_throwsValidationException() throws Exception {
        // setup
        when(input.getIdFromUri()).thenReturn("123");
        when(facultyService.getById(123L)).thenReturn(null);

        // execute
        validator.parseExistingFacultyFromUrl();
    }

    @Test
    public void parseAndGetFacultyValidForApplication_registrationOpen_returnsFaculty() throws Exception {
        // setup
        LocalDate today = LocalDate.now();
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateRegistrationStarts(Date.valueOf(today.minusMonths(1)));
        faculty.setDateRegistrationEnds(Date.valueOf(today.plusMonths(1)));
        faculty.setDateStudiesStart(Date.valueOf(today.plusMonths(2)));
        validator = spy(validator);
        doReturn(faculty).when(validator).parseExistingFacultyFromUrl();

        // execute
        Faculty result = validator.parseAndGetFacultyValidForApplication();

        // verify
        assertEquals(faculty, result);

    }

    @Test(expected = ValidationException.class)
    public void parseAndGetFacultyValidForApplication_registrationClosed_throwsValidationException() throws Exception {
        // setup
        LocalDate today = LocalDate.now();
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateRegistrationStarts(Date.valueOf(today.minusMonths(2)));
        faculty.setDateRegistrationEnds(Date.valueOf(today.minusMonths(1)));
        faculty.setDateStudiesStart(Date.valueOf(today.plusMonths(1)));
        validator = spy(validator);
        doReturn(faculty).when(validator).parseExistingFacultyFromUrl();

        // execute
        validator.parseAndGetFacultyValidForApplication();
    }


    @Test
    public void parseFacultyValidForSelection_valid_returnsFaculty() throws Exception {
        // setup
        Faculty faculty = mock(Faculty.class);
        validator = spy(validator);
        doReturn(faculty).when(validator).parseExistingFacultyFromUrl();
        when(facultyService.countUnconsideredApplicants(faculty)).thenReturn(0L);
        when(facultyService.countAllApplicants(faculty)).thenReturn(100L);

        // execute
        Faculty result = validator.parseFacultyValidForSelection();

        // verify
        assertEquals(faculty, result);
    }

    @Test(expected = ValidationException.class)
    public void parseFacultyValidForSelection_unconsideredApplicants_throwsValidationException() throws Exception {
        // setup
        Faculty faculty = mock(Faculty.class);
        validator = spy(validator);
        doReturn(faculty).when(validator).parseExistingFacultyFromUrl();
        when(facultyService.countUnconsideredApplicants(faculty)).thenReturn(10L);
        when(facultyService.countAllApplicants(faculty)).thenReturn(100L);

        // execute
        validator.parseFacultyValidForSelection();
    }

    @Test(expected = ValidationException.class)
    public void parseFacultyValidForSelection_zeroCandidates_throwsValidationException() throws Exception {
        // setup
        Faculty faculty = mock(Faculty.class);
        validator = spy(validator);
        doReturn(faculty).when(validator).parseExistingFacultyFromUrl();
        when(facultyService.countUnconsideredApplicants(faculty)).thenReturn(0L);
        when(facultyService.countAllApplicants(faculty)).thenReturn(0L);

        // execute
        validator.parseFacultyValidForSelection();
    }
}