package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util;


import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class to contain repetitive code connected with JDBC Connection.
 * Thus less typing is requires, and modification here will apply to any code that uses it.
 *
 * @author Daniil Yurov
 */
public class CommitAndCloseUtil {

    /**
     * Tries to execute passed operation and then commit and close the connection passed.
     * If it fails to commit, it throws a DaoException with the errorMessage passed as an argument.
     *
     * @param function operation to execute before committing
     * @param connection connection that should be committed and closed after the operation execution
     * @param errorMessage String to pass into DaoException in case commit fails
     * @throws DaoException if operation throws such or if the method fails to commit
     */
    public static void executeOperationThenCommitAndCloseConnection(EmptyDaoOperator function,
                                                                    Connection connection, String errorMessage)
            throws DaoException {

        try {
            function.execute();
            connection.commit();
        }
        catch (SQLException e) {
            throw new DaoException(errorMessage + " " + e.getMessage(), e);
        }
        finally {
            tryClosing(connection);
        }

    }

    /**
     * Tries to get an instance from the passed source function and then closes the connection passed.
     *
     * @param source function that provides desired instance when called.
     * @param connection connection that should be closed after source function provides the desired instance.
     * @param <T> instance type requested.
     * @return instance that source function provides or null if it provides nothing.
     * @throws DaoException if source function throws one.
     */
    public static <T> T getFromSourceThenCloseConnection(DaoSupplier<T> source, Connection connection)
            throws DaoException {
        T instance = null;
        try {
            instance = source.get();
        } finally {
            tryClosing(connection);
        }
        return instance;
    }

    // helper method to prevent code repetition and increase code readability
    private static void tryClosing(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO log!
            }
        }
    }

}
