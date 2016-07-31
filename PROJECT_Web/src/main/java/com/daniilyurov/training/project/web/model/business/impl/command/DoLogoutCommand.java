package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.i18n.Value.SUC_SUCCESSFUL_LOGOUT;

public class DoLogoutCommand extends AbstractAuthorizedRoleCommand {

    @Override
    protected String executeAsApplicant(Provider provider) throws Exception {
        return doLogout(provider);
    }

    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {
        return doLogout(provider);
    }

    // Private helper methods are listed below

    private String doLogout(Provider provider) throws Exception {

        // setup dependencies
        OutputTool output = provider.getOutputTool();
        SessionManager session = provider.getSessionManager();

        // forget user
        session.invalidate();

        // notify about success
        output.setSuccessMsg(SUC_SUCCESSFUL_LOGOUT);

        // go to
        return GET_MAIN_PAGE;

    }
}
