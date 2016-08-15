package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.i18n.Localize;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ApplicationService encapsulates all utility methods
 * that work with repository required by business logic.
 */
public class FacultyService {

    protected RepositoryTool repositoryTool;

    public FacultyService(RepositoryTool repositoryTool) {this.repositoryTool = repositoryTool;
    }

    /**
     * Returns an array of all faculties preserved in the repository.
     * @return all faculties in repository
     * @throws DaoException if repository layer fails.
     */
    public Faculty[] getAllFaculties() throws DaoException {
        return repositoryTool.getAutoCommittalFacultyRepository().getAll();
    }

    /**
     * Returns a number of students currently studying at the faculty.
     * @param faculty to search for
     * @return number of students
     * @throws DaoException  if repository layer fails.
     */
    public long countStudents(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.ACCEPTED);
    }

    /**
     * Returns a number of graduates currently studying at the faculty.
     * @param faculty to search for
     * @return number of graduates
     * @throws DaoException if repository layer fails.
     */
    public long countGraduates(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.GRADUATED);
    }

    /**
     * Returns a number of applicants for the faculty that
     * have status APPLIED or UNDER_CONSIDERATION
     * @param faculty to search for
     * @return number of candidates for the faculty.
     * @throws DaoException if repository layer fails.
     */
    public long countAllApplicants(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.UNDER_CONSIDERATION) + countUnconsideredApplicants(faculty);
    }

    /**
     * Returns a number of applicants for the faculty that
     * have status UNDER_CONSIDERATION
     * @param faculty to search for
     * @return number of candidates under consideration
     * @throws DaoException if repository layer fails.
     */
    public long countUnconsideredApplicants(Faculty faculty) throws DaoException {
        return repositoryTool.getAutoCommittalApplicationRepository()
                .countAllOf(faculty, Application.Status.APPLIED);
    }

    /**
     * Fills FacultyInfoItem with all the information about the faculty.
     * @param facultyInfoItemToFill item to fill
     * @param faculty to search for
     * @param lt for localizing the info
     * @throws DaoException if repository layer fails.
     */
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

    /**
     * Returns a set of info about all faculties including how the user is
     * related to each faculty.
     * @param applicationService to find how user is related to faculty
     * @param localization to localize info
     * @param currentUserId to search for
     * @return a set of info about all faculties and how the user is related to each of them
     * @throws DaoException if repository layer fails.
     */
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

    /**
     * Returns faculty instance corresponding to the id.
     * @param facultyId to search for
     * @return faculty
     * @throws DaoException if repository layer fails.
     */
    public Faculty getById(Long facultyId) throws DaoException {
        return repositoryTool.getAutoCommittalFacultyRepository().getById(facultyId);
    }
}
