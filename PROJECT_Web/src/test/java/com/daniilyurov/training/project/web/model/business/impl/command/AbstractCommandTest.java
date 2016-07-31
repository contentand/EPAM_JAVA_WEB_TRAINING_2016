package com.daniilyurov.training.project.web.model.business.impl.command;


import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ResultValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import org.junit.Before;
import org.mockito.Mockito;

public class AbstractCommandTest extends Mockito {

    Provider provider;

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

    // Other
    OutputTool outputTool;
    SessionManager sessionManager;
    LocalizationTool localization;




    @Before
    public void settingUp() throws Exception {

        outputTool = mock(OutputTool.class);
        sessionManager = mock(SessionManager.class);
        localization = mock(LocalizationTool.class);
        userValidator = mock(UserValidator.class);
        facultyService = mock(FacultyService.class);
        applicationService = mock(ApplicationService.class);
        userService = mock(UserService.class);
        subjectService = mock(SubjectService.class);
        resultValidator = mock(ResultValidator.class);
        resultsService = mock(ResultsService.class);
        applicationValidator = mock(ApplicationValidator.class);

        provider = mock(Provider.class);
        when(provider.getOutputTool()).thenReturn(outputTool);
        when(provider.getSessionManager()).thenReturn(sessionManager);
        when(provider.getLocalizationTool()).thenReturn(localization);
        when(provider.getUserValidator()).thenReturn(userValidator);
        when(provider.getFacultyService()).thenReturn(facultyService);
        when(provider.getApplicationService()).thenReturn(applicationService);
        when(provider.getUserService()).thenReturn(userService);
        when(provider.getSubjectService()).thenReturn(subjectService);
        when(provider.getResultValidator()).thenReturn(resultValidator);
        when(provider.getResultsService()).thenReturn(resultsService);
        when(provider.getApplicationValidator()).thenReturn(applicationValidator);

    }

}
