package com.daniilyurov.training.project.web.model.business.impl.command;


import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;

import static com.daniilyurov.training.project.web.i18n.Value.ERR_PAGE_NOT_FOUND;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;

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

    protected ServicesFactory servicesFactory;

    @Provided
    public void setServicesFactory(ServicesFactory servicesFactory) {
        this.servicesFactory = servicesFactory;
    }

    /**
     * Default final strategy for invokers with role Guest.
     */
    @Override
    protected final String executeAsGuest(Request request) throws Exception {
        OutputTool out = outputToolFactory.getInstance(request);
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;

    }
}
