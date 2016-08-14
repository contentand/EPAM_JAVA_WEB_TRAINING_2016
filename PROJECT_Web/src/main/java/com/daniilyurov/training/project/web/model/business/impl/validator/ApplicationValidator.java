package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.impl.tool.*;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.i18n.Value.*;

import java.util.Objects;
import java.util.Set;

/**
 * This class validates if the user input parameters are valid.
 * It can also be used to validate if data is consistent.
 *
 * Application Rules.
 *
 * 1. You CANNOT apply if you are unauthorized.
 * 2. You CANNOT apply if you are an administrator.
 * 3. You CANNOT apply to the same faculty:
 *      (3.1)   if you have status ACCEPTED in any selection,
 *      (3.2)   if you have status GRADUATED in any selection,
 *      (3.3)   if you have status APPLIED or UNDER_CONSIDERATION in current selection.
 * 4. You CANNOT apply if you are already a student at two other faculties.
 * 5. You CANNOT apply if the registration period is over or has not started yet.
 * 6. You CANNOT apply if you do not have specific subjects required by destination faculty.
 *
 * @author Daniil Yurov
 */

public class ApplicationValidator extends AbstractValidator {

    protected final long PARALLEL_STUDIES_LIMIT = 2;

    protected InputTool input;
    protected ServicesFactory servicesFactory;


    public ApplicationValidator(InputTool input, OutputTool output, ServicesFactory servicesFactory) {
        if (input == null || output == null || servicesFactory == null) throw new NullPointerException();
        this.output = output;
        this.input = input;
        this.servicesFactory = servicesFactory;
    }

    /**
     * Returns the number of faculties the indicated user is a student in.
     */
    public long countNumberOfParallelStudies(Long userId) throws DaoException {
        return servicesFactory.getApplicationService().countAllOf(userId, Application.Status.ACCEPTED);
    }

    /**
     * Returns true if the user has exceeded the maximum number of parallel studies allowed.
     */
    public boolean hasExceededParallelStudyLimit(Long userId) throws DaoException {
        long parallelStudies = countNumberOfParallelStudies(userId);
        return parallelStudies >= PARALLEL_STUDIES_LIMIT;
    }

    /**
     * Ensures that the faculty is open for enrolment.
     * And the user can apply for this particular faculty.
     * Returns an application instance with all necessary fields
     * filled in.
     * @return filled application instance
     * @throws ValidationException if current user cannot apply
     */
    public Application getValidFilledNewApplication(User user, Faculty faculty) throws DaoException {
        ensureCanApply(user, faculty);
        Application application = new Application();
        application.setUser(user);
        application.setFaculty(faculty);
        application.setStatus(Application.Status.APPLIED);
        application.setDateStudiesStart(faculty.getDateStudiesStart());
        application.setMonthsToStudy(faculty.getMonthsToStudy());
        return application;
    }

    /**
     * Ensures that the application that was sent via request
     * belongs to the user.
     * Returns the instance if it does.
     */
    public Application parseValidApplication(User user) throws DaoException {
        Application application = parseExistingApplication();
        if (!application.getUser().equals(user)) {
            output.setErrorMsg(ERR_WRONG_INPUT);
            throw new ValidationException();
        }
        return application;
    }

    /**
     * Ensures that the id sent via request parameter corresponds to the real
     * application in database.
     */
    public Application parseExistingApplication() throws DaoException {
        Long id = parseApplicationId();
        Application application = servicesFactory.getApplicationService().getById(id);
        if (application == null) {
            output.setErrorMsg(ERR_NON_EXISTING_APPLICATION);
            throw new ValidationException();
        }
        return application;
    }

    /**
     * If the application is such that its state is not allowed to be
     * modified to APPLIED state, a ValidationException is thrown.
     * @param application to validate
     * @throws DaoException if Repository layer fails
     * @throws ValidationException if validation fails
     */
    public void ensureCanReapply(Application application) throws DaoException {
        ensureIsForCurrentSelection(application);
        ensureCanApply(application.getUser(), application.getFaculty());
    }

    /**
     * If the application is such that its state is not allowed to be
     * modified to CANCELLED state, a ValidationException is thrown.
     * @param application to validate
     * @throws DaoException if Repository layer fails
     * @throws ValidationException if validation fails
     */
    public void ensureCanCancel(Application application) throws DaoException {
        ensureIsForCurrentSelection(application);
        ensureApplicationStatusAllowsCancel(application);
    }

