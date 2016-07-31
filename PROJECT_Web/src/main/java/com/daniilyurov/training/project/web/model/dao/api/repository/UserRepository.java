package com.daniilyurov.training.project.web.model.dao.api.repository;


import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

/**
 * Defines the concrete UserRepository interface.
 * It declares additional unique methods not defined in the GenericRepository super-interface.
 *
 * @author Daniil Yurov
 */

public interface UserRepository extends GenericRepository<User> {

    /**
     * Gets the user with such login and password combination.
     *
     * @param login user login
     * @param password user password
     * @return corresponding User instance or null if such instance is absent
     * @throws DaoException if it fails during User instance retrieval
     */
    User getUserByLoginAndPassword(String login, String password) throws DaoException;

    /**
     * Checks if there is a User with the indicated login.
     *
     * @param login login to be checked
     * @return true if there is already a User with the login or false otherwise.
     * @throws DaoException if it fails during lookup process
     */
    boolean doesSuchLoginExist(String login) throws DaoException;

}
