package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.TransactionalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.util.Locale;
import java.util.Set;

public class UserService {

    protected RepositoryTool repositoryTool;

    public UserService(RepositoryTool repositoryTool) {
        this.repositoryTool = repositoryTool;
    }

    public void updateLocalePreferencesForUser(User user, Locale newLocale) {
        try {
            user.setLocale(newLocale);
            repositoryTool.getAutoCommittalUserRepository().update(user);
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

    public User getUserByLoginAndPassword(String login, String password) throws DaoException {
        return repositoryTool.getAutoCommittalUserRepository().getUserByLoginAndPassword(login, password);
    }

    public boolean doesSuchLoginExist(String login) throws DaoException {
        return repositoryTool.getAutoCommittalUserRepository().doesSuchLoginExist(login);
    }
}
