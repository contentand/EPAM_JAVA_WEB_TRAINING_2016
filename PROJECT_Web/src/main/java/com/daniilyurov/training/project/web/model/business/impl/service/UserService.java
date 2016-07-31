package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Service;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.api.TransactionalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.utility.ContextAttributes;

import java.util.Locale;
import java.util.Set;

public class UserService implements Service {

    protected RepositoryTool repositoryTool;

    public UserService(Request request) {
        this.repositoryTool = new RepositoryTool((RepositoryManagerFactory) request.getContextAttribute(ContextAttributes.REPOSITORY_MANAGER_FACTORY));

    }

    public UserService(RepositoryTool repositoryTool) {
        this.repositoryTool = repositoryTool;
    }

    public void updateLocalePreferencesForUser(Long userId, Locale newLocale) {
        try {
            User currentUser = repositoryTool.getAutoCommittalUserRepository().getById(userId);
            currentUser.setLocale(newLocale);
            repositoryTool.getAutoCommittalUserRepository().update(currentUser);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public void persist(User newUser) throws DaoException {
        repositoryTool.getAutoCommittalUserRepository().create(newUser);
    }

    public void persist(User newUser, Set<Result> hisResults) throws DaoException {
        TransactionalRepositoryManager transaction = null;
        try {
            transaction = repositoryTool.startTransaction();
            transaction.getUserRepository().create(newUser);
            transaction.getResultRepository().createAll(hisResults);
            transaction.commit();
        } finally {
            if (transaction != null && !transaction.isCommitted()) {
                transaction.rollback();
            }
        }

    }

    public User getUser(Long id) throws DaoException {
        return repositoryTool.getAutoCommittalUserRepository().getById(id);
    }
}
