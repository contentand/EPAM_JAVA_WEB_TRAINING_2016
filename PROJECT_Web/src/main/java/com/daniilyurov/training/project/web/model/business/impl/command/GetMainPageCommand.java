package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;

import java.util.Set;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;

public class GetMainPageCommand extends AbstractGeneralRoleCommand {

    @Override
    protected String executeAsGuest(Provider provider) throws Exception {
        sendInformationAboutAllFaculties(provider, false);
        return GET_MAIN_PAGE;
    }

    @Override
    protected String executeAsApplicant(Provider provider) throws Exception {
        sendInformationAboutAllFaculties(provider, true);
        return GET_MAIN_PAGE;
    }

    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {
        sendInformationAboutAllFaculties(provider, false);
        return GET_MAIN_PAGE;
    }

    // Private helper methods are listed below

    private void sendInformationAboutAllFaculties(Provider provider,
                                                  boolean isApplicant) throws Exception {

        // setup dependencies
        FacultyService fs = provider.getFacultyService();
        ApplicationService at = provider.getApplicationService();
        ApplicationValidator av = provider.getApplicationValidator();
        UserService us = provider.getUserService();
        LocalizationTool lt = provider.getLocalizationTool();
        OutputTool output = provider.getOutputTool();
        SessionManager manager = provider.getSessionManager();

        // let the view know the header should be adjusted for main page
        output.set(ATTRIBUTE_IS_MAIN, true);

        // get current user id if present
        Long currentUserId = manager.getUserId().orElse(null);

        // get statistical info about all faculties [and (optionally) how they relate to current user]
        Set<FacultyInfoItem> setOfFacultyInfo = fs.getSetOfFacultyInfo(at, lt, currentUserId);

        // output the set to display on the view
        output.set(ATTRIBUTE_FACULTY_LIST, setOfFacultyInfo);

        // is applicant?
        if (isApplicant) {

            // add info if he has exceeded parallel study limit
            boolean exceededStudyLimit = av.hasExceededParallelStudyLimit(currentUserId);
            output.set(ATTRIBUTE_EXCEEDED_STUDY_LIMIT, exceededStudyLimit);

        }

    }

}
