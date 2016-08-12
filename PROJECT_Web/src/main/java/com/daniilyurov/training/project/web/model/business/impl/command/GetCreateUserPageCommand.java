package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.service.SubjectService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;

import java.util.List;
import java.util.Map;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_USER_REGISTRATION_FORM;

public class GetCreateUserPageCommand extends AbstractUnauthorizedRoleCommand {

    private ServicesFactory servicesFactory;

    @Provided
    public void setDependencies(ServicesFactory servicesFactory) {
        this.servicesFactory = servicesFactory;
    }

    @Override
    protected String executeAsGuest(Request request) throws Exception {

        // setup dependencies
        OutputTool output = outputToolFactory.getInstance(request);
        SubjectService subjectService = servicesFactory.getSubjectService();

        // let jsp know the header should be adjusted for user registration page
        output.set(ATTRIBUTE_IS_REGISTRATION_PAGE, true);

        // get the list of all subjects available
        List<Map.Entry<String, String>> subjectList = subjectService
                .getMapWithSubjectIdsAndTheirLocalNames(output);

        // make this list available for the view to display
        output.set(ATTRIBUTE_SUBJECT_LIST, subjectList);
        return GET_USER_REGISTRATION_FORM;

    }
}
