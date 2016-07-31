package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util;

import com.daniilyurov.training.project.web.model.dao.api.DaoException;

/**
 * Helper functional interface.
 * It's method takes no arguments and returns nothing but can throw DaoException.
 *
 * @author Daniil Yurov
 */
@FunctionalInterface
public interface EmptyDaoOperator {

    /**
     * Executes statements passed.
     * Consumes no arguments, supplies no value.
     *
     * @throws DaoException if any statement inside throws uncaught DaoException.
     */
    void execute() throws DaoException;
}
