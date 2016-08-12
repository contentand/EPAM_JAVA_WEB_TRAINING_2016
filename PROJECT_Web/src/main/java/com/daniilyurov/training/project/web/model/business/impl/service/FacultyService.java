package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.tool.Localize;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.util.LinkedHashSet;
import java.util.Set;


public class FacultyService {

    protected RepositoryTool repositoryTool;

    public FacultyService(RepositoryTool repositoryTool) {this.repositoryTool = repositoryTool;
    }

    public Faculty[] getAllFaculties() throws DaoException {
        return repositoryTool.getAutoCommittalFacultyRepository().getAll();
    }

    public long countStudents(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.ACCEPTED);
    }

    public long countGraduates(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.GRADUATED);
    }

    public long countAllApplicants(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.UNDER_CONSIDERATION) + countUnconsideredApplicants(faculty);
    }

    public long countUnconsideredApplicants(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.APPLIED);
    }

    public void fillWithStatistics(FacultyInfoItem facultyInfoItemToFill, Faculty faculty, Localize lt)
            throws DaoException {

        // gathering statistics
        Long numberOfGraduates = countGraduates(faculty);
        Long numberOfStudents = countStudents(faculty);
        Long numberOfStudentsToConsider = countUnconsideredApplicants(faculty);
        Long numberOfAppliedStudents = countAllApplicants(faculty);
        FacultyInfoItem.RegistrationStatus status = FacultyInfoItem.getStatus(faculty, numberOfAppliedStudents);
        String[] requiredSubjects = faculty.getRequiredSubjects()
                .stream().map(lt::getLocalName).toArray(String[]::new);


        // filling up the details
        facultyInfoItemToFill.setId(faculty.getId());
        facultyInfoItemToFill.setLocalName(lt.getLocalName(faculty));
        facultyInfoItemToFill.setLocalDescription(lt.getLocalDescription(faculty));
        facultyInfoItemToFill.setRegistrationStart(faculty.getDateRegistrationStarts());
        facultyInfoItemToFill.setRegistrationEnd(faculty.getDateRegistrationEnds());
        facultyInfoItemToFill.setStudyStart(faculty.getDateStudiesStart());
        facultyInfoItemToFill.setDuration(faculty.getMonthsToStudy());
        facultyInfoItemToFill.setMaxStudents(faculty.getStudents());
        facultyInfoItemToFill.setNumberOfGraduates(numberOfGraduates);
        facultyInfoItemToFill.setNumberOfStudents(numberOfStudents);
        facultyInfoItemToFill.setNumberOfAppliedStudents(numberOfAppliedStudents);
        facultyInfoItemToFill.setNumberOfStudentsToAddUnderConsideration(numberOfStudentsToConsider);
        facultyInfoItemToFill.setRegistrationStatus(status);
        facultyInfoItemToFill.setRequiredSubjects(requiredSubjects);
    }


    public Set<FacultyInfoItem> getSetOfFacultyInfo(ApplicationService applicationService,
                                                    Localize localization,
                                                    Long currentUserId) throws DaoException {

        // create set of container fo storing output
        Set<FacultyInfoItem> extendedFaculties = new LinkedHashSet<>();

        // fill each container with data and add it to set
        Faculty[] faculties = getAllFaculties();
        for (Faculty faculty : faculties) {
            FacultyInfoItem facultyInfoItem = new FacultyInfoItem();
            fillWithStatistics(facultyInfoItem, faculty, localization);
            applicationService.fillWithStatistics(facultyInfoItem, faculty, currentUserId);
            extendedFaculties.add(facultyInfoItem);
        }

        return extendedFaculties;
    }

    public Faculty getById(Long facultyId) throws DaoException {
        return repositoryTool.getAutoCommittalFacultyRepository().getById(facultyId);
    }
}
