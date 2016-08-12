package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.i18n.Value.ERR_PAGE_NOT_FOUND;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;

public abstract class AbstractAdminOnlyCommand extends AbstractAuthorizedRoleCommand {

    /**
     * Default final strategy for invokers with role User.
     */
    @Override
    protected final String executeAsApplicant(Request request) throws Exception {
        OutputTool out = outputToolFactory.getInstance(request);
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;

    }
}
