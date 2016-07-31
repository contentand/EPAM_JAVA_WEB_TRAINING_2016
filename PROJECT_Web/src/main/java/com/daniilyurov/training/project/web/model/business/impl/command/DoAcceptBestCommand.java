package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

public class DoAcceptBestCommand extends AbstractAdminOnlyCommand {
    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {

        // setup dependencies
        FacultyValidator facultyValidator = provider.getFacultyValidator();
        ApplicationService applicationService = provider.getApplicationService();
        LocalizationTool localization = provider.getLocalizationTool();
        OutputTool output = provider.getOutputTool();

        try {

            // get requested faculty and ensure it is valid for selection
            Faculty faculty = facultyValidator.parseFacultyValidForSelection();

            // select the best
            int numberSelected = applicationService.acceptBestAndRejectOthers(faculty, localization);

            // notify
            output.setSuccessMsg(SUC_SUCCESSFUL_SELECTION_OF_X_FOR_Y,
                    numberSelected, localization.getLocalName(faculty));

            // go to
            return GET_MAIN_PAGE;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }



        // notify 3.
        // respond 4.
    }
}
