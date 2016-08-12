package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;

public class DoQuitStudiesCommand extends AbstractApplicantOnlyCommand {
    @Override
    protected String executeAsApplicant(Request request) throws Exception {

        try {

            // setup dependencies
            UserService userService = servicesFactory.getUserService();
            ApplicationService applicationService = servicesFactory.getApplicationService();
            UserValidator userValidator = validatorFactory.getUserValidator(request);
            ApplicationValidator applicationValidator = validatorFactory.getApplicationValidator(request);
            OutputTool output = outputToolFactory.getInstance(request);

            // retrieving application
            User user = userValidator.getCurrentUser().get();
            Application application = applicationValidator.parseValidApplication(user);


            // confirm operation is valid
            applicationValidator.ensureCanQuit(application);

            // perform update
            application.setStatus(Application.Status.QUIT);

            // persist changes
            applicationService.update(application);

            // notify
            Faculty faculty = application.getFaculty();
            output.setSuccessMsg(Value.SUC_QUIT_STUDIES_IN_X, output.getLocalName(faculty));
            return GET_MAIN_PAGE;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }

}
}
