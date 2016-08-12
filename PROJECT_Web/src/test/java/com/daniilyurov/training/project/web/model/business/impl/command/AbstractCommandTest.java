package com.daniilyurov.training.project.web.model.business.impl.command;


import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ResultValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import org.junit.Before;
import org.mockito.Mockito;

public class AbstractCommandTest extends Mockito {

    Request request;

    // Services
    FacultyService facultyService;
    ApplicationService applicationService;
    UserService userService;
    SubjectService subjectService;
    ResultsService resultsService;

    // Validators
    UserValidator userValidator;
    ResultValidator resultValidator;
    ApplicationValidator applicationValidator;
    FacultyValidator facultyValidator;

    // Other
    OutputTool outputTool;

    OutputToolFactory outputToolFactory;
    ValidatorFactory validatorFactory;
    ServicesFactory servicesFactory;

    @Before
    public void settingUp() throws Exception {

        request = mock(Request.class);

        outputTool = mock(OutputTool.class);
        userValidator = mock(UserValidator.class);
        facultyService = mock(FacultyService.class);
        applicationService = mock(ApplicationService.class);
        userService = mock(UserService.class);
        subjectService = mock(SubjectService.class);
        resultValidator = mock(ResultValidator.class);
        resultsService = mock(ResultsService.class);
        applicationValidator = mock(ApplicationValidator.class);
        facultyValidator = mock(FacultyValidator.class);

        outputToolFactory = mock(OutputToolFactory.class);
        validatorFactory = mock(ValidatorFactory.class);
        servicesFactory = mock(ServicesFactory.class);

        when(outputToolFactory.getInstance(eq(request))).thenReturn(outputTool);
        when(validatorFactory.getApplicationValidator(eq(request))).thenReturn(applicationValidator);
        when(validatorFactory.getFacultyValidator(eq(request))).thenReturn(facultyValidator);
        when(validatorFactory.getResultValidator(eq(request))).thenReturn(resultValidator);
        when(validatorFactory.getUserValidator(eq(request))).thenReturn(userValidator);

        when(servicesFactory.getApplicationService()).thenReturn(applicationService);
        when(servicesFactory.getFacultyService()).thenReturn(facultyService);
        when(servicesFactory.getResultsService()).thenReturn(resultsService);
        when(servicesFactory.getSubjectService()).thenReturn(subjectService);
        when(servicesFactory.getUserService()).thenReturn(userService);
    }

}
