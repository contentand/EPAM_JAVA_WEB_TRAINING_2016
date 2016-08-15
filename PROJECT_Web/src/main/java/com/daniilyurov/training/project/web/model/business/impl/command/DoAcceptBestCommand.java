package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import static com.daniilyurov.training.project.web.model.business.impl.Key.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

/**
 * This command selects the top best candidates and turns them
 * into students of the faculties, other candidates are rejected.
 */
public class DoAcceptBestCommand extends AbstractAdminOnlyCommand {

    @Override
    protected String executeAsAdministrator(Request request) throws Exception {

        // setup dependencies
        FacultyValidator facultyValidator = validatorFactory.getFacultyValidator(request);
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);

        try {

            // get requested faculty and ensure it is valid for selection
            Faculty faculty = facultyValidator.parseFacultyValidForSelection();

            // select the best
            int numberSelected = applicationService.acceptBestAndRejectOthers(faculty, output);

            // notify
            output.setSuccessMsg(SUC_SUCCESSFUL_SELECTION_OF_X_FOR_Y,
                    numberSelected, output.getLocalName(faculty));

            // go to
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        } catch (ValidationException e) {
            return GET_MAIN_PAGE;
        }
    }
}
