package com.daniilyurov.training.project.web.model.business.impl.service;


import com.daniilyurov.training.project.web.model.business.api.Service;
import com.daniilyurov.training.project.web.model.business.impl.output.ApplicantInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.output.ApplicationInfoItemComparator;
import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.entity.*;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class ApplicationService implements Service {

    protected RepositoryTool repository;

    public ApplicationService(RepositoryTool repository) {
        this.repository = repository;
    }


    public void fillWithStatistics(FacultyInfoItem facultyInfoItem, Faculty faculty, Long currentUserId)
            throws DaoException {



        if (currentUserId == null) return;

        Application latestApplicationOfUser = repository.getAutoCommittalApplicationRepository()
                .getLastOf(faculty, currentUserId);

        if (latestApplicationOfUser == null) return;

        facultyInfoItem.setLatestStatusOfCurrentUser(latestApplicationOfUser.getStatus());
        if (isApplicationIdRequired(latestApplicationOfUser, faculty)) {
            facultyInfoItem.setApplicationIdForCurrentSelection(latestApplicationOfUser.getId());
        }

    }

    // Private helper methods are listed below

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

    public void persist(Application application) throws DaoException {
        repository.getAutoCommittalApplicationRepository().create(application);
    }


    public void update(Application application) throws DaoException {
        repository.getAutoCommittalApplicationRepository().update(application);
    }



    public int acceptBestAndRejectOthers(Faculty faculty, LocalizationTool localization) throws DaoException {
        // get all applicants sorted by score
        TreeSet<ApplicantInfoItem> appliedApplicants = getAppliedApplicants(faculty, localization);
        return selectBestAndRejectOthers(appliedApplicants, faculty);
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

        System.out.println("INPUT " + appliedApplicants.size()); // TODO sout
        System.out.println("OUT_AC " + applicationIdsForAccept.size()); // TODO sout
        System.out.println("OUT_REJ " + applicationIdsForReject.size()); // TODO sout

        return counter;
    }

    private void update(Set<Long> applicationIds, Application.Status status) throws DaoException {
        repository.getAutoCommittalApplicationRepository().updateAll(applicationIds, status);
    }

    private TreeSet<ApplicantInfoItem> getAppliedApplicants(Faculty faculty, LocalizationTool localization)
            throws DaoException {

        Applicants applicants = collectApplicants(faculty, localization);
        TreeSet<ApplicantInfoItem> allAppliedApplicants = new TreeSet<>(new ApplicationInfoItemComparator());

        allAppliedApplicants.addAll(applicants.getApplicantsUnderConsideration());
        allAppliedApplicants.addAll(applicants.getUnconsideredApplicants());

        return allAppliedApplicants;
    }

    public class Applicants {
        TreeSet<ApplicantInfoItem> unconsideredApplicants = new TreeSet<>(new ApplicationInfoItemComparator());
        TreeSet<ApplicantInfoItem> applicantsUnderConsideration = new TreeSet<>(new ApplicationInfoItemComparator());

        public TreeSet<ApplicantInfoItem> getUnconsideredApplicants() {
            return unconsideredApplicants;
        }

        public TreeSet<ApplicantInfoItem> getApplicantsUnderConsideration() {
            return applicantsUnderConsideration;
        }
    }

    public Applicants collectApplicants(Faculty faculty, LocalizationTool localization) throws DaoException {

        Applicants applicants = new Applicants();

        Application[] allApplications = repository.getAutoCommittalApplicationRepository()
                .getAllOf(faculty, faculty.getDateStudiesStart());

        int i = 1; //todo sout

        for (Application application : allApplications) {
            User user = application.getUser();

            ApplicantInfoItem applicant = new ApplicantInfoItem();
            applicant.setApplicationId(application.getId());
            applicant.setLocalFirstName(localization.getLocalFirstName(user));
            applicant.setLocalLastName(localization.getLocalLastName(user));

            double totalScore = user.getAverageSchoolResult();
            Set<Subject> subjectsRequired = faculty.getRequiredSubjects();
            Result[] studentResults = new Result[0];
            studentResults = repository.getAutoCommittalResultRepository().getAllOf(user);

            for (Result result : studentResults) {
                if (subjectsRequired.contains(result.getSubject())) {
                    totalScore += result.getResult();
                }
            }

            applicant.setTotalScore(totalScore);
            if (application.getStatus().equals(Application.Status.APPLIED)) {
                applicants.getUnconsideredApplicants().add(applicant);
            }
            if (application.getStatus().equals(Application.Status.UNDER_CONSIDERATION)) {
                applicants.getApplicantsUnderConsideration().add(applicant);
                System.out.println(i++);
            }
        }

        System.out.println("TOTAL" + applicants.getApplicantsUnderConsideration().size());

        return applicants;
    }
}
