package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_IS_LOGIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_LOGIN_PAGE;

public class GetLoginPageCommand extends AbstractUnauthorizedRoleCommand {

    @Override
    protected String executeAsGuest(Request request) throws Exception {

        // setup dependencies
        OutputTool output = outputToolFactory.getInstance(request);

        // let jsp know the header should be adjusted for login page
        output.set(ATTRIBUTE_IS_LOGIN_PAGE, true);

        // allow going to the page
        return GET_LOGIN_PAGE;
    }
}
