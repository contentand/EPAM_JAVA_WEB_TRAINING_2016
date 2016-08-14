package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.model.business.impl.Key.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

public class DoApplyCommand extends AbstractGeneralRoleCommand {

    private ServicesFactory servicesFactory;

    @Provided
    public void setServicesFactory(ServicesFactory servicesFactory) {
        this.servicesFactory = servicesFactory;
    }


    @Override
    protected String executeAsGuest(Request request) throws Exception {

        // setup dependencies
        OutputTool output = outputToolFactory.getInstance(request);

        output.setMsg(INFO_PLEASE_LOGIN);
        return GET_LOGIN_PAGE;
    }

    @Override
    protected String executeAsApplicant(Request request) throws Exception {

        // setup dependencies
        FacultyValidator facultyValidator = validatorFactory.getFacultyValidator(request);
        ApplicationValidator applicationValidator = validatorFactory.getApplicationValidator(request);
        UserValidator userValidator = validatorFactory.getUserValidator(request);
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);


        try {
            User user = userValidator.getCurrentUser().get();
            Faculty faculty = facultyValidator.parseAndGetFacultyValidForApplication();
            Application application = applicationValidator.getValidFilledNewApplication(user, faculty);
            applicationService.persist(application);
            output.setSuccessMsg(SUC_APPLICATION_FOR_X_CREATED, output.getLocalName(faculty));
            return GET_MAIN_PAGE;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }

    }

    @Override
    protected String executeAsAdministrator(Request request) throws Exception {
        // setup dependencies
        OutputTool output = outputToolFactory.getInstance(request);

        output.setErrorMsg(ERR_ADMINS_CANNOT_APPLY);
        return GET_MAIN_PAGE;
    }
}
