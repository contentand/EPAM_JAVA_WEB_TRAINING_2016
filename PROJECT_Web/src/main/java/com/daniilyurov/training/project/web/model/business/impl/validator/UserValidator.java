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

    public UserValidator(InputTool input, OutputTool output, ServicesFactory servicesFactory) {
        if (input == null || output == null || servicesFactory == null) throw new NullPointerException();
        this.input = input;
        this.output = output;
        this.servicesFactory = servicesFactory;
    }

    /**
     * Reads input parameters containing the information
     * about the new user, validates them and creates a
     * new user instance.
     * @return user instance containing state taken from parameters
     * @throws ValidationException if any parameter value is not valid
     * @throws DaoException if repository layer fails
     */
    public User parseValidUserInstance() throws ValidationException, DaoException {
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

    /**
     * Gets language code from parameter, ensures it is valid
     * and returns a new Locale instance.
     * @return a locale instance created from input parameter.
     */
    public Locale parseValidLocale() {
        String language = input.getParameter(PARAMETER_LANGUAGE);
        currentField = FIELD_LOCALIZATION;
        shouldBeNotNullNotEmpty(language);
        return new Locale(language);
    }

    /**
     * Gets an instance of the user that is currently authenticated.
     * If the user is guest, an empty Optional is returned.
     * @return an optional containing the logged in user instance or null
     * @throws DaoException if repository layer fails
     */
    public Optional<User> getCurrentUser() throws DaoException {
        Long id = input.getUserId();
        if (id == null) return Optional.empty();
        else return Optional.ofNullable(servicesFactory.getUserService().getUser(id));
    }

    /**
     * Returns an id of the user currently logged in
     * or null if it is a guest.
     * @return id of the logged in user or null if such is absent
     */
    public Long getCurrentUserId() {
        return input.getUserId();
    }

    /**
     * Returns the role of the session holder.
     * @return user role
     */
    public Role getCurrentUserRole() {
        return input.getRole();
    }

    // Private helper methods are listed below------------------------------------------------------

    private void ensureIsUniqueLogin(String login) throws DaoException {
        if (servicesFactory.getUserService().doesSuchLoginExist(login)) {
            output.setErrorMsg(ERR_LOGIN_IS_TAKEN);
            throw new ValidationException();
        }
    }

    private String parseValidLogin() throws ValidationException, DaoException {
        currentField = FIELD_LOGIN;
        String login = input.getParameter(PARAMETER_LOGIN);
        shouldNotBeNull(login);
        shouldNotBeEmpty(login);
        shouldBeWithinRange(login, 4, Double.POSITIVE_INFINITY);
        ensureIsUniqueLogin(login);
        return login;
    }

    private String parseValidPassword() throws ValidationException {
        currentField = FIELD_PASSWORD;
        String password = input.getParameter(PARAMETER_PASSWORD);
        shouldNotBeNull(password);
        shouldNotBeEmpty(password);
        shouldBeWithinRange(password, 6, Double.POSITIVE_INFINITY);
        return password;
    }

    private String parseValidEmail() throws ValidationException {
        currentField = FIELD_EMAIL;
        String email = input.getParameter(PARAMETER_EMAIL);
        shouldNotBeNull(email);
        shouldNotBeEmpty(email);
        shouldBeValidEmail(email);
        return email;
    }

    private String parseValidCyrillicFirstName() throws ValidationException {
        currentField = FIELD_FIRST_NAME;
        String firstName = input.getParameter(PARAMETER_CYRILLIC_FIRST_NAME);
        shouldBeNotNullNotEmpty(firstName);
        shouldContainOnlyCyrillicCharsAndApostropheAndDash(firstName);
        return firstName;
    }

    private String parseValidCyrillicLastName() throws ValidationException {
        currentField = FIELD_LAST_NAME;
        String lastName = input.getParameter(PARAMETER_CYRILLIC_LAST_NAME);
        shouldBeNotNullNotEmpty(lastName);
        shouldContainOnlyCyrillicCharsAndApostropheAndDash(lastName);
        return lastName;
    }

    private String parseValidLatinFirstName() throws ValidationException {
        currentField = FIELD_FIRST_NAME;
        String firstName = input.getParameter(PARAMETER_LATIN_FIRST_NAME);
        shouldBeNotNullNotEmpty(firstName);
        shouldContainOnlyLatinCharsAndApostropheAndDash(firstName);
        return firstName;
    }

    private String parseValidLatinLastName() throws ValidationException {
        currentField = FIELD_LAST_NAME;
        String lastName = input.getParameter(PARAMETER_LATIN_LAST_NAME);
        shouldBeNotNullNotEmpty(lastName);
        shouldContainOnlyLatinCharsAndApostropheAndDash(lastName);
        return lastName;
    }

    private double parseValidAverageSchoolResult() throws ValidationException {
        currentField = FIELD_AVERAGE_SCHOOL_RESULT;
        String resultString = input.getParameter(PARAMETER_AVERAGE_SCHOOL_RESULT);
        shouldNotBeNull(resultString);
        shouldNotBeEmpty(resultString);
        double result = parseDouble(resultString);
        shouldBeWithinRange(result, 100.0, 200.0);
        return result;
    }

}
