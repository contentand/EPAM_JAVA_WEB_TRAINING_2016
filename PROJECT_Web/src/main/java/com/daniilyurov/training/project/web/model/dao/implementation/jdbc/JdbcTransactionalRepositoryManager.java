package com.daniilyurov.training.project.web.model.dao.implementation.jdbc;

import com.daniilyurov.training.project.web.model.dao.api.TransactionalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.*;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>A class that generates concrete Jdbc Repositories that do not auto-commit and do not close connection.
 * In order for changes invoked by the generated repositories to take place one should explicitly call commit method.
 * Otherwise one should call rollback.</p>
 *
 * <p><strong>WARNING:</strong>Forgetting to call either commit or rollback can cause unnecessary data
 * consistency and/or performance issues.</p>
 *
 * @author Daniil Yurov
 */
public class JdbcTransactionalRepositoryManager implements TransactionalRepositoryManager {

    private Connection connection;
    private boolean isCommitted;

    /**
     * Starts a transaction within the passed connection.
     * And creates an instance of JdbcTransactionalRepositoryManager.
     *
     * @param connection Connection instance that the transaction will use.
     */
    public JdbcTransactionalRepositoryManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public final void commit() throws DaoException {
        try {
            connection.commit();
        } catch (SQLException e) {
            throw new DaoException("Unable to close the connection.",e); // TODO ensure legal
        } finally {
            closeConnection();
            isCommitted = true;
        }
    }

    @Override
    public final void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            // TODO log!
        }
        finally {
            closeConnection();
        }
    }

    @Override
    public boolean isCommitted() {
        return isCommitted;
    }

    @Override
    public UserRepository getUserRepository() throws DaoException {
        try {
            return new TransactionalJdbcUserRepository(connection);
        } catch (SQLException e) {
            throw new DaoException("Unable to get user repository.",e); // TODO ensure legal
        }

    }

    @Override
    public ResultRepository getResultRepository() throws DaoException {
        try {
            return new TransactionalJdbcResultRepository(connection);
        } catch (SQLException e) {
            throw new DaoException("Unable to get result repository.",e); // TODO ensure legal
        }
    }

    @Override
    public FacultyRepository getFacultyRepository() throws DaoException {
        try {
            return new TransactionalJdbcFacultyRepository(connection);
        } catch (SQLException e) {
            throw new DaoException("Unable to get faculty repository.",e); // TODO ensure legal
        }
    }

    @Override
    public ApplicationRepository getApplicationRepository() throws DaoException {
        try {
            return new TransactionalJdbcApplicationRepository(connection);
        } catch (SQLException e) {
            throw new DaoException("Unable to get application repository.",e); // TODO ensure legal
        }
    }

    @Override
    public SubjectRepository getSubjectRepository() throws DaoException {
        try {
            return new TransactionalJdbcSubjectRepository(connection);
        } catch (SQLException e) {
            throw new DaoException("Unable to get subject repository.",e); // TODO ensure legal
        }
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // TODO log!
            }
        }
    }
}
