package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;

public class DoCancelApplicationCommand extends AbstractApplicantOnlyCommand {

    @Override
    protected String executeAsApplicant(Request request) throws Exception {

        // setup dependencies
        UserValidator userValidator = validatorFactory.getUserValidator(request);
        ApplicationValidator applicationValidator = validatorFactory.getApplicationValidator(request);
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);

        try {
            // retrieving application
            User user = userValidator.getCurrentUser().get();
            Application application = applicationValidator.parseValidApplication(user);

            // confirm operation is valid
            applicationValidator.ensureCanCancel(application);

            // perform update
            application.setStatus(Application.Status.CANCELLED);

            // persist changes
            applicationService.update(application);

            // notify
            Faculty faculty = application.getFaculty();
            output.setSuccessMsg(Value.SUC_APPLICATION_FOR_X_CANCELLED, output.getLocalName(faculty));
            return GET_MAIN_PAGE;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }

    }
}