    /**
     * If the application is such that its state is not allowed to be
     * modified to UNDER_CONSIDERATION state, a ValidationException is thrown.
     * @param application to validate
     * @throws ValidationException if validation fails
     */
    public void ensureCanConsider(Application application) {
        if (!isApplied(application)) {
            output.setErrorMsg(ERR_EDITING_INVALID_APPLICATION);
            throw new ValidationException();
        }
    }

    /**
     * If the application is such that its state is not allowed to be
     * modified to EXPELLED state, a ValidationException is thrown.
     * @param application to validate
     * @throws ValidationException if validation fails
     */
    public void ensureCanBeExpelled(Application application) {
        if (!isStudying(application)) {
            output.setErrorMsg(ERR_EDITING_INVALID_APPLICATION);
            throw new ValidationException();
        }
    }

    /**
     * If the application is such that its state is not allowed to be
     * modified to QUIT state, a ValidationException is thrown.
     * @param application to validate
     * @throws ValidationException if validation fails
     */
    public void ensureCanQuit(Application application) {
        if (!isStudying(application)) {
            output.setErrorMsg(ERR_EDITING_INVALID_APPLICATION);
            throw new ValidationException();
        }
    }

    // Private helper members are listed below -------------------------------------------------------

    /**
     * Private helper class to gather statistics.
     * Note! It should not be given away as it has no
     * getter/setter methods. FOR INTERNAL USE ONLY!
     *
     * Motivation:
     * 1. Avoid redundant fields in the outer class
     * 2. Avoid a long list of parameters in some private helper methods
     *    within the outer class.
     */
    private final static class Statistics {
        private boolean isAppliedForCurrentSelectionToDestinationFaculty;
        private boolean isStudyingAtDestinationFaculty;
        private boolean isGraduateOfDestinationFaculty;
        private int numberOfOtherFacultiesCurrentUserIsStudentOf;
        private User user; // user under test
        private Faculty faculty; // faculty under test
    }

    private void ensureNotAlreadyAppliedForCurrentSelectionToDestinationFaculty(Statistics statistics) {
        if (statistics.isAppliedForCurrentSelectionToDestinationFaculty) {
            output.setErrorMsg(ERR_ALREADY_BEING_APPLIED);
            throw new ValidationException();
        }
    }

