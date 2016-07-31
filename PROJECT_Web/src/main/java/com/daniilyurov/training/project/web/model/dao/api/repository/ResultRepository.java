package com.daniilyurov.training.project.web.model.dao.api.repository;


import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.util.Collection;

/**
 * Defines the concrete ResultRepository interface.
 * It declares additional unique methods not defined in the GenericRepository super-interface.
 *
 * @author Daniil Yurov
 */

public interface ResultRepository extends GenericRepository<Result> {

    /**
     * Gets all results of the user passed.
     *
     * @param user to search
     * @throws DaoException if it fails during lookup process.
     */
    Result[] getAllOf(User user) throws DaoException;

    /**
     * Persists all results passed.
     *
     * @param results to persist
     * @throws DaoException if it fails during creation process.
     */
    void createAll(Collection<Result> results) throws DaoException;
}
