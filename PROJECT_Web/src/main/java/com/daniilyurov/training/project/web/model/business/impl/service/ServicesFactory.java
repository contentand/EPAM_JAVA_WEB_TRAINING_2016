package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;

/**
 * ServicesFactory holds instances of
 * all services of the application and provides them
 * upon request.
 */
public class ServicesFactory {

    private ApplicationService applicationService;
    private FacultyService facultyService;
    private ResultsService resultsService;
    private SubjectService subjectService;
    private UserService userService;

    public ServicesFactory(RepositoryTool repositoryTool) {
        this.applicationService = new ApplicationService(repositoryTool);
        this.facultyService = new FacultyService(repositoryTool);
        this.resultsService = new ResultsService(repositoryTool);
        this.subjectService = new SubjectService(repositoryTool);
        this.userService = new UserService(repositoryTool);
    }

    /**
     * @return application service
     */
    public ApplicationService getApplicationService() {
        return applicationService;
    }

    /**
     * @return faculty service
     */
    public FacultyService getFacultyService() {
        return facultyService;
    }

    /**
     * @return results service
     */
    public ResultsService getResultsService() {
        return resultsService;
    }

    /**
     * @return subject service
     */
    public SubjectService getSubjectService() {
        return subjectService;
    }

    /**
     * @return user service
     */
    public UserService getUserService() {
        return userService;
    }
}
