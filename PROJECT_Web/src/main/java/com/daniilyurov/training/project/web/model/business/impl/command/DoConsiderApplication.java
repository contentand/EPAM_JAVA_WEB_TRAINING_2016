package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.REDIRECT_TO_WHERE_HE_CAME_FROM;

public class DoConsiderApplication extends AbstractAdminOnlyCommand {


    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {

        // setup dependencies
        ApplicationValidator applicationValidator = provider.getApplicationValidator();
        ApplicationService applicationService = provider.getApplicationService();
        OutputTool output = provider.getOutputTool();
        LocalizationTool localization = provider.getLocalizationTool();

        try {
            // retrieving application
            Application application = applicationValidator.parseExistingApplication();

            // confirm operation is valid
            applicationValidator.ensureCanConsider(application);

            // perform update
            application.setStatus(Application.Status.UNDER_CONSIDERATION);

            // persist changes
            applicationService.update(application);

            // notify
            Faculty faculty = application.getFaculty();
            output.setSuccessMsg(Value.SUC_APPLICANT_UNDER_CONSIDERATION);
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }

    }
}
