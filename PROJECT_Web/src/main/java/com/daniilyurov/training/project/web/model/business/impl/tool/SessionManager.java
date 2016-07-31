package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Role;

import static com.daniilyurov.training.project.web.utility.ContextAttributes.*;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class SessionManager {

    protected Request request;

    public SessionManager(Request request) {
        this.request = request;
    }

    public void setRole(String roleAsString) throws IllegalArgumentException {
        Role role = Role.valueOf(roleAsString);
        request.setSessionAttribute(AUTHORITY, role);
    }

    public Role getRole() {
        return (Role) request.getSessionAttribute(AUTHORITY);
    }

    public Locale setLocale(Locale locale) {
        Localizer localizer = (Localizer) request.getContextAttribute(LOCALIZER);
        locale = localizer.adjustLocale(locale);
        ResourceBundle bundle = localizer.getBundle(locale);
        request.setSessionAttribute(BUNDLE, bundle);
        request.setSessionAttribute(LOCALE, locale);
        return locale;
    }

    public void invalidate() {
        request.setSessionAttribute(USER_ID, null);
        request.setSessionAttribute(AUTHORITY, null);
    }

    public void setUserId(Long id) {
        request.setSessionAttribute(USER_ID, id);
    }

    public Optional<Long> getUserId() {
        return Optional.ofNullable((Long) request.getSessionAttribute(USER_ID));
    }

    public Locale getLocale() {
        return (Locale) request.getSessionAttribute(LOCALE);
    }
}
