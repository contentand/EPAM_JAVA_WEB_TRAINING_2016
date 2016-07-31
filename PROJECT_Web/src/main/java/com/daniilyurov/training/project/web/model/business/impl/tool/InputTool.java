package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.model.business.api.Request;

public class InputTool {

    protected Request request;

    public InputTool(Request request) {
        this.request = request;
    }

    public String getParameter(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null || values.length == 0) return null;
        return values[0];
    }

    public String getIdFromUri() {
        // extracts digits and parses them
        return request.getUrlPath().replaceAll("\\D", "");
    }
}
