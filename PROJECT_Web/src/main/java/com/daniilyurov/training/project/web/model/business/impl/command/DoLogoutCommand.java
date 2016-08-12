package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.i18n.Value.SUC_SUCCESSFUL_LOGOUT;

public class DoLogoutCommand extends AbstractAuthorizedRoleCommand {

    @Override
    protected String executeAsApplicant(Request request) throws Exception {
        return doLogout(request);
    }

    @Override
    protected String executeAsAdministrator(Request request) throws Exception {
        return doLogout(request);
    }

    // Private helper methods are listed below

    private String doLogout(Request request) throws Exception {

        // setup dependencies
        OutputTool output = outputToolFactory.getInstance(request);

        // forget user
        output.invalidate();

        // notify about success
        output.setSuccessMsg(SUC_SUCCESSFUL_LOGOUT);

        // go to
        return GET_MAIN_PAGE;

    }
}
