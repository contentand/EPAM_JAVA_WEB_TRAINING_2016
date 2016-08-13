package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.i18n.Value.*;

import java.util.Collection;

/**
 * AbstractValidator assembles common
 * methods shared amongst all concrete validators.
 *
 * @author Daniil Yurov
 */
public abstract class AbstractValidator {

    protected OutputTool output; // for setting error messages
    protected Value currentField; // for specifying which exact field is wrong in case of error

    /**
     * Parses double from String or sets error message and throws ValidationException
     * @param input String to parse
     * @return parsed double
     */
    protected double parseDouble(String input) {
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException e) {
            output.setErrorMsg(ERR_CANNOT_PARSE_X, currentField);
            throw new ValidationException();
        }
    }

    /**
     * Parses long from String or sets error message and throws ValidationException
     * @param input String to parse
     * @return parsed long
     */
    protected long parseLong(String input) {
        try {
            return Long.valueOf(input);
        } catch (NumberFormatException e) {
            output.setErrorMsg(ERR_CANNOT_PARSE_X, currentField);
            throw new ValidationException();
        }
    }

    /**
     * If the string is null or empty, an error message is set and ValidationException is thrown
     * @param stringToValidate string to validate
     */
    protected void shouldBeNotNullNotEmpty(String stringToValidate) {
        shouldNotBeNull(stringToValidate);
        shouldNotBeEmpty(stringToValidate);
    }

    /**
     * If the string is null, an error message is set and ValidationException is thrown
     * @param objectToValidate string to validate
     */
    protected void shouldNotBeNull(Object objectToValidate) {
        if (objectToValidate == null) {
            output.setErrorMsg(ERR_X_IS_EMPTY, currentField);
            throw new ValidationException();
        }
    }

    /**
     * If the string is empty, an error message is set and ValidationException is thrown
     * @param stringToValidate string to validate
     */
    protected void shouldNotBeEmpty(String stringToValidate) {
        if (stringToValidate.length() == 0) {
            output.setErrorMsg(ERR_X_IS_EMPTY, currentField);
            throw new ValidationException();
        }
    }

    /**
     * If the collection is empty, an error message is set and ValidationException is thrown
     * @param collectionToValidate collection to validate
     */
    protected void shouldNotBeEmpty(Collection collectionToValidate) {
        if (collectionToValidate.isEmpty()) {
            output.setErrorMsg(ERR_X_IS_EMPTY, currentField);
            throw new ValidationException();
        }
    }

    /**
     * If the numeric value is outside the bounds, a corresponding error is set and
     * ValidationException is thrown.
     * @param value numeric value to test
     * @param fromInclusive lower bound
     * @param toInclusive upper bound
     */
    protected void shouldBeWithinRange(double value, double fromInclusive, double toInclusive) {
        if (value > toInclusive) {
            output.setErrorMsg(ERR_X_IS_OVER_Y, currentField, toInclusive);
            throw new ValidationException();
        }
        if (value < fromInclusive) {
            output.setErrorMsg(ERR_X_IS_BELOW_Y, currentField, fromInclusive);
            throw new ValidationException();
        }
    }

    /**
     * If the string contains any characters that are not
     * upper and lowercase letters from а to я of Russian alphabet
     * or dash(-), white space or apostrophe('),
     * a corresponding error is set and ValidationException is thrown.
     *
     * @param stringToValidate string to validate
     */
    protected void shouldContainOnlyCyrillicCharsAndApostropheAndDash(String stringToValidate) {
        if (!stringToValidate.matches("[А-Яа-я\\-'\\s]*")) {
            output.setErrorMsg(ERR_X_INVALID_CYRILLIC, currentField);
            throw new ValidationException();
        }
    }

    /**
     * If the string contains any characters that are not
     * upper and lowercase letters from a to z of English/German alphabet
     * or dash(-), white space or apostrophe('),
     * a corresponding error is set and ValidationException is thrown.
     *
     * @param stringToValidate string to validate
     */
    protected void shouldContainOnlyLatinCharsAndApostropheAndDash(String stringToValidate) {
        if (!stringToValidate.matches("[A-ZÖÜÄa-zöüäß\\-'\\s]*")) {
            output.setErrorMsg(ERR_X_INVALID_LATIN, currentField);
            throw new ValidationException();
        }
    }

    /**
     * If the string size is outside the bounds, a corresponding error is set and
     * ValidationException is thrown.
     * @param value string value the size of which will be tested
     * @param fromInclusive lower bound
     * @param toInclusive upper bound
     */
    protected void shouldBeWithinRange(String value, double fromInclusive, double toInclusive) {
        if (value.length() > toInclusive) {
            output.setErrorMsg(ERR_X_IS_OVER_Y, currentField, toInclusive);
            throw new ValidationException();
        }
        if (value.length() < fromInclusive) {
            output.setErrorMsg(ERR_X_IS_BELOW_Y, currentField, fromInclusive);
            throw new ValidationException();
        }
    }

    /**
     * If the string does not contain basic properties of e-mail address,
     * a corresponding error is set and ValidationException is thrown.
     * @param value to be tested
     */
    protected void shouldBeValidEmail(String value) {

        if (!value.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) { // simple reg expr.
            output.setErrorMsg(ERR_X_INVALID, currentField);
            throw new ValidationException();
        }
    }
}