    // ensure not already a student at two other faculties (rule #4)
    private void ensureNotExceedingMaximumParallelStudiesLimitation(Statistics statistics) throws DaoException {

        if (statistics.numberOfOtherFacultiesCurrentUserIsStudentOf >= PARALLEL_STUDIES_LIMIT) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, output.getLocalName(statistics.faculty));
            output.setErrorMsg(INFO_MAXIMUM_X_PARALLEL_STUDIES, PARALLEL_STUDIES_LIMIT);
            output.setErrorMsg(ERR_ALREADY_A_STUDENT_AT_X_FACULTIES,
                    statistics.numberOfOtherFacultiesCurrentUserIsStudentOf);
            throw new ValidationException();
        }
    }

    // ensure not studying at the destination faculty (rule #3.1)
    private void ensureNotAlreadyStudentAtDestinationFaculty(Statistics statistics) {

        if (statistics.isStudyingAtDestinationFaculty) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, output.getLocalName(statistics.faculty));
            output.setErrorMsg(ERR_ALREADY_A_STUDENT_AT_THIS_FACULTY);
            throw new ValidationException();
        }
    }

    // ensure not graduate of the destination faculty (rule #3.2)
    private void ensureNotAlreadyGraduateOfDestinationFaculty(Statistics statistics) {
        if (statistics.isGraduateOfDestinationFaculty) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, output.getLocalName(statistics.faculty));
            output.setErrorMsg(ERR_ALREADY_A_GRADUATE_OF_THIS_FACULTY);
            throw new ValidationException();
        }
    }

    // ensure has required subjects (rule #6)
    private void ensureHasRequiredSubjects(Statistics statistics) throws DaoException {
        Faculty destinationFaculty = statistics.faculty;
        Set<Subject> requiredSubjects = destinationFaculty.getRequiredSubjects();
        Set<Subject> studentSubjects = servicesFactory.getResultsService().getSubjectsOf(statistics.user);

        if (!studentSubjects.containsAll(requiredSubjects)) {
            output.setErrorMsg(ERR_YOU_CANNOT_APPLY_FOR_X, output.getLocalName(destinationFaculty));
            requiredSubjects.stream()
                    .filter(requiredSubject -> !studentSubjects.contains(requiredSubject))
                    .forEach(missingSubject -> output.setErrorMsg(ERR_X_SUBJECT_MISSING_IN_PROFILE,
                            output.getLocalName(missingSubject)));
            throw new ValidationException();
        }
    }

    // is application for the destination faculty?
    private boolean isSameFaculty(Application application, Faculty destinationFaculty) {
        return Objects.equals(application.getFaculty().getId(), destinationFaculty.getId());
    }

    private boolean isStudying(Application application) {
        return application.getStatus().equals(Application.Status.ACCEPTED);
    }

    private boolean isGraduate(Application application) {
        return application.getStatus().equals(Application.Status.GRADUATED);
    }

    // is application for the current selection period of the faculty
    private boolean isCurrentSelection(Application application, Faculty faculty) {
        return application.getDateStudiesStart().equals(faculty.getDateStudiesStart());
    }

    private boolean isWillingToStudy(Application application) {
        return isApplied(application) || isUnderConsideration(application);
    }

    private boolean isUnderConsideration(Application application) {
        return application.getStatus().equals(Application.Status.UNDER_CONSIDERATION);
    }

    private boolean isApplied(Application application) {
        return application.getStatus().equals(Application.Status.APPLIED);
    }


    // gather statistics of current user applications for further validation against rules
    private Statistics gatherStatisticsAboutAllCurrentUserApplications(User user, Faculty destinationFaculty)
            throws DaoException {

        Application[] allUserApplications = servicesFactory.getApplicationService().getAllOf(user);
        Statistics statistics = new Statistics();
        statistics.user = user;
        statistics.faculty = destinationFaculty;

        for (Application application : allUserApplications) {
            if (isSameFaculty(application, destinationFaculty)) {
                if (isCurrentSelection(application, destinationFaculty) && isWillingToStudy(application)) {
                    statistics.isAppliedForCurrentSelectionToDestinationFaculty = true;
                }
                if (isStudying(application)) {
                    statistics.isStudyingAtDestinationFaculty = true;
                }
                if (isGraduate(application)) {
                    statistics.isGraduateOfDestinationFaculty = true;
                }
            }
            else {
                if (isStudying(application)) {
                    statistics.numberOfOtherFacultiesCurrentUserIsStudentOf++;
                }
            }
        }

        return statistics;
    }


    private void ensureCanApply(User user, Faculty faculty) throws DaoException {
        if (user == null || faculty == null) throw new NullPointerException();
        Statistics statistics = gatherStatisticsAboutAllCurrentUserApplications(user, faculty);
        ensureNotAlreadyAppliedForCurrentSelectionToDestinationFaculty(statistics);   // (rule #3.3)
        ensureNotExceedingMaximumParallelStudiesLimitation(statistics);               // (rule #4)
        ensureNotAlreadyStudentAtDestinationFaculty(statistics);                      // (rule #3.1)
        ensureNotAlreadyGraduateOfDestinationFaculty(statistics);                     // (rule #3.2)
        ensureHasRequiredSubjects(statistics);                                        // (rule #6)
    }


    // if not, throws ValidationException
    private void ensureApplicationStatusAllowsCancel(Application application) {
        if (!isWillingToStudy(application)) {
            output.setErrorMsg(ERR_NON_EXISTING_APPLICATION);
            throw new ValidationException();
        }
    }

    // ensure application is for current selection
    private void ensureIsForCurrentSelection(Application application) {
        Faculty faculty = application.getFaculty();
        if (!faculty.getDateStudiesStart().equals(application.getDateStudiesStart())) {
            output.setErrorMsg(ERR_EDITING_INVALID_APPLICATION);
            throw new ValidationException();
        }
    }

    // parses application id from the url or throws ValidationException
    private Long parseApplicationId() {
        String idString = input.getIdFromUri();
        currentField = FIELD_APPLICATION_ID;
        return parseLong(idString);
    }
}
