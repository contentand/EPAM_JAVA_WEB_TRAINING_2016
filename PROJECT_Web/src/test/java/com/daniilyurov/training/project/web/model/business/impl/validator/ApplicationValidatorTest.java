package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.InputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ApplicationValidatorTest extends GenericValidator {

    ApplicationValidator validator;

    @Before
    public void setup() {
        validator = new ApplicationValidator(input, output, services);
    }

    @Test
    public void testCountNumberOfParallelStudies() throws Exception {
        // setup
        Long userId = 77L;
        long numberOfParallelStudies = 2;
        when(applicationService.countAllOf(eq(userId), eq(Application.Status.ACCEPTED)))
                .thenReturn(numberOfParallelStudies);

        // execute
        long result = validator.countNumberOfParallelStudies(userId);

        // verify
        assertEquals(numberOfParallelStudies, result);
    }

    @Test
    public void testHasExceededParallelStudyLimit_exceeded_returnsTrue() throws Exception {
        // setup
        Long userId = 77L;
        long numberOfParallelStudies = 2;
        when(applicationService.countAllOf(eq(userId), eq(Application.Status.ACCEPTED)))
                .thenReturn(numberOfParallelStudies);

        // execute
        boolean result = validator.hasExceededParallelStudyLimit(userId);

        // verify
        assertTrue(result);
    }

    @Test
    public void testHasExceededParallelStudyLimit_notExceeded_returnsFalse() throws Exception {
        // setup
        Long userId = 77L;
        long numberOfParallelStudies = 0;
        when(applicationService.countAllOf(eq(userId), eq(Application.Status.ACCEPTED)))
                .thenReturn(numberOfParallelStudies);

        // execute
        boolean result = validator.hasExceededParallelStudyLimit(userId);

        // verify
        assertFalse(result);
    }

    @Test
    public void getValidFilledNewApplication_userCanApply_returnsFilledApplication() throws Exception {
        // setup
        Date date = mock(Date.class);
        User user = mock(User.class);
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateStudiesStart(date);
        faculty.setMonthsToStudy(10L);
        when(applicationService.getAllOf(eq(user))).thenReturn(new Application[]{});

        // execute
        Application application = validator.getValidFilledNewApplication(user, faculty);

        // verify
        assertEquals(user, application.getUser());
        assertEquals(faculty, application.getFaculty());
        assertEquals(Application.Status.APPLIED, application.getStatus());
        assertEquals(date, application.getDateStudiesStart());
        assertTrue(10L == application.getMonthsToStudy());
    }

    @Test(expected = NullPointerException.class)
    public void getValidFilledNewApplication_nullsPassed_throwsNPE() throws Exception {
        // execute
        validator.getValidFilledNewApplication(null, null);
    }

    @Test(expected = ValidationException.class)
    // already applied for current selection to destination faculty
    public void getValidFilledNewApplication_userCannotApply1_throwsValidationException() throws Exception {
        // setup
        User user = mock(User.class);
        Date date = mock(Date.class);
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateStudiesStart(date);
        Application applicationForTheSameFaculty = new Application();
        applicationForTheSameFaculty.setFaculty(faculty);
        applicationForTheSameFaculty.setStatus(Application.Status.APPLIED);
        applicationForTheSameFaculty.setDateStudiesStart(date);
        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{applicationForTheSameFaculty});

        // execute
        validator.getValidFilledNewApplication(user, faculty);
    }

    @Test(expected = ValidationException.class)
    // exceeding maximum parallel studies limitation
    public void getValidFilledNewApplication_userCannotApply2_throwsValidationException() throws Exception {
        // setup
        User user = new User();
        user.setId(111L);
        Date date = mock(Date.class);
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateStudiesStart(date);
        when(applicationService.getAllOf(eq(user)))
                .thenReturn(getTwoOtherParallelStudies());

        // execute
        validator.getValidFilledNewApplication(user, faculty);
    }

    @Test(expected = ValidationException.class)
    // already a student at destination faculty
    public void getValidFilledNewApplication_userCannotApply3_throwsValidationException() throws Exception {
        // setup
        User user = mock(User.class);
        Date date = mock(Date.class);
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateStudiesStart(date);
        Application applicationForTheSameFaculty = new Application();
        applicationForTheSameFaculty.setFaculty(faculty);
        applicationForTheSameFaculty.setStatus(Application.Status.ACCEPTED);
        applicationForTheSameFaculty.setDateStudiesStart(date);
        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{applicationForTheSameFaculty});

        // execute
        validator.getValidFilledNewApplication(user, faculty);
    }

    @Test(expected = ValidationException.class)
    // already a graduate of destination faculty
    public void getValidFilledNewApplication_userCannotApply4_throwsValidationException() throws Exception {
        // setup
        User user = mock(User.class);
        Date date = mock(Date.class);
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateStudiesStart(date);
        Application applicationForTheSameFaculty = new Application();
        applicationForTheSameFaculty.setFaculty(faculty);
        applicationForTheSameFaculty.setStatus(Application.Status.GRADUATED);
        applicationForTheSameFaculty.setDateStudiesStart(date);
        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{applicationForTheSameFaculty});

        // execute
        validator.getValidFilledNewApplication(user, faculty);
    }

    @Test(expected = ValidationException.class)
    // does not have required subjects
    public void getValidFilledNewApplication_userCannotApply5_throwsValidationException() throws Exception {
        // setup
        User user = new User();
        user.setId(111L);
        Date date = mock(Date.class);
        Faculty faculty = new Faculty();
        faculty.setId(777L);
        faculty.setDateStudiesStart(date);
        faculty.setRequiredSubjects(getSubjects(3));
        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{});
        when(resultsService.getSubjectsOf(eq(user)))
                .thenReturn(getSubjects(2)); // user has one subject less

        // execute
        validator.getValidFilledNewApplication(user, faculty);
    }

    @Test
    public void parseValidApplication_usersMatch_returnsApplication() throws Exception {
        // setup
        User user = new User();
        user.setId(777L);
        Application application = new Application();
        application.setId(111L);
        application.setUser(user);
        validator = spy(validator);
        doReturn(application).when(validator).parseExistingApplication();

        // execute
        Application result = validator.parseValidApplication(user);

        // verify
        assertEquals(application, result);
    }

    @Test(expected = ValidationException.class)
    public void parseValidApplication_usersDoNotMatch_throwsValidationException() throws Exception {
        // setup
        User user = new User();
        user.setId(777L);
        User fakeUser = new User();
        fakeUser.setId(874L);
        Application application = new Application();
        application.setId(111L);
        application.setUser(user);
        validator = spy(validator);
        doReturn(application).when(validator).parseExistingApplication();

        // execute
        Application result = validator.parseValidApplication(fakeUser);

        // verify
        assertEquals(application, result);
    }

    @Test
    public void parseExistingApplication_exists_returnsApplication() throws Exception {
        // setup
        Application application = mock(Application.class);
        when(input.getIdFromUri()).thenReturn("123");
        when(applicationService.getById(123L)).thenReturn(application);

        // execute
        Application result = validator.parseExistingApplication();

        // verify
        assertEquals(application, result);
    }

    @Test(expected = ValidationException.class)
    public void parseExistingApplication_doesNotExist_throwsValidationException() throws Exception {
        // setup
        when(input.getIdFromUri()).thenReturn("123");

        // execute
        validator.parseExistingApplication();
    }

    @Test(expected = ValidationException.class)
    public void parseExistingApplication_inputHasNoId_throwsValidationException() throws Exception {
        // setup
        when(input.getIdFromUri()).thenReturn("");

        // execute
        validator.parseExistingApplication();
    }

    @Test
    public void ensureCanReapply_valid_doesNothing() throws Exception {
        // setup
        Date date = mock(Date.class);

        User user = new User();
        user.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setDateStudiesStart(date);

        Application application = new Application();
        application.setId(1L);
        application.setUser(user);
        application.setDateStudiesStart(date);
        application.setFaculty(faculty);
        application.setStatus(Application.Status.CANCELLED);

        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{application});

        // execute
        validator.ensureCanReapply(application);
    }

    @Test(expected = ValidationException.class)
    public void ensureCanReapply_invalid_throwsValidationException() throws Exception {
        // setup
        Date date = mock(Date.class);
        Date otherDate = mock(Date.class);

        User user = new User();
        user.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setDateStudiesStart(date);

        Application application = new Application();
        application.setId(1L);
        application.setUser(user);
        application.setDateStudiesStart(otherDate);
        application.setFaculty(faculty);
        application.setStatus(Application.Status.CANCELLED);

        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{application});

        // execute
        validator.ensureCanReapply(application);
    }


    @Test
    public void ensureCanCancel_valid_doesNothing() throws Exception {
        // setup
        Date date = mock(Date.class);

        User user = new User();
        user.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setDateStudiesStart(date);

        Application application = new Application();
        application.setId(1L);
        application.setUser(user);
        application.setDateStudiesStart(date);
        application.setFaculty(faculty);
        application.setStatus(Application.Status.APPLIED);

        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{application});

        // execute
        validator.ensureCanCancel(application);
    }

    @Test(expected = ValidationException.class)
    public void ensureCanCancel_invalid_ThrowsValidationException() throws Exception {
        // setup
        Date date = mock(Date.class);

        User user = new User();
        user.setId(1L);

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setDateStudiesStart(date);

        Application application = new Application();
        application.setId(1L);
        application.setUser(user);
        application.setDateStudiesStart(date);
        application.setFaculty(faculty);
        application.setStatus(Application.Status.CANCELLED);

        when(applicationService.getAllOf(eq(user)))
                .thenReturn(new Application[]{application});

        // execute
        validator.ensureCanCancel(application);
    }

    @Test
    public void ensureCanConsider_valid_doesNothing() throws Exception {
        // setup
        Application application = new Application();
        application.setStatus(Application.Status.APPLIED);

        // execute
        validator.ensureCanConsider(application);
    }

    @Test(expected = ValidationException.class)
    public void ensureCanConsider_invalid_throwsValidationException() throws Exception {
        // setup
        Application application = new Application();
        application.setStatus(Application.Status.UNDER_CONSIDERATION);

        // execute
        validator.ensureCanConsider(application);
    }

    @Test
    public void ensureCanBeExpelled_valid_doesNothing() throws Exception {
        // setup
        Application application = new Application();
        application.setStatus(Application.Status.ACCEPTED);

        // execute
        validator.ensureCanBeExpelled(application);
    }

    @Test(expected = ValidationException.class)
    public void ensureCanBeExpelled_invalid_throwsValidationException() throws Exception {
        // setup
        Application application = new Application();
        application.setStatus(Application.Status.QUIT);

        // execute
        validator.ensureCanBeExpelled(application);
    }

    @Test
    public void ensureCanQuit_valid_doesNothing() throws Exception {
        // setup
        Application application = new Application();
        application.setStatus(Application.Status.ACCEPTED);

        // execute
        validator.ensureCanQuit(application);
    }

    @Test(expected = ValidationException.class)
    public void ensureCanQuit_invalid_throwsValidationException() throws Exception {
        // setup
        Application application = new Application();
        application.setStatus(Application.Status.EXPELLED);

        // execute
        validator.ensureCanQuit(application);
    }

    // Private helper methods are listed below -------------------------------------------------

    private Application[] getTwoOtherParallelStudies() {

        Application[] applications = new Application[2];

        for (int i =0; i < applications.length; i++) {
            Application application = new Application();
            application.setDateStudiesStart(mock(Date.class));
            application.setFaculty(mock(Faculty.class));
            application.setStatus(Application.Status.ACCEPTED);
            applications[i] = application;
        }

        return applications;
    }

    private Set<Subject> getSubjects(int number) {
        Set<Subject> subjects = new HashSet<>();
        for (int i = 0; i < number; i++) {
            Subject subject = new Subject();
            subject.setId((long) i);
            subjects.add(subject);
        }
        return subjects;
    }
}