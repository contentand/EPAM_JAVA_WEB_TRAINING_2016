package com.daniilyurov.training.project.web.model.business.impl.tool;

import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.api.TransactionalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.*;

/**
 * RepositoryTool provides methods for getting instances of
 * different repositories of different types.
 */
public class RepositoryTool {

    RepositoryManagerFactory repositoryManagerFactory;

    public RepositoryTool(RepositoryManagerFactory repositoryManagerFactory) {
        this.repositoryManagerFactory = repositoryManagerFactory;
    }

    public UserRepository getAutoCommittalUserRepository() throws DaoException {
        return repositoryManagerFactory.getAutoCommittalRepositoryManager().getUserRepository();
    }

    public ResultRepository getAutoCommittalResultRepository() throws DaoException {
        return repositoryManagerFactory.getAutoCommittalRepositoryManager().getResultRepository();
    }

    public FacultyRepository getAutoCommittalFacultyRepository() throws DaoException {
        return repositoryManagerFactory.getAutoCommittalRepositoryManager().getFacultyRepository();
    }

    public ApplicationRepository getAutoCommittalApplicationRepository() throws DaoException {
        return repositoryManagerFactory.getAutoCommittalRepositoryManager().getApplicationRepository();
    }

    public SubjectRepository getAutoCommittalSubjectRepository() throws DaoException {
        return repositoryManagerFactory.getAutoCommittalRepositoryManager().getSubjectRepository();
    }

    public TransactionalRepositoryManager startTransaction() throws DaoException {
        return repositoryManagerFactory.getTransactionalRepositoryManager();
    }

}
