package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ResultsService;
import com.daniilyurov.training.project.web.model.business.impl.service.SubjectService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import java.util.List;
import java.util.Map;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.*;
import static com.daniilyurov.training.project.web.utility.RequestParameters.*;

/**
 *  Collects and sets the necessary information for displaying
 *  Current User Info Page.
 */
public class GetCurrentUserInfoPageCommand extends AbstractAuthorizedRoleCommand {
    @Override
    protected String executeAsApplicant(Request request) throws Exception {
        return sharedLogic(request);
    }

    @Override
    protected String executeAsAdministrator(Request request) throws Exception {
        return sharedLogic(request);
    }

    // Private helper methods are listed below.

    private String sharedLogic(Request request) throws Exception {

        // setup dependencies
        UserValidator userValidator = validatorFactory.getUserValidator(request);
        ResultsService resultsService = servicesFactory.getResultsService();
        SubjectService subjectService = servicesFactory.getSubjectService();
        OutputTool output = outputToolFactory.getInstance(request);

        // let jsp know the header should be adjusted for user info page
        output.set(ATTRIBUTE_IS_USER_INFO_PAGE, true);

        // get all results of current user
        User currentUser = userValidator.getCurrentUser().get();
        Result[] resultsOfCurrentUser = resultsService.getAllOf(currentUser);

        // output the results
        for (Result result : resultsOfCurrentUser) {
            Subject subject = result.getSubject();
            String key = PREFIX_PARAMETER_SUBJECT_ID + subject.getId();
            double value = result.getResult();
            output.set(key, value);
        }

        // get the list of all subjects available
        List<Map.Entry<String, String>> subjectList = subjectService
                .getListOfSubjectIdsAndTheirLocalNames(output);

        // make this list available for the view to display
        output.set(ATTRIBUTE_SUBJECT_LIST, subjectList);

        // output user info
        output.set(ATTRIBUTE_USER_INFO, currentUser);

        return GET_CURRENT_USER_INFO;
    }
}
