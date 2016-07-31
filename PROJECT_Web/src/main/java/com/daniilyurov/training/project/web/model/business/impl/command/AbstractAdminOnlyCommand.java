package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.i18n.Value.ERR_PAGE_NOT_FOUND;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_MAIN_PAGE;

public abstract class AbstractAdminOnlyCommand extends AbstractGeneralRoleCommand {

    /**
     * Default final strategy for invokers with role Guest.
     */
    @Override
    protected final String executeAsGuest(Provider provider) throws Exception {
        OutputTool out = provider.getOutputTool();
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;
    }


    /**
     * Default final strategy for invokers with role User.
     */
    @Override
    protected final String executeAsApplicant(Provider provider) throws Exception {
        OutputTool out = provider.getOutputTool();
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;

    }
}
