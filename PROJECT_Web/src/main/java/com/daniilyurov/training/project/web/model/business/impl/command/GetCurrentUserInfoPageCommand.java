package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.ResultsService;
import com.daniilyurov.training.project.web.model.business.impl.service.SubjectService;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import java.util.List;
import java.util.Map;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;
import static com.daniilyurov.training.project.web.utility.RequestParameters.*;

public class GetCurrentUserInfoPageCommand extends AbstractAuthorizedRoleCommand {
    @Override
    protected String executeAsApplicant(Provider provider) throws Exception {
        return sharedLogic(provider);
    }

    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {
        return sharedLogic(provider);
    }

    // Private helper methods are listed below.

    private String sharedLogic(Provider provider) throws Exception {

        // setup dependencies
        UserService userService = provider.getUserService();
        ResultsService resultsService = provider.getResultsService();
        SubjectService subjectService = provider.getSubjectService();
        SessionManager management = provider.getSessionManager();
        LocalizationTool localization = provider.getLocalizationTool();
        OutputTool output = provider.getOutputTool();

        // let jsp know the header should be adjusted for user info page
        output.set(ATTRIBUTE_IS_USER_INFO_PAGE, true);

        // get all results of current user
        Long currentUserId = management.getUserId().get();
        User currentUser = userService.getUser(currentUserId);
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
                .getMapWithSubjectIdsAndTheirLocalNames(localization);

        // make this list available for the view to display
        output.set(ATTRIBUTE_SUBJECT_LIST, subjectList);

        // output user info
        output.set(ATTRIBUTE_USER_INFO, currentUser);

        return GET_CURRENT_USER_INFO;
    }
}
