package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;

public class DoReapplyCommand extends AbstractApplicantOnlyCommand {


    @Override
    protected String executeAsApplicant(Provider provider) throws Exception {

        // setup dependencies
        UserService userService = provider.getUserService();
        SessionManager manager = provider.getSessionManager();
        ApplicationValidator applicationValidator = provider.getApplicationValidator();
        ApplicationService applicationService = provider.getApplicationService();
        LocalizationTool localization = provider.getLocalizationTool();
        OutputTool output = provider.getOutputTool();

        try {
            // retrieving application
            Long currentUserId = manager.getUserId().get();
            User user = userService.getUser(currentUserId);
            Application application = applicationValidator.parseValidApplication(user);

            // confirm operation is valid
            applicationValidator.ensureCanReapply(application);

            // perform update
            application.setStatus(Application.Status.APPLIED);

            // persist changes
            applicationService.update(application);

            // notify
            Faculty faculty = application.getFaculty();
            output.setSuccessMsg(Value.SUC_APPLICATION_FOR_X_CREATED, localization.getLocalName(faculty));
            return GET_MAIN_PAGE;

        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }
    }
}
