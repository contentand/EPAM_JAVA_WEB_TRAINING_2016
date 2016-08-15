package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.TransactionalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.util.Locale;
import java.util.Set;

/**
 * UserService encapsulates all utility methods
 * that work with repository required by business logic.
 */
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

    /**
     * Saves the new user in repository.
     * @param newUser to save
     * @throws DaoException if repository layer fails.
     */
    public void persist(User newUser) throws DaoException {
        repositoryTool.getAutoCommittalUserRepository().create(newUser);
    }

    /**
     * Saves the new user along with his/her subject results.
     * @param newUser to save
     * @param hisResults set of results of the new user
     * @throws DaoException if repository layer fails.
     */
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

    /**
     * Returns user with id
     * @param id to search for
     * @return a user instance
     * @throws DaoException if repository layer fails.
     */
    public User getUser(Long id) throws DaoException {
        return repositoryTool.getAutoCommittalUserRepository().getById(id);
    }

    /**
     * Returns a user that has the indicated login and password.
     * @param login to search
     * @param password to search
     * @return a user corresponding to the arguments or null if none is found
     * @throws DaoException if repository layer fails.
     */
    public User getUserByLoginAndPassword(String login, String password) throws DaoException {
        return repositoryTool.getAutoCommittalUserRepository().getUserByLoginAndPassword(login, password);
    }

    /**
     * Returns true if such login is already in the repository or false otherwise
     * @param login to search for
     * @return true if exists, false if not
     * @throws DaoException if repository layer fails.
     */
    public boolean doesSuchLoginExist(String login) throws DaoException {
        return repositoryTool.getAutoCommittalUserRepository().doesSuchLoginExist(login);
    }
}
