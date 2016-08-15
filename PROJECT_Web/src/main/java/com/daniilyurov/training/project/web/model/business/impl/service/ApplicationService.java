package com.daniilyurov.training.project.web.model.business.impl.service;


import com.daniilyurov.training.project.web.model.business.impl.output.ApplicantInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.i18n.Localize;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.entity.*;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * ApplicationService encapsulates all utility methods
 * that work with repository required by business logic.
 */
public class ApplicationService {

    protected RepositoryTool repository;

    public ApplicationService(RepositoryTool repository) {
        this.repository = repository;
    }


    /**
     * Takes FacultyInfoItem and fills it with the information about a particular
     * faculty and user.
     * @param facultyInfoItem to fill with information.
     * @param faculty to search for
     * @param currentUserId to search for
     * @throws DaoException if repository layer fails.
     */
    public void fillWithStatistics(FacultyInfoItem facultyInfoItem, Faculty faculty, Long currentUserId)
            throws DaoException {

        if (currentUserId == null) return; // if there is no user, there is no point in this method

        Application latestApplicationOfUser = repository.getAutoCommittalApplicationRepository()
                .getLastOf(faculty, currentUserId);

        if (latestApplicationOfUser == null) return;

        facultyInfoItem.setLatestStatusOfCurrentUser(latestApplicationOfUser.getStatus());
        if (isApplicationIdRequired(latestApplicationOfUser, faculty)) {
            facultyInfoItem.setApplicationIdForCurrentSelection(latestApplicationOfUser.getId());
        }
    }

    /**
     * Returns the number of applications of the user that have a particular status.
     * @param userId to search for
     * @param status to search for
     * @return number of applications matching the criteria
     * @throws DaoException if repository layer fails.
     */
    public long countAllOf(Long userId, Application.Status status) throws DaoException {
        return repository.getAutoCommittalApplicationRepository()
                .countAllOf(userId, status);
    }

    /**
     * Returns an application corresponding to the id.
     * @param id to search for
     * @return application matching the criteria
     * @throws DaoException if repository layer fails.
     */
    public Application getById(Long id) throws DaoException {
        return repository.getAutoCommittalApplicationRepository().getById(id);
    }

    /**
     * Returns all applications of a particular user.
     * @param user to search for
     * @return applications matching the criteria
     * @throws DaoException if repository layer fails.
     */
    public Application[] getAllOf(User user) throws DaoException {
        return repository.getAutoCommittalApplicationRepository().getAllOf(user);
    }

    /**
     * Saves the new application in the repository.
     * @param application to save
     * @throws DaoException if repository layer fails.
     */
    public void persist(Application application) throws DaoException {
        repository.getAutoCommittalApplicationRepository().create(application);
    }

    /**
     * Updates the application in the repository.
     * @param application to update
     * @throws DaoException if repository layer fails.
     */
    public void update(Application application) throws DaoException {
        repository.getAutoCommittalApplicationRepository().update(application);
    }

    /**
     * Sets status ACCEPTED for top best candidates for current selection
     * of the faculty, sets status REJECTED for the remainder of the list.
     * Returns the number of candidates accepted.
     *
     * @param faculty to search for
     * @param localization for information to be localized
     * @return the number of candidates accepted as students.
     * @throws DaoException if repository layer fails.
     */
    public int acceptBestAndRejectOthers(Faculty faculty, Localize localization) throws DaoException {
        // get all applicants sorted by score
        TreeSet<ApplicantInfoItem> appliedApplicants = getAppliedApplicants(faculty, localization);
        return selectBestAndRejectOthers(appliedApplicants, faculty);
    }

    /**
     * Collects the information about all students
     * of the latest selection of the faculty.
     *
     * @param faculty to search for
     * @param localize for information to be localized
     * @return info about all students for latest selection
     * @throws DaoException if repository layer fails.
     */
    public TreeSet<ApplicantInfoItem> collectStudentsOfLastSelection(Faculty faculty,
                                                                     Localize localize) throws DaoException {
        // container for result
        TreeSet<ApplicantInfoItem> results = new TreeSet<>();

        // Gathering all applications for latest selection
        Application[] allApplications = repository.getAutoCommittalApplicationRepository()
                .getAllOf(faculty, faculty.getDateStudiesStart());

        // For every application
        for (Application application : allApplications) {
            if (application.getStatus().equals(Application.Status.ACCEPTED)) {
                // Sub result container
                ApplicantInfoItem applicant = new ApplicantInfoItem();

                // Fill info about applicant
                User user = application.getUser();
                applicant.setApplicationId(application.getId());
                applicant.setLocalFirstName(localize.getLocalFirstName(user));
                applicant.setLocalLastName(localize.getLocalLastName(user));

                // count total score of the applicant
                double totalScore = user.getAverageSchoolResult();

                // add all relevant subject results to total score of the applicant
                Set<Subject> subjectsRequired = faculty.getRequiredSubjects();
                Result[] studentResults = repository.getAutoCommittalResultRepository().getAllOf(user);
                for (Result result : studentResults) {
                    if (subjectsRequired.contains(result.getSubject())) {
                        totalScore += result.getResult();
                    }
                }
                applicant.setTotalScore(totalScore);

                results.add(applicant);
            }
        }
        return results;
    }

