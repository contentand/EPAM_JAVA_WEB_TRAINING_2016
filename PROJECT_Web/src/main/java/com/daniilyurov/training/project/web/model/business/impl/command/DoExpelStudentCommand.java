package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;

import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Key.REDIRECT_TO_WHERE_HE_CAME_FROM;

/**
 * This commands expels a student from a faculty.
 */
public class DoExpelStudentCommand extends AbstractAdminOnlyCommand{
    @Override
    protected String executeAsAdministrator(Request request) throws Exception {
        // setup dependencies
        ApplicationValidator applicationValidator = validatorFactory.getApplicationValidator(request);
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);

        try {
            // retrieving application
            Application application = applicationValidator.parseExistingApplication();

            // confirm operation is valid
            applicationValidator.ensureCanBeExpelled(application);

            // perform update
            application.setStatus(Application.Status.EXPELLED);

            // persist changes
            applicationService.update(application);

            // notify
            output.setSuccessMsg(Value.SUC_STUDENT_EXPELLED);
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }
    }
}
