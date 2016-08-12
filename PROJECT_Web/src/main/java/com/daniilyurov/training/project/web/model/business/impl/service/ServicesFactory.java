package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;

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

    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public FacultyService getFacultyService() {
        return facultyService;
    }

    public ResultsService getResultsService() {
        return resultsService;
    }

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public UserService getUserService() {
        return userService;
    }
}
