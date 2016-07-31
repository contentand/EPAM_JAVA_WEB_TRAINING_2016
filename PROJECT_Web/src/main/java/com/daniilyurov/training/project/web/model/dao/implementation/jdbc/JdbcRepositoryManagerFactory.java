package com.daniilyurov.training.project.web.model.dao.implementation.jdbc;


import com.daniilyurov.training.project.web.model.dao.api.AutoCommittalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.api.TransactionalRepositoryManager;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * The class provides methods to obtain instances of concrete RepositoryManagers
 * using data source instance passed upon factory creation.
 *
 * @author Daniil Yurov
 */
public final class JdbcRepositoryManagerFactory implements RepositoryManagerFactory {

    private DataSource dataSource;

    /**
     * Creates an instance of RepositoryManagerFactory.
     *
     * @param dataSource instance that will be user to construct concrete RepositoryManagers.
     */
    public JdbcRepositoryManagerFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public AutoCommittalRepositoryManager getAutoCommittalRepositoryManager() {
        return new JdbcAutoCommittalRepositoryManager(dataSource);
    }

    @Override
    public TransactionalRepositoryManager getTransactionalRepositoryManager() throws DaoException {
        try {
            return new JdbcTransactionalRepositoryManager(dataSource.getConnection());
        } catch (SQLException e) {
            throw new DaoException("Failed to create Transactional Repository Manager. " + e.getMessage(), e);
        }
    }
}
