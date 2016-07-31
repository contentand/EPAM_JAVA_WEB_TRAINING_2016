package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.*;
import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.*;
import com.daniilyurov.training.project.web.model.business.impl.validator.*;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.function.Function;

import static com.daniilyurov.training.project.web.i18n.Value.ERR_SYSTEM_ERROR;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.REDIRECT_TO_WHERE_HE_CAME_FROM;
import static com.daniilyurov.training.project.web.utility.ContextAttributes.*;

public abstract class AbstractSafeCommand implements Command {

    static Logger logger = Logger.getLogger(AbstractSafeCommand.class);

    @Override
    public final String execute(Request request) {
        try {
            Provider provider = new ProviderImpl(request);
            return safeExecute(provider);
        } catch (Exception e) {
            logger.error("Error processing command. Business logic or Repository failed.", e);
            OutputTool output = new OutputTool(request);
            output.setErrorMsg(ERR_SYSTEM_ERROR);
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        }
    }

    public abstract String safeExecute(Provider provider) throws Exception;


    /**
     * The implementing class that provides dependencies for business logic.
     */
    private static class ProviderImpl implements Provider {

        private final Map<Provider.Services, Service> services;
        private Request request;
        RepositoryTool repository;

        ProviderImpl(Request request) {
            //noinspection unchecked : Context has configured checked map.
            this.services = (Map<Provider.Services, Service>) request.getContextAttribute(SERVICES);
            this.request = request;
            RepositoryManagerFactory rmf = (RepositoryManagerFactory) request
                    .getContextAttribute(REPOSITORY_MANAGER_FACTORY);
            repository = new RepositoryTool(rmf);
        }

        @Override
        public ApplicationService getApplicationService() {
            return (ApplicationService) getService(Services.APPLICATION, ApplicationService::new);
        }

        @Override
        public FacultyService getFacultyService() {
            return (FacultyService) getService(Services.FACULTY, FacultyService::new);
        }

        @Override
        public ResultsService getResultsService() {
            return (ResultsService) getService(Services.RESULTS, ResultsService::new);
        }

        @Override
        public SubjectService getSubjectService() {
            return (SubjectService) getService(Services.SUBJECTS, SubjectService::new);
        }

        @Override
        public UserService getUserService() {
            return (UserService) getService(Services.USER, UserService::new);
        }

        @Override
        public ApplicationValidator getApplicationValidator() {
            InputTool input = new InputTool(request);
            return new ApplicationValidator(input, this, repository);
        }

        @Override
        public FacultyValidator getFacultyValidator() {
            InputTool input = new InputTool(request);
            return new FacultyValidator(input, this, repository);
        }

        @Override
        public ResultValidator getResultValidator() {
            return new ResultValidator(request, repository);
        }

        @Override
        public UserValidator getUserValidator() {
            return new UserValidator(request, repository);
        }

        @Override
        public SessionManager getSessionManager() {
            return new SessionManager(request);
        }

        @Override
        public OutputTool getOutputTool() {
            return new OutputTool(request);
        }

        @Override
        public LocalizationTool getLocalizationTool() {
            return new LocalizationTool(request);
        }

        // Private helper methods are listed below

        private Service getService(Services serviceName,
                                   Function<RepositoryTool, Service> serviceGenerator) {

            // get from map
            Service service = services.get(serviceName);

            // not in map?
            if (service == null) {

                // preliminarily prepare a new instance
                Service newInstance = serviceGenerator.apply(repository);

                // lock map
                synchronized (services) {

                    // still not there?
                    service = services.get(serviceName);
                    if (service == null) {

                        // put new instance into map ----- and assign new value
                        services.put(serviceName, service = newInstance);
                    }
                }
            }
            return service;
        }

    }

}

