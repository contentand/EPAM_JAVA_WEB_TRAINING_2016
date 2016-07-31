package com.daniilyurov.training.project.web.model.business.impl.command;


import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.i18n.Value.ERR_PAGE_NOT_FOUND;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_MAIN_PAGE;

/**
 * This class provides final implementation of executeAsGuest
 * strategy.
 *
 * Descendants should only implement:
 * 1. executeAsAdministrator() -- instructions if the invoker is an administrator
 * 2. executeAsApplicant() -- instructions if the invoker is an ordinary authorized user
 *
 * @author Daniil Yurov
 */
public abstract class AbstractAuthorizedRoleCommand extends AbstractGeneralRoleCommand {

    /**
     * Default final strategy for invokers with role Guest.
     */
    @Override
    protected final String executeAsGuest(Provider provider) throws Exception {
        OutputTool out = provider.getOutputTool();
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;

    }
}
