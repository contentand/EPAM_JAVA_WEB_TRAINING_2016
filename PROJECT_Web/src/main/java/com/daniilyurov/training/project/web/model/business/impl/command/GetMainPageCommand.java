package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ApplicationValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;

import java.util.Set;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.*;

public class GetMainPageCommand extends AbstractGeneralRoleCommand {

    private ServicesFactory servicesFactory;

    @Provided
    public void setDependencies(ServicesFactory servicesFactory) {
        this.servicesFactory = servicesFactory;
    }

    @Override
    protected String executeAsGuest(Request request) throws Exception {
        sendInformationAboutAllFaculties(request, false);
        return GET_MAIN_PAGE;
    }

    @Override
    protected String executeAsApplicant(Request request) throws Exception {
        sendInformationAboutAllFaculties(request, true);
        return GET_MAIN_PAGE;
    }

    @Override
    protected String executeAsAdministrator(Request request) throws Exception {
        sendInformationAboutAllFaculties(request, false);
        return GET_MAIN_PAGE;
    }

    // Private helper methods are listed below

    private void sendInformationAboutAllFaculties(Request request,
                                                  boolean isApplicant) throws Exception {

        // setup dependencies
        FacultyService fs = servicesFactory.getFacultyService();
        ApplicationService at = servicesFactory.getApplicationService();
        ApplicationValidator av = validatorFactory.getApplicationValidator(request);
        UserValidator userValidator = validatorFactory.getUserValidator(request);
        OutputTool output = outputToolFactory.getInstance(request);

        // let the view know the header should be adjusted for main page
        output.set(ATTRIBUTE_IS_MAIN, true);

        // get current user id if present
        Long currentUserId = userValidator.getCurrentUserId();

        // get statistical info about all faculties [and (optionally) how they relate to current user]
        Set<FacultyInfoItem> setOfFacultyInfo = fs.getSetOfFacultyInfo(at, output, currentUserId);

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
