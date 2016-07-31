package com.daniilyurov.training.project.web.model.business.api;

import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ResultValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;

/**
 * A class implementing this interface is able to provide
 * dependencies such as RepositoryServices or Validators.
 * As well as other instruments.
 */
public interface Provider {

    enum Services {
        APPLICATION, FACULTY, RESULTS, SUBJECTS, USER
    }

    enum Validators {
        APPLICATION, FACULTY, RESULTS, USER
    }

    // Services
    ApplicationService getApplicationService();
    FacultyService getFacultyService();
    ResultsService getResultsService();
    SubjectService getSubjectService();
    UserService getUserService();

    // Validators
    ApplicationValidator getApplicationValidator();
    FacultyValidator getFacultyValidator();
    ResultValidator getResultValidator();
    UserValidator getUserValidator();

    // Other
    SessionManager getSessionManager();
    OutputTool getOutputTool();
    LocalizationTool getLocalizationTool();

}
