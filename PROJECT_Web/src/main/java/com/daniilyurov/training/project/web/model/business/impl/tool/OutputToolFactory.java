package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Request;

/**
 * OutputToolFactory contains necessary application scoped
 * components to create instances of OutputTool.
 * It provides method to create a new instance of OutputTool out of the request.
 */
public class OutputToolFactory {

    private Localizer localizer;

    public OutputToolFactory(Localizer localizer) {
        this.localizer = localizer;
    }

    /**
     * Creates an instance of output tool constructed from the request.
     * @param request that should be wrapped by output tool
     * @return output tool
     */
    public OutputTool getInstance(Request request) {
        return new OutputTool(request, localizer);
    }
}
