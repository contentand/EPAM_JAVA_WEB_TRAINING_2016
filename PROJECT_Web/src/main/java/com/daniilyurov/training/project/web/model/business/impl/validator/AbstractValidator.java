package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

import static com.daniilyurov.training.project.web.i18n.Value.*;

import java.util.Collection;

public abstract class AbstractValidator {

    protected OutputTool output;
    protected Value currentField;

    protected double parseDouble(String input) {
        try {
            return Double.valueOf(input);
        } catch (NumberFormatException e) {
            output.setErrorMsg(ERR_CANNOT_PARSE_X, currentField);
            throw new ValidationException();
        }
    }

    protected long parseLong(String input) {
        try {
            return Long.valueOf(input);
        } catch (NumberFormatException e) {
            output.setErrorMsg(ERR_CANNOT_PARSE_X, currentField);
            throw new ValidationException();
        }
    }

    protected void shouldBeNotNullNotEmpty(String stringToValidate) {
        shouldNotBeNull(stringToValidate);
        shouldNotBeEmpty(stringToValidate);
    }


    protected void shouldNotBeNull(Object objectToValidate) {
        if (objectToValidate == null) {
            output.setErrorMsg(ERR_X_IS_EMPTY, currentField);
            throw new ValidationException();
        }
    }

    protected void shouldNotBeEmpty(String stringToValidate) {
        if (stringToValidate.length() == 0) {
            output.setErrorMsg(ERR_X_IS_EMPTY, currentField);
            throw new ValidationException();
        }
    }

    protected void shouldNotBeEmpty(Collection collectionToValidate) {
        if (collectionToValidate.isEmpty()) {
            output.setErrorMsg(ERR_X_IS_EMPTY, currentField);
            throw new ValidationException();
        }
    }

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

    protected void shouldContainOnlyCyrillicCharsAndApostropheAndDash(String stringToValidate) {
        if (!stringToValidate.matches("[А-Яа-я\\-'\\s]*")) {
            output.setErrorMsg(ERR_X_INVALID_CYRILLIC, currentField);
            throw new ValidationException();
        }
    }

    protected void shouldContainOnlyLatinCharsAndApostropheAndDash(String stringToValidate) {
        if (!stringToValidate.matches("[A-ZÖÜÄa-zöüäß\\-'\\s]*")) {
            output.setErrorMsg(ERR_X_INVALID_LATIN, currentField);
            throw new ValidationException();
        }
    }

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

    protected void shouldBeValidEmail(String value) {

        if (!value.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) { // simple reg expr.
            output.setErrorMsg(ERR_X_INVALID, currentField);
            throw new ValidationException();
        }
    }
}
