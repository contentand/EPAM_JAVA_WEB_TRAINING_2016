package com.daniilyurov.training.project.web.model.business.impl.command;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.Request;

/**
 * This command returns the main page and notifies
 * the invoker that the application failed to understand the request.
 */
public class EmptyCommand implements Command {

    @Override
    public String execute(Request request) {

        OutputTool out = new OutputTool(request);
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;

    }

}
