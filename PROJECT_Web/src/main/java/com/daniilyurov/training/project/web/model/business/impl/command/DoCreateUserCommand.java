package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ResultValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.i18n.Value.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.*;

import java.util.Set;

public class DoCreateUserCommand extends AbstractUnauthorizedRoleCommand {

    private ServicesFactory servicesFactory;

    @Provided
    public void setDependencies(ServicesFactory servicesFactory) {
        this.servicesFactory = servicesFactory;
    }

    @Override
    protected String executeAsGuest(Request request) throws Exception {
        try {
            // setup dependencies
            UserValidator userValidator = validatorFactory.getUserValidator(request);
            ResultValidator resultValidator = validatorFactory.getResultValidator(request);
            UserService userService = servicesFactory.getUserService();
            OutputTool output = outputToolFactory.getInstance(request);

            // validate user input
            User newUser = userValidator.parseValidUserInstance();
            Set<Result> results = resultValidator.parseResultsForUser(newUser);

            // save user and results
            userService.persist(newUser, results);

            // report success
            output.setSuccessMsg(SUC_USER_CREATED);

            // go to
            return GET_LOGIN_PAGE;

        } catch (ValidationException e) {
            return GET_USER_REGISTRATION_FORM;
        }
    }
}
