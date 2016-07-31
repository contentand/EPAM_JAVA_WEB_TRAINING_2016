package com.daniilyurov.training.project.web.model.dao.implementation.jdbc;


import com.daniilyurov.training.project.web.model.dao.api.AutoCommittalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.*;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.autocommittable.*;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * A class that generates concrete Jdbc Repositories that do automatic commit and Connection closure
 * upon each method call.
 *
 * @author Daniil Yurov
 */
public class JdbcAutoCommittalRepositoryManager implements AutoCommittalRepositoryManager {

    private DataSource dataSource;

    /**
     * Creates an instance of JdbcAutoCommittalRepositoryManager
     *
     * @param dataSource DataSource used to generate Jdbc Connections for each concrete JdbcAutoCommittalRepository.
     */
    public JdbcAutoCommittalRepositoryManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UserRepository getUserRepository() throws DaoException {
        try {
            return new AutoCommittalJdbcUserRepository(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Failed to create User Repository. " + e.getMessage(), e);
        }
    }

    @Override
    public ResultRepository getResultRepository() throws DaoException {
        try {
            return new AutoCommittalJdbcResultRepository(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Failed to create Result Repository. " + e.getMessage(), e);
        }
    }

    @Override
    public FacultyRepository getFacultyRepository() throws DaoException {
        try {
            return new AutoCommittalJdbcFacultyRepository(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Failed to create Faculty Repository. " + e.getMessage(), e);
        }
    }

    @Override
    public ApplicationRepository getApplicationRepository() throws DaoException {
        try {
            return new AutoCommittalJdbcApplicationRepository(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Failed to create Application Repository. " + e.getMessage(), e);
        }
    }

    @Override
    public SubjectRepository getSubjectRepository() throws DaoException {
        try {
            return new AutoCommittalJdbcSubjectRepository(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Failed to create Subject Repository. " + e.getMessage(), e);
        }
    }
}
