package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.InputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import org.junit.Before;
import org.mockito.Mockito;

/**
 * Contains setup procedures shared between
 * all tests of Validator classes.
 */
public class GenericValidator extends Mockito {

    InputTool input;
    OutputTool output;
    ServicesFactory services;

    ApplicationService applicationService;
    FacultyService facultyService;
    ResultsService resultsService;
    SubjectService subjectService;
    UserService userService;

    @Before
    public void generalSetup() {
        this.input = mock(InputTool.class);
        this.output = mock(OutputTool.class);
        this.services = mock(ServicesFactory.class);

        this.applicationService = mock(ApplicationService.class);
        this.facultyService = mock(FacultyService.class);
        this.resultsService = mock(ResultsService.class);
        this.subjectService = mock(SubjectService.class);
        this.userService = mock(UserService.class);

        when(services.getApplicationService()).thenReturn(applicationService);
        when(services.getFacultyService()).thenReturn(facultyService);
        when(services.getResultsService()).thenReturn(resultsService);
        when(services.getSubjectService()).thenReturn(subjectService);
        when(services.getUserService()).thenReturn(userService);
    }
}
