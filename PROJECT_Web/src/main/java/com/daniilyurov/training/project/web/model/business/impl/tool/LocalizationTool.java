package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.DescriptionLocalizable;
import com.daniilyurov.training.project.web.i18n.FirstLastNameLocalizable;
import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.i18n.NameLocalizable;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.utility.ContextAttributes;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.BUNDLE;

public class LocalizationTool {

    protected Request request;

    public LocalizationTool(Request request) {
        if (request == null) throw new NullPointerException();
        this.request = request;
    }

    public Localizer getLocalizer() {
        return (Localizer) request.getContextAttribute(ContextAttributes.LOCALIZER);
    }

    public <T extends NameLocalizable> String getLocalName(T element) {
        return getLocalizer().getLocalName(element, getLocale());
    }

    public <T extends FirstLastNameLocalizable> String getLocalFirstName(T element) {
        return getLocalizer().getLocalFirstName(element, getLocale());
    }

    public <T extends FirstLastNameLocalizable> String getLocalLastName(T element) {
        return getLocalizer().getLocalLastName(element, getLocale());
    }

    public <T extends DescriptionLocalizable> String getLocalDescription(T element) {
        return getLocalizer().getLocalDescription(element, getLocale());
    }

    public Locale getLocale() {return getResourceBundle().getLocale();}

    private ResourceBundle getResourceBundle() {
        return (ResourceBundle) request.getSessionAttribute(BUNDLE);
    }
}
