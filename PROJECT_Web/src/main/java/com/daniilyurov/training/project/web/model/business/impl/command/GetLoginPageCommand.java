package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_IS_LOGIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_LOGIN_PAGE;

public class GetLoginPageCommand extends AbstractUnauthorizedRoleCommand {

    @Override
    protected String executeAsGuest(Provider provider) throws Exception {

        // setup dependencies
        OutputTool output = provider.getOutputTool();

        // let jsp know the header should be adjusted for login page
        output.set(ATTRIBUTE_IS_LOGIN_PAGE, true);

        // allow going to the page
        return GET_LOGIN_PAGE;
    }
}
