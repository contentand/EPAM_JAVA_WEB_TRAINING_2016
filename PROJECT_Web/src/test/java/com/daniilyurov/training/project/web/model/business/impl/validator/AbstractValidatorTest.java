package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.i18n.Value;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

import static com.daniilyurov.training.project.web.i18n.Value.*;
import static org.junit.Assert.*;

public class AbstractValidatorTest extends Mockito {

    AbstractValidator validator;
    OutputTool outputTool;
    Value field;

    @Before
    public void setup() {
        this.outputTool = mock(OutputTool.class);
        this.field = Value.FIELD_NAME; // arbitrary value
        this.validator = new AbstractValidator() {
            {
                super.output = outputTool;
                super.currentField = field;
            }
        };
    }

    @Test
    public void validParseDouble_returnsValue() throws Exception {
        // execute
        double result = validator.parseDouble("2.333");
        // verify
        assertEquals(2.333D, result, 0.00001);
    }

    @Test(expected = ValidationException.class)
    public void wrongParseDouble_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.parseDouble("blah-blah-blah");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_CANNOT_PARSE_X), eq(field));
            throw e;
        }
    }


    @Test
    public void correctParseLong_returnsValue() throws Exception {
        // execute
        long result = validator.parseLong("1234");
        // verify
        assertEquals(1234L, result);
    }

    @Test(expected = ValidationException.class)
    public void wrongParseLong_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.parseLong("blah-blah-blah");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_CANNOT_PARSE_X), eq(field));
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldBeNotNullNotEmpty_null_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.shouldBeNotNullNotEmpty(null);
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_EMPTY), eq(field));
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldBeNotNullNotEmpty_empty_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.shouldBeNotNullNotEmpty("");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_EMPTY), eq(field));
            throw e;
        }
    }

    @Test
    public void shouldBeNotNullNotEmpty_valid_doesNothing() throws Exception {
        // execute
        validator.shouldBeNotNullNotEmpty("Correct Line");
    }

    @Test(expected = ValidationException.class)
    public void shouldNotBeNull_null_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.shouldNotBeNull(null);
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_EMPTY), eq(field));
            throw e;
        }
    }

    @Test
    public void shouldNotBeNull_valid_doesNothing() throws Exception {
        // execute
        validator.shouldNotBeNull(new Object());
    }


    @Test(expected = ValidationException.class)
    public void shouldNotBeEmpty_empty_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.shouldNotBeEmpty("");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_EMPTY), eq(field));
            throw e;
        }
    }

    @Test
    public void shouldNotBeEmpty_valid_doesNothing() throws Exception {
        // execute
        validator.shouldNotBeEmpty("Correct Line");
    }

    @Test(expected = ValidationException.class)
    public void shouldNotBeEmptyCollection_empty_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.shouldNotBeEmpty(Collections.emptySet());
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_EMPTY), eq(field));
            throw e;
        }
    }

    @Test
    public void shouldNotBeEmptyCollection_valid_doesNothing() throws Exception {
        // execute
        validator.shouldNotBeEmpty(Arrays.asList("one", "two", "three"));
    }


    @Test(expected = ValidationException.class)
    public void shouldBeWithinRange_above_outputsErrorAndThrowsValidationException() throws Exception {
        // setup
        double value = 100D;
        double fromInclusive = 50D;
        double toInclusive = 0D;

        try {
            // execute
            validator.shouldBeWithinRange(value, fromInclusive, toInclusive);
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_OVER_Y), eq(field), eq(toInclusive));
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldBeWithinRange_lower_outputsErrorAndThrowsValidationException() throws Exception {
        // setup
        double value = -100D;
        double fromInclusive = 50D;
        double toInclusive = 0D;

        try {
            // execute
            validator.shouldBeWithinRange(value, fromInclusive, toInclusive);
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_BELOW_Y), eq(field), eq(fromInclusive));
            throw e;
        }
    }

    @Test
    public void shouldBeWithinRange_valid_doesNothing() throws Exception {
        // execute
        validator.shouldBeWithinRange(10D, 1D, 20D);
    }

    @Test // When correct - does nothing
    public void testShouldContainOnlyCyrillicCharsAndApostropheAndDash_correct() throws Exception {
        // execute
        validator.shouldContainOnlyCyrillicCharsAndApostropheAndDash("Марьям-Вильгельм");
    }

    @Test(expected = ValidationException.class)
    // When wrong - outputs corresponding message and throws ValidationException
    public void testShouldContainOnlyCyrillicCharsAndApostropheAndDash_invalid() throws Exception {
        try {
            // execute
            validator.shouldContainOnlyCyrillicCharsAndApostropheAndDash("Mariam-Wilhelm");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_INVALID_CYRILLIC), eq(field));
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    // When wrong - outputs corresponding message and throws ValidationException
    public void testShouldContainOnlyLatinCharsAndApostropheAndDash_invalid() throws Exception {
        try {
            // execute
            validator.shouldContainOnlyLatinCharsAndApostropheAndDash("Марьям-Вильгельм");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_INVALID_LATIN), eq(field));
            throw e;
        }
    }

    @Test // When correct - does nothing
    public void testShouldContainOnlyLatinCharsAndApostropheAndDash_correct() throws Exception {
        // execute
        validator.shouldContainOnlyLatinCharsAndApostropheAndDash("Mariam-Wilhelm");
    }

    @Test(expected = ValidationException.class)
    public void shouldBeWithinRange_smaller_outputsErrorAndThrowsValidationException() throws Exception {
        // setup
        double fromInclusive = 5;
        double toInclusive = 10;
        try {
            // execute
            validator.shouldBeWithinRange("Oh", fromInclusive, toInclusive);
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_BELOW_Y), eq(field), eq(fromInclusive));
            throw e;
        }
    }

    @Test(expected = ValidationException.class)
    public void shouldBeWithinRange_larger_outputsErrorAndThrowsValidationException() throws Exception {
        // setup
        double fromInclusive = 5;
        double toInclusive = 10;
        try {
            // execute
            validator.shouldBeWithinRange("Internationalization", fromInclusive, toInclusive);
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_IS_OVER_Y), eq(field), eq(toInclusive));
            throw e;
        }
    }

    @Test
    public void shouldBeWithinRange_correct_doesNothing() throws Exception {
        // execute
        validator.shouldBeWithinRange("Oh", 0, 10);
    }

    @Test
    public void shouldBeValidEmail_correct_doesNothing() throws Exception {
        // execute
        validator.shouldBeValidEmail("Oh@oh.oh");
    }

    @Test(expected = ValidationException.class)
    public void shouldBeValidEmail_invalid_outputsErrorAndThrowsValidationException() throws Exception {
        try {
            // execute
            validator.shouldBeValidEmail("Internationalization");
        } catch (ValidationException e) {
            // verify
            verify(outputTool, times(1)).setErrorMsg(eq(ERR_X_INVALID), eq(field));
            throw e;
        }
    }
}