    /**
     * Collects the information about all applicants with status APPLIED or UNDER_CONSIDERATION
     * for the latest selection of the faculty.
     *
     * @param faculty to search for
     * @param localization for information to be localized
     * @return info about all candidates for current selection
     * @throws DaoException if repository layer fails.
     */
    public Applicants collectApplicants(Faculty faculty, Localize localization) throws DaoException {

        // Gathering all applications for latest selection
        Application[] allApplications = repository.getAutoCommittalApplicationRepository()
                .getAllOf(faculty, faculty.getDateStudiesStart());

        // Result container
        Applicants applicants = new Applicants();

        // For every application
        for (Application application : allApplications) {

            // Sub result container
            ApplicantInfoItem applicant = new ApplicantInfoItem();

            // Fill info about applicant
            User user = application.getUser();
            applicant.setApplicationId(application.getId());
            applicant.setLocalFirstName(localization.getLocalFirstName(user));
            applicant.setLocalLastName(localization.getLocalLastName(user));

            // count total score of the applicant
            double totalScore = user.getAverageSchoolResult();

            // add all relevant subject results to total score of the applicant
            Set<Subject> subjectsRequired = faculty.getRequiredSubjects();
            Result[] studentResults = repository.getAutoCommittalResultRepository().getAllOf(user);
            for (Result result : studentResults) {
                if (subjectsRequired.contains(result.getSubject())) {
                    totalScore += result.getResult();
                }
            }
            applicant.setTotalScore(totalScore);

            // add the sub result in the appropriate bucket of the result
            if (application.getStatus().equals(Application.Status.APPLIED)) {
                applicants.getUnconsideredApplicants().add(applicant);
            }
            if (application.getStatus().equals(Application.Status.UNDER_CONSIDERATION)) {
                applicants.getApplicantsUnderConsideration().add(applicant);
            }
        }
        // finally
        return applicants;
    }

    /**
     * A wrapper for encapsulating information
     * about all unconsidered applicants and all applicants
     * under consideration (sorted by total score).
     * Motivation: pass such item to view for display.
     */
    public class Applicants {
        TreeSet<ApplicantInfoItem> unconsideredApplicants = new TreeSet<>();
        TreeSet<ApplicantInfoItem> applicantsUnderConsideration = new TreeSet<>();

        public TreeSet<ApplicantInfoItem> getUnconsideredApplicants() {
            return unconsideredApplicants;
        }

        public TreeSet<ApplicantInfoItem> getApplicantsUnderConsideration() {
            return applicantsUnderConsideration;
        }
    }

    // Private helper methods are listed below -------------------------------------------------------

    private boolean isApplicationIdRequired(Application application, Faculty faculty) {
        return isStudent(application) ||
                (isApplicationForCurrentSelection(application, faculty));
    }

    private boolean isApplicationForCurrentSelection(Application application, Faculty faculty) {
        Date applicationSelectionDate = application.getDateStudiesStart();
        Date currentSelectionDate = faculty.getDateStudiesStart();
        return applicationSelectionDate.equals(currentSelectionDate);
    }

    private boolean isStudent(Application application) {
        return application.getStatus().equals(Application.Status.ACCEPTED);
    }

    private int selectBestAndRejectOthers(TreeSet<ApplicantInfoItem> appliedApplicants,
                                          Faculty faculty) throws DaoException {

        int studentsToSelect = faculty.getStudents();
        Set<Long> applicationIdsForAccept = new HashSet<>();
        Set<Long> applicationIdsForReject = new HashSet<>();

        int counter = 0;
        for (ApplicantInfoItem applicant : appliedApplicants) {
            Long applicationId = applicant.getApplicationId();
            if (counter < studentsToSelect) {
                counter++;
                applicationIdsForAccept.add(applicationId);
            } else {
                applicationIdsForReject.add(applicationId);
            }
        }

        update(applicationIdsForAccept, Application.Status.ACCEPTED);
        update(applicationIdsForReject, Application.Status.REJECTED);

        return counter;
    }

    private void update(Set<Long> applicationIds, Application.Status status) throws DaoException {
        repository.getAutoCommittalApplicationRepository().updateAll(applicationIds, status);
    }

    // returns a single sorted set of info about all applicants that have status APPLIED/UNDER_CONSIDERATION
    private TreeSet<ApplicantInfoItem> getAppliedApplicants(Faculty faculty, Localize localization)
            throws DaoException {

        Applicants applicants = collectApplicants(faculty, localization);
        TreeSet<ApplicantInfoItem> allAppliedApplicants = new TreeSet<>();

        allAppliedApplicants.addAll(applicants.getApplicantsUnderConsideration());
        allAppliedApplicants.addAll(applicants.getUnconsideredApplicants());

        return allAppliedApplicants;
    }
}
