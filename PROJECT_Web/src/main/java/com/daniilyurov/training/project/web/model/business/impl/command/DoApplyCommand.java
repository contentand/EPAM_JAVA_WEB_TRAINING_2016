package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

public class DoApplyCommand extends AbstractGeneralRoleCommand {

    @Override
    protected String executeAsGuest(Provider provider) throws Exception {

        // setup dependencies
        OutputTool output = provider.getOutputTool();

        output.setMsg(INFO_PLEASE_LOGIN);
        return GET_LOGIN_PAGE;
    }

    @Override
    protected String executeAsApplicant(Provider provider) throws Exception {

        // setup dependencies
        FacultyValidator facultyValidator = provider.getFacultyValidator();
        ApplicationValidator applicationValidator = provider.getApplicationValidator();
        ApplicationService applicationService = provider.getApplicationService();
        SessionManager manager = provider.getSessionManager();
        UserService userService = provider.getUserService();
        LocalizationTool localization = provider.getLocalizationTool();
        OutputTool output = provider.getOutputTool();


        try {
            Long id = manager.getUserId().get();
            User user = userService.getUser(id);
            Faculty faculty = facultyValidator.parseAndGetFacultyValidForApplication();
            Application application = applicationValidator.getValidBlankApplication(user, faculty);
            applicationService.persist(application);
            output.setSuccessMsg(SUC_APPLICATION_FOR_X_CREATED, localization.getLocalName(faculty));
            return GET_MAIN_PAGE;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }

    }

    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {
        // setup dependencies
        OutputTool output = provider.getOutputTool();

        output.setErrorMsg(ERR_ADMINS_CANNOT_APPLY);
        return GET_MAIN_PAGE;
    }
}
