package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.tool.*;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import java.util.Date;

import static com.daniilyurov.training.project.web.utility.RequestParameters.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;

public class FacultyValidator extends AbstractValidator {

    protected InputTool input;
    protected SessionManager management;
    protected RepositoryTool repository;
    protected LocalizationTool localization;
    protected Provider provider;

    public FacultyValidator(InputTool input, Provider provider, RepositoryTool repository) {
        this.output = provider.getOutputTool();
        this.repository = repository;
        this.input = input;
        this.management = provider.getSessionManager();
        this.localization = provider.getLocalizationTool();
        this.provider = provider;
    }

    /**
     * Ensures the user has sent faculty id as a parameter.
     * Ensures that the id is parsable.
     * Ensures there is an instance of faculty corresponding to the id.
     * Ensures the faculty is open for registrations.
     *
     * Sends feedback to user via error_msg in case of failure.
     *
     * @return instance of Faculty eligible for registration
     * @throws ValidationException if the above mentioned conditions are violated.
     * @throws DaoException in case Repository fails.
     */
    public Faculty parseAndGetFacultyValidForApplication() throws ValidationException, DaoException {
        Long id = parseFacultyIdFromParameters();
        Faculty destinationFaculty = getExistingFaculty(id);
        ensureRegistrationIsOpen(destinationFaculty);
        return destinationFaculty;
    }

    private void ensureRegistrationIsOpen(Faculty faculty){
        Date currentDate = new Date(System.currentTimeMillis());
        Date registrationStart = faculty.getDateRegistrationStarts();
        Date registrationEnd = faculty.getDateRegistrationEnds();

        if (currentDate.before(registrationStart)) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, localization.getLocalName(faculty));
            output.setErrorMsg(ERR_REGISTRATION_STARTS_AFTER_X, registrationStart);
            throw new ValidationException();
        }

        if (currentDate.after(registrationEnd)) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, localization.getLocalName(faculty));
            output.setErrorMsg(ERR_REGISTRATION_OVER);
            throw new ValidationException();
        }
    }


    public Long parseFacultyIdFromParameters() throws ValidationException {
        currentField = FIELD_FACULTY;
        String idString = input.getParameter(PARAMETER_FACULTY_ID);
        return parseLong(idString);
    }

    public Long parseFacultyIdFromUrl() throws ValidationException {
        currentField = FIELD_FACULTY;
        String idString = input.getIdFromUri();
        return parseLong(idString);
    }

    public Faculty getExistingFaculty(Long facultyId) throws ValidationException, DaoException {
        Faculty faculty = repository.getAutoCommittalFacultyRepository().getById(facultyId);
        shouldNotBeNull(faculty);
        return faculty;
    }

    public Faculty parseExistingFacultyFromUrl() throws DaoException {
        // parse id, get faculty
        Long id = parseFacultyIdFromUrl();
        return getExistingFaculty(id);
    }

    public Faculty parseFacultyValidForSelection() throws ValidationException, DaoException {

        Faculty faculty = parseExistingFacultyFromUrl();
        FacultyService facultyService = provider.getFacultyService();

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
}
