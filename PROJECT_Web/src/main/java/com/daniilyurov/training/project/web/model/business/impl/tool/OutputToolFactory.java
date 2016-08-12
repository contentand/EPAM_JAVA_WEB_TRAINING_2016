package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

public class OutputToolFactory {

    private Localizer localizer;

    public OutputToolFactory(Localizer localizer) {
        this.localizer = localizer;
    }

    public OutputTool getInstance(Request request) {
        return new OutputTool(request, localizer);
    }
}
