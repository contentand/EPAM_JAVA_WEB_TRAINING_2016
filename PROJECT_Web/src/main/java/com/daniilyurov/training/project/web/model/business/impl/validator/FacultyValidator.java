package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.tool.*;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import java.util.Date;

import static com.daniilyurov.training.project.web.i18n.Value.*;
import static com.daniilyurov.training.project.web.utility.RequestParameters.*;

/**
 * FacultyValidator contains methods necessary for
 * taking and validating user input concerning faculties.
 *
 * @author Daniil Yurov
 */
public class FacultyValidator extends AbstractValidator {

    protected InputTool input;
    protected ServicesFactory servicesFactory;

    public FacultyValidator(InputTool input, OutputTool output, ServicesFactory servicesFactory) {
        if (input == null || output == null || servicesFactory == null) throw new NullPointerException();
        this.output = output;
        this.input = input;
        this.servicesFactory = servicesFactory;
    }

    /**
     * Ensures the user has sent faculty id as a parameter.
     * Ensures that the id can be parsed.
     * Ensures there is an instance of faculty corresponding to the id.
     * Ensures the faculty is open for registrations.
     * <p>
     * Sends feedback to user via error_msg in case of failure.
     *
     * @return instance of Faculty eligible for registration
     * @throws ValidationException if the above mentioned conditions are violated.
     * @throws DaoException        in case Repository fails.
     */
    public Faculty parseAndGetFacultyValidForApplication() throws ValidationException, DaoException {
        Long id = parseFacultyIdFromParameters();
        Faculty faculty = getExistingFaculty(id);
        ensureRegistrationIsOpen(faculty);
        return faculty;
    }

    /**
     * Parses faculty id from the URL and returns an instance of the faculty.
     * If parsing fails, throws ValidationException.
     * If faculty with the indicated id is not found, ValidationException is thrown.
     * @return faculty instance corresponding to the id indicated in the url.
     * @throws DaoException if repository layer fails
     */
    public Faculty parseExistingFacultyFromUrl() throws ValidationException, DaoException {
        Long id = parseFacultyIdFromUrl();
        return getExistingFaculty(id);
    }

    /**
     * Parses faculty id from the URL and returns an instance of the faculty.
     * If parsing fails, throws ValidationException.
     * If faculty with the indicated id is not found, ValidationException is thrown.
     * If faculty if not valid for selection, ValidationException is thrown.
     * @return faculty instance valid for student selection
     * @throws ValidationException if you cannot select best candidates from the faculty
     * @throws DaoException if repository layer fails
     */
    public Faculty parseFacultyValidForSelection() throws ValidationException, DaoException {

        Faculty faculty = parseExistingFacultyFromUrl();
        FacultyService facultyService = servicesFactory.getFacultyService();

        // ensure numberOfStudentsToConsider for faculty is 0
        long numberOfUnconsideredApplicants = facultyService.countUnconsideredApplicants(faculty);
        if (numberOfUnconsideredApplicants != 0) {
            output.setErrorMsg(ERR_SOME_UNCONSIDERED_APPLICANTS_LEFT);
            throw new ValidationException();
        }

        // ensure numberOfAppliedStudents for faculty is not 0
        long numberOfApplicants = facultyService.countAllApplicants(faculty);
        if (numberOfApplicants == 0) {
            output.setErrorMsg(ERR_NOTHING_TO_SELECT_FROM);
            throw new ValidationException();
        }

        return faculty;
    }

    // Private helper methods are listed below ------------------------------------------------

    private Long parseFacultyIdFromUrl() throws ValidationException {
        currentField = FIELD_FACULTY;
        String idString = input.getIdFromUri();
        return parseLong(idString);
    }

    private Faculty getExistingFaculty(Long facultyId) throws ValidationException, DaoException {
        Faculty faculty = servicesFactory.getFacultyService().getById(facultyId);
        shouldNotBeNull(faculty);
        return faculty;
    }

    private void ensureRegistrationIsOpen(Faculty faculty){
        Date currentDate = new Date(System.currentTimeMillis());
        Date registrationStart = faculty.getDateRegistrationStarts();
        Date registrationEnd = faculty.getDateRegistrationEnds();

        if (currentDate.before(registrationStart)) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, output.getLocalName(faculty));
            output.setErrorMsg(ERR_REGISTRATION_STARTS_AFTER_X, registrationStart);
            throw new ValidationException();
        }

        if (currentDate.after(registrationEnd)) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, output.getLocalName(faculty));
            output.setErrorMsg(ERR_REGISTRATION_OVER);
            throw new ValidationException();
        }
    }

    private Long parseFacultyIdFromParameters() throws ValidationException {
        currentField = FIELD_FACULTY;
        String idString = input.getParameter(PARAMETER_FACULTY_ID);
        return parseLong(idString);
    }
}
