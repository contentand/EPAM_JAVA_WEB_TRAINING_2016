package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.*;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Role;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * A Request decorator that provides a handful set of
 * methods allowing to send error, success or info messages
 * in a language local to the invoker.
 *
 * Motivation: avoid code repetitions.
 */
public class OutputTool implements Localize {

    protected Request request;
    protected Localizer localizer;

    public OutputTool(Request request, Localizer localizer) {
        if (request == null) throw new NullPointerException();
        this.request = request;
        this.localizer = localizer;
    }

    /**
     * Finds MessageFormat pattern corresponding to the patternKey in Resource Bundle.
     * Creates a String using the pattern and passed patternArguments.
     * Appends the created string to the corresponding Attribute value in Session
     * using <br> to concatenate several messages.
     *
     * @param patternKey message key to find local value.
     * @param patternArguments any other objects that can be con
     */
    public void setSuccessMsg(Value patternKey, Object ... patternArguments) {
        String previousMsg = getPrevious(ATTRIBUTE_MSG_SUCCESS);
        String currentMsg = translate(patternKey, patternArguments);
        request.setSessionAttribute(ATTRIBUTE_MSG_SUCCESS, previousMsg + currentMsg);
    }

    /**
     * Finds MessageFormat pattern corresponding to the patternKey in Resource Bundle.
     * Creates a String using the pattern and passed patternArguments.
     * Appends the created string to the corresponding Attribute value in Session
     * using <br> to concatenate several messages.
     *
     * @param patternKey message key to find local value.
     * @param patternArguments any other objects that can be con
     */
    public void setMsg(Value patternKey, Object ... patternArguments) {
        String previousMsg = getPrevious(ATTRIBUTE_MSG);
        String currentMsg = translate(patternKey, patternArguments);
        request.setSessionAttribute(ATTRIBUTE_MSG, previousMsg + currentMsg);
    }

    /**
     * Finds MessageFormat pattern corresponding to the patternKey in Resource Bundle.
     * Creates a String using the pattern and passed patternArguments.
     * Appends the created string to the corresponding Attribute value in Session
     * using <br> to concatenate several messages.
     *
     * @param patternKey message key to find local value.
     * @param patternArguments any other objects that can be con
     */
    public void setErrorMsg(Value patternKey, Object ... patternArguments) {
        String previousMsg = getPrevious(ATTRIBUTE_MSG_ERROR);
        String currentMsg = translate(patternKey, patternArguments);
        request.setSessionAttribute(ATTRIBUTE_MSG_ERROR, previousMsg + currentMsg);
    }

    /**
     * Sets session attribute.
     * @param attributeName name
     * @param attributeValue value
     */
    public void set(String attributeName, Object attributeValue) {
        request.setSessionAttribute(attributeName, attributeValue);
    }

    /**
     * Sets role of current user.
     * @param roleAsString role
     * @throws IllegalArgumentException if role is not supported
     */
    public void setRole(String roleAsString) throws IllegalArgumentException {
        Role role = Role.valueOf(roleAsString);
        request.setSessionAttribute(ROLE, role);
    }

    /**
     * Sets locale for current user. If locale is not supported,
     * the application default locale is set.
     * @param locale locale
     * @return locale that has finally been set
     */
    public Locale setLocale(Locale locale) {
        locale = localizer.adjustLocale(locale);
        ResourceBundle bundle = localizer.getBundle(locale);
        request.setSessionAttribute(BUNDLE, bundle);
        request.setSessionAttribute(LOCALE, locale);
        return locale;
    }

    /**
     * Forgets information about the current user role, locale and id.
     */
    public void invalidateSession() {
        request.setSessionAttribute(USER_ID, null);
        request.setSessionAttribute(ROLE, null);
        request.setSessionAttribute(LOCALE, null);
    }

    /**
     * Sets user id for current user.
     * @param id of the user
     */
    public void setUserId(Long id) {
        request.setSessionAttribute(USER_ID, id);
    }

    @Override
    public <T extends NameLocalizable> String getLocalName(T element) {
        return localizer.getLocalName(element, getLocale());
    }

    @Override
    public <T extends FirstLastNameLocalizable> String getLocalFirstName(T element) {
        return localizer.getLocalFirstName(element, getLocale());
    }

    @Override
    public <T extends FirstLastNameLocalizable> String getLocalLastName(T element) {
        return localizer.getLocalLastName(element, getLocale());
    }

    @Override
    public <T extends DescriptionLocalizable> String getLocalDescription(T element) {
        return localizer.getLocalDescription(element, getLocale());
    }

    @Override
    public Locale getLocale() {return getResourceBundle().getLocale();}


    // Private helper methods are listed below

    private String translate(Value messageToInternationalize, Object ... patternArguments) {
        // recursively translates objects that have been passed.
        translate(patternArguments);

        String token = messageToInternationalize.name();
        String patternedMsg = getResourceBundle().getString(token);
        return MessageFormat.format(patternedMsg, patternArguments);
    }

    private void translate(Object[] patternArguments) {
        for (int index = 0; index < patternArguments.length; index++) {
            // translates only in case they are translatable.
            if (patternArguments[index] instanceof Value) {
                patternArguments[index] = translate((Value) patternArguments[index]);
            }
        }
    }

    // gets string value currently stored in session under the indicated attribute name.
    private String getPrevious(String attributeName) {
        String previousMessage = (String) request.getSessionAttribute(attributeName);
        return (previousMessage == null) ? "" : previousMessage + "<br>";
    }

    // gets resource bundle
    private ResourceBundle getResourceBundle() {
        return (ResourceBundle) request.getSessionAttribute(BUNDLE);
    }
}
