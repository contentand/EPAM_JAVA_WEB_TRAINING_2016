package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.i18n.Translatable;
import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.api.Request;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * A Request decorator that provides a handful set of
 * methods allowing to send error, success or info messages
 * in a language local to the invoker.
 *
 * Motivation: avoid code repetitions.
 */
public class OutputTool {

    protected Request request;

    public OutputTool(Request request) {
        if (request == null) throw new NullPointerException();
        this.request = request;
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

    public void setErrorMsg(Translatable e) { // TODO : DEPRECATE ?
        String previousMsg = getPrevious(ATTRIBUTE_MSG_ERROR);
        String currentMsg = translate(e);
        request.setSessionAttribute(ATTRIBUTE_MSG_ERROR, previousMsg + currentMsg);
    }

    public void set(String attributeName, Object attributeValue) {
        request.setSessionAttribute(attributeName, attributeValue);
    }


    // Private helper methods are listed below

    private String translate(Value messageToInternationalize, Object ... patternArguments) {

        translate(patternArguments); // TODO : DEPRECATE ?

        String token = messageToInternationalize.name();
        String patternedMsg = getResourceBundle().getString(token);
        return MessageFormat.format(patternedMsg, patternArguments);
    }

    private void translate(Object[] patternArguments) { // TODO : DEPRECATE ?
        for (int index = 0; index < patternArguments.length; index++) {
            if (patternArguments[index] instanceof Value) {
                patternArguments[index] = translate((Value) patternArguments[index]);
            }
        }
    }

    private String translate(Translatable e) { // TODO : DEPRECATE ?
        return translate(e.getPattern(), e.getArguments());
    } // TODO DEPRECATE ?


    private String getPrevious(String attributeName) {
        String previousMessage = (String) request.getSessionAttribute(attributeName);
        return (previousMessage == null) ? "" : previousMessage + "<br>";
    }

    private ResourceBundle getResourceBundle() {
        return (ResourceBundle) request.getSessionAttribute(BUNDLE);
    }
}
