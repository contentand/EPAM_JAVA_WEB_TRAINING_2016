package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util;


import com.daniilyurov.training.project.web.model.dao.api.DaoException;

/**
 * Helper functional interface.
 * It's method takes no arguments but returns an instance of generic type.
 * It can throw DaoException.
 *
 * @author Daniil Yurov
 */
@FunctionalInterface
public interface DaoSupplier<T> {

    /**
     * Executes statements passed and returns an instance.
     *
     * @return an instance produced by the function statements or null.
     * @throws DaoException if any statement inside throws uncaught DaoException.
     */
    T get() throws DaoException;
}
