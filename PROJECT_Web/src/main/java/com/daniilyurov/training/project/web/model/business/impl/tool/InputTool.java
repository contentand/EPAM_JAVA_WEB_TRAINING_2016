package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Role;

import java.util.Locale;
import java.util.Optional;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.LOCALE;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ROLE;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.USER_ID;

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

    public Role getRole() {
        return (Role) request.getSessionAttribute(ROLE);
    }

    public Locale getLocale() {
        return (Locale) request.getSessionAttribute(LOCALE);
    }

    public Long getUserId() {
        return (Long) request.getSessionAttribute(USER_ID);
    }
}
