package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.daniilyurov.training.project.web.utility.RequestParameters.PREFIX_PARAMETER_SUBJECT_ID;
import static org.junit.Assert.*;

public class ResultValidatorTest extends GenericValidator {

    ResultValidator validator;

    @Before
    public void setup() {
        validator = new ResultValidator(input, output, services);
    }

    @Test(expected = NullPointerException.class)
    public void parseResultsForUser_nullPassed_throwsNullPointerException() throws Exception {
        // execute
        validator.parseResults(null);
    }

    @Test(expected = ValidationException.class)
    public void parseResultsForUser_resultCannotBeParsed_throwsValidationException() throws Exception {
        // setup
        User user = mock(User.class);
        Subject subject = new Subject();
        subject.setId(1L);
        when(subjectService.getAll()).thenReturn(new Subject[]{subject});
        when(input.getParameter(eq(PREFIX_PARAMETER_SUBJECT_ID + 1L))).thenReturn("blah");

        // execute
        validator.parseResults(user);
    }

    @Test(expected = ValidationException.class)
    public void parseResultsForUser_resultInvalid_throwsValidationException() throws Exception {
        // setup
        User user = mock(User.class);
        Subject subject = new Subject();
        subject.setId(1L);
        when(subjectService.getAll()).thenReturn(new Subject[]{subject});
        when(input.getParameter(eq(PREFIX_PARAMETER_SUBJECT_ID + 1L))).thenReturn("205");

        // execute
        validator.parseResults(user);
    }

    @Test
    public void parseResultsForUser_valid_returnsSetOfResults() throws Exception {
        // setup
        User user = mock(User.class);
        Subject subject = new Subject();
        subject.setId(1L);
        when(subjectService.getAll()).thenReturn(new Subject[]{subject});
        when(input.getParameter(eq(PREFIX_PARAMETER_SUBJECT_ID + 1L))).thenReturn("190");

        // execute
        Set<Result> results = validator.parseResults(user);

        // verify
        List<Result> resultList = new ArrayList<>(results);
        assertEquals(1, resultList.size());
        assertTrue(1L  == resultList.get(0).getSubject().getId());
        assertEquals(190D, resultList.get(0).getResult(), 0.0001);
    }
}