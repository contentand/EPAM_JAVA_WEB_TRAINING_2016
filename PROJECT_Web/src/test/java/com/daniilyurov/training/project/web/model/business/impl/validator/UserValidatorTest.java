package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.api.Role;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;
import java.util.Optional;

import static com.daniilyurov.training.project.web.utility.RequestParameters.*;
import static org.junit.Assert.*;

public class UserValidatorTest extends GenericValidator {

    UserValidator validator;

    @Before
    public void setup() {
        validator = new UserValidator(input, output, services);
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_loginInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("a"); // too short!
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_loginAlreadyTaken_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(true); // already taken

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_passwordInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("a"); // too short
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_emailInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("invalidEmail"); // invalid email
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_resultInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Vladimir"); // not cyrillic
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_cyrillicNameInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Smirnov"); // not cyrillic
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_cyrillicSurnameInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Смирнов"); // not Latin
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_latinNameInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Владимир"); // not Latin
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test(expected = ValidationException.class)
    public void parseValidUserInstance_latinSurnameInvalid_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("299"); // too large
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        validator.parseValidUserInstance();
    }

    @Test
    public void parseValidUserInstance_valid_returnsUser() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn("properLogin");
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn("properPassword");
        when(input.getParameter(PARAMETER_EMAIL)).thenReturn("proper@email.com");
        when(input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME)).thenReturn("Владимир");
        when(input.getParameter(PARAMETER_CYRILLIC_LAST_NAME)).thenReturn("Смирнов");
        when(input.getParameter(PARAMETER_LATIN_FIRST_NAME)).thenReturn("Vladimir");
        when(input.getParameter(PARAMETER_LATIN_LAST_NAME)).thenReturn("Smirnov");
        when(input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT)).thenReturn("199");
        when(userService.doesSuchLoginExist(eq("properLogin"))).thenReturn(false);

        // execute
        User result = validator.parseValidUserInstance();

        // verify
        assertEquals("properLogin", result.getLogin());
        assertEquals("properPassword", result.getPassword());
        assertEquals("proper@email.com", result.getEmail());
        assertEquals("Владимир", result.getCyrillicFirstName());
        assertEquals("Смирнов", result.getCyrillicLastName());
        assertEquals("Vladimir", result.getLatinFirstName());
        assertEquals("Smirnov", result.getLatinLastName());
        assertEquals(199D, result.getAverageSchoolResult(), 0.0001);

    }


    @Test
    public void parseAuthenticatedUser_userExists_returnsUser() throws Exception {
        // setup
        User user = mock(User.class);
        String login = "login";
        String password = "password";
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn(login);
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn(password);
        when(userService.getUserByLoginAndPassword(eq(login), eq(password)))
                .thenReturn(user);

        // execute
        User result = validator.parseAuthenticatedUser();

        // verify
        assertEquals(user, result);
    }

    @Test(expected = ValidationException.class)
    public void parseAuthenticatedUser_userAbsent_throwsValidationException() throws Exception {
        // setup
        String login = "login";
        String password = "password";
        when(input.getParameter(PARAMETER_LOGIN)).thenReturn(login);
        when(input.getParameter(PARAMETER_PASSWORD)).thenReturn(password);
        when(userService.getUserByLoginAndPassword(eq(login), eq(password)))
                .thenReturn(null);

        // execute
        validator.parseAuthenticatedUser();
    }

    @Test
    public void parseValidLocale_valid_returnsLocale() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LANGUAGE)).thenReturn("en");
        // execute
        Locale locale = validator.parseValidLocale();
        // verify
        assertEquals("en", locale.getLanguage());
    }

    @Test(expected = ValidationException.class)
    public void parseValidLocale_nullLanguageKey_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LANGUAGE)).thenReturn(null);
        // execute
        validator.parseValidLocale();
    }

    @Test(expected = ValidationException.class)
    public void parseValidLocale_emptyLanguageKey_throwsValidationException() throws Exception {
        // setup
        when(input.getParameter(PARAMETER_LANGUAGE)).thenReturn("");
        // execute
        validator.parseValidLocale();
    }


    @Test
    public void getCurrentUser_present_returnsOptionalWithUser() throws Exception {
        // setup
        Long id = 1L;
        User user = mock(User.class);
        when(input.getUserId()).thenReturn(id);
        when(userService.getUser(eq(id))).thenReturn(user);
        // execute
        Optional<User> result = validator.getCurrentUser();
        // verify
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void getCurrentUser_absent_returnsEmptyOptional() throws Exception {
        // setup
        when(input.getUserId()).thenReturn(null);
        // execute
        Optional<User> result = validator.getCurrentUser();
        // verify
        assertFalse(result.isPresent());
    }

    @Test
    public void getCurrentUserId_present_returnsId() throws Exception {
        // setup
        Long id = 1L;
        when(input.getUserId()).thenReturn(id);
        // execute
        Long result = validator.getCurrentUserId();
        // verify
        assertEquals(id, result);
    }

    @Test
    public void getCurrentUserId_absent_returnsNull() throws Exception {
        // setup
        when(input.getUserId()).thenReturn(null);
        // execute
        Long result = validator.getCurrentUserId();
        // verify
        assertNull(result);
    }

    @Test
    public void getCurrentUserRole_always_returnsRole() throws Exception {
        // setup
        Role role = Role.GUEST;
        when(input.getRole()).thenReturn(role);
        // execute
        Role result = validator.getCurrentUserRole();
        // verify
        assertEquals(role, result);
    }
}