package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.SubjectService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import java.util.List;
import java.util.Map;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_USER_REGISTRATION_FORM;

public class GetCreateUserPageCommand extends AbstractUnauthorizedRoleCommand {

    @Override
    protected String executeAsGuest(Provider provider) throws Exception {

        // setup dependencies
        OutputTool output = provider.getOutputTool();
        LocalizationTool localization = provider.getLocalizationTool();
        SubjectService subjectService = provider.getSubjectService();

        // let jsp know the header should be adjusted for user registration page
        output.set(ATTRIBUTE_IS_REGISTRATION_PAGE, true);

        // get the list of all subjects available
        List<Map.Entry<String, String>> subjectList = subjectService
                .getMapWithSubjectIdsAndTheirLocalNames(localization);

        // make this list available for the view to display
        output.set(ATTRIBUTE_SUBJECT_LIST, subjectList);
        return GET_USER_REGISTRATION_FORM;

    }
}
