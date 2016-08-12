package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.DescriptionLocalizable;
import com.daniilyurov.training.project.web.i18n.FirstLastNameLocalizable;
import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.i18n.NameLocalizable;

import java.util.Locale;

/**
 * Created by Daniel Yurov on 11.08.2016.
 */
public interface Localize {
    <T extends NameLocalizable> String getLocalName(T element);

    <T extends FirstLastNameLocalizable> String getLocalFirstName(T element);

    <T extends FirstLastNameLocalizable> String getLocalLastName(T element);

    <T extends DescriptionLocalizable> String getLocalDescription(T element);

    Locale getLocale();
}
