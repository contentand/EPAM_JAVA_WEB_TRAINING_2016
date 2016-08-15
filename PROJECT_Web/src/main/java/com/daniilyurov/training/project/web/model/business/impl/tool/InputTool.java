package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Role;

import java.util.Locale;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.LOCALE;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ROLE;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.USER_ID;

/**
 * Utility class to encapsulate methods
 * that work with request parameters, session container and request url.
 */
public class InputTool {

    protected Request request;

    public InputTool(Request request) {
        this.request = request;
    }

    /**
     * Returns the first parameter value corresponding to the parameter name
     * @param name to search for
     * @return first parameter value
     */
    public String getParameter(String name) {
        String[] values = request.getParameterValues(name);
        if (values == null || values.length == 0) return null;
        return values[0];
    }

    /**
     * Returns numeric value extracted from the URL.
     * @return number as String
     */
    public String getIdFromUri() {
        // extracts digits and parses them
        return request.getUrlPath().replaceAll("\\D", "");
    }

    /**
     * Returns role of currently logged in user.
     * @return role
     */
    public Role getRole() {
        return (Role) request.getSessionAttribute(ROLE);
    }

    /**
     * Returns locale of currently logged in user.
     * @return locale
     */
    public Locale getLocale() {
        return (Locale) request.getSessionAttribute(LOCALE);
    }

    /**
     * Returns id of currently logged in user or null if user is a GUEST
     * @return user id
     */
    public Long getUserId() {
        return (Long) request.getSessionAttribute(USER_ID);
    }
}
