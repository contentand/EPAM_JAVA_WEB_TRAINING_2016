package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.api.Role;
import com.daniilyurov.training.project.web.model.business.impl.tool.InputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import java.util.Locale;
import java.util.Optional;

import static com.daniilyurov.training.project.web.utility.RequestParameters.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

public class UserValidator extends AbstractValidator {

    protected InputTool input;
    ServicesFactory servicesFactory;

    public UserValidator(InputTool inputTool, OutputTool outputTool, ServicesFactory servicesFactory) {
        this.input = inputTool;
        this.output = outputTool;
        this.servicesFactory = servicesFactory;
    }

    public User parseValidUserInstance() throws ValidationException {
        User user = new User();
        user.setLogin(parseValidLogin());
        user.setPassword(parseValidPassword());
        user.setEmail(parseValidEmail());
        user.setAverageSchoolResult(parseValidAverageSchoolResult());
        user.setRole(Role.APPLICANT.name());
        user.setLocale(output.getLocale());
        user.setCyrillicFirstName(parseValidCyrillicFirstName());
        user.setCyrillicLastName(parseValidCyrillicLastName());
        user.setLatinFirstName(parseValidLatinFirstName());
        user.setLatinLastName(parseValidLatinLastName());
        return user;
    }

    public String parseValidLogin() throws ValidationException {
        currentField = FIELD_LOGIN;
        String login = input.getParameter(PARAMETER_LOGIN);
        shouldNotBeNull(login);
        shouldNotBeEmpty(login);
        shouldBeWithinRange(login, 4, Double.POSITIVE_INFINITY);
        ensureIsUniqueLogin(login);
        return login;
    }

    public String parseValidPassword() throws ValidationException {
        currentField = FIELD_PASSWORD;
        String password = input.getParameter(PARAMETER_PASSWORD);
        shouldNotBeNull(password);
        shouldNotBeEmpty(password);
        shouldBeWithinRange(password, 6, Double.POSITIVE_INFINITY);
        return password;
    }

    public String parseValidEmail() throws ValidationException {
        currentField = FIELD_EMAIL;
        String email = input.getParameter(PARAMETER_EMAIL);
        shouldNotBeNull(email);
        shouldNotBeEmpty(email);
        shouldBeValidEmail(email);
        return email;
    }

    public String parseValidCyrillicFirstName() throws ValidationException {
        currentField = FIELD_FIRST_NAME;
        String firstName = input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME);
        shouldBeNotNullNotEmpty(firstName);
        shouldContainOnlyCyrillicCharsAndApostropheAndDash(firstName);
        return firstName;
    }

    public String parseValidCyrillicLastName() throws ValidationException {
        currentField = FIELD_LAST_NAME;
        String lastName = input.getParameter(PARAMETER_CYRILLIC_LAST_NAME);
        shouldBeNotNullNotEmpty(lastName);
        shouldContainOnlyCyrillicCharsAndApostropheAndDash(lastName);
        return lastName;
    }

    public String parseValidLatinFirstName() throws ValidationException {
        currentField = FIELD_FIRST_NAME;
        String firstName = input.getParameter(PARAMETER_LATIN_FIRST_NAME);
        shouldBeNotNullNotEmpty(firstName);
        shouldContainOnlyLatinCharsAndApostropheAndDash(firstName);
        return firstName;
    }

    public String parseValidLatinLastName() throws ValidationException {
        currentField = FIELD_LAST_NAME;
        String lastName = input.getParameter(PARAMETER_LATIN_LAST_NAME);
        shouldBeNotNullNotEmpty(lastName);
        shouldContainOnlyLatinCharsAndApostropheAndDash(lastName);
        return lastName;
    }

    public double parseValidAverageSchoolResult() throws ValidationException {
        currentField = FIELD_AVERAGE_SCHOOL_RESULT;
        String resultString = input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT);
        shouldNotBeNull(resultString);
        shouldNotBeEmpty(resultString);
        double result = parseDouble(resultString);
        shouldBeWithinRange(result, 100.0, 200.0);
        return result;
    }

    /**
     * Parses login and password parameters sent by user and
     * returns a corresponding User instance.
     *
     * If input is invalid or no user found, it sends corresponding
     * feedback message to the user and throws ValidationException.
     *
     * @return User instance that has indicated login and password.
     * @throws ValidationException in case user is not found
     * @throws DaoException in case of problems with Repository.
     */
    public User parseAuthenticatedUser() throws ValidationException, DaoException {

        // get login and password from request parameters
        String login = input.getParameter(PARAMETER_LOGIN);
        String password = input.getParameter(PARAMETER_PASSWORD);

        // did user send any login/password?
        currentField = FIELD_LOGIN;
        shouldBeNotNullNotEmpty(login);
        currentField = FIELD_PASSWORD;
        shouldBeNotNullNotEmpty(password);

        // find user with such login and password
        User user = servicesFactory.getUserService().getUserByLoginAndPassword(login, password);

        // no user?
        if (user == null) {

            // notify and break process
            output.setErrorMsg(WRONG_CREDENTIALS);
            throw new ValidationException();
        }

        // otherwise
        return user;
    }

    public Locale parseValidLocale() {
        String language = input.getParameter(PARAMETER_LANGUAGE);
        currentField = FIELD_LOCALIZATION;
        shouldBeNotNullNotEmpty(language);
        return new Locale(language);
    }


    public Optional<User> getCurrentUser() throws DaoException {
        Long id = input.getUserId();
        if (id == null) return Optional.empty();
        else return Optional.ofNullable(servicesFactory.getUserService().getUser(id));
    }

    public Long getCurrentUserId() {
        return input.getUserId();
    }


    public Role getCurrentUserRole() {
        return input.getRole();
    }

    // Private helper methods are listed below

    private void ensureIsUniqueLogin(String login) {
        try {
            if (servicesFactory.getUserService().doesSuchLoginExist(login)) {
                output.setErrorMsg(ERR_LOGIN_IS_TAKEN);
                throw new ValidationException();
            }
        } catch (DaoException e) {
            e.printStackTrace();
            throw new IllegalStateException();
        }
    }

}
