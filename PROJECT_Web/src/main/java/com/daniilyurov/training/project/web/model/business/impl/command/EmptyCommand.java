package com.daniilyurov.training.project.web.model.business.impl.command;

import static com.daniilyurov.training.project.web.model.business.impl.Key.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;

/**
 * This command returns the main page and notifies
 * the invoker that the application failed to understand the request.
 */
public class EmptyCommand implements Command {

    private OutputToolFactory outputToolFactory;

    @Provided
    public void setOutputToolFactory(OutputToolFactory outputToolFactory) {
        this.outputToolFactory = outputToolFactory;
    }

    @Override
    public String execute(Request request) {

        OutputTool out = outputToolFactory.getInstance(request);
        out.setErrorMsg(ERR_PAGE_NOT_FOUND);
        return GET_MAIN_PAGE;

    }

}
