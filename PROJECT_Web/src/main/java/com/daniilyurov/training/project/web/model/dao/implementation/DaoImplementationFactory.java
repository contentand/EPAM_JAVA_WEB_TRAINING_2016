package com.daniilyurov.training.project.web.model.dao.implementation;


import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.JdbcRepositoryManagerFactory;

import javax.sql.DataSource;

/**
 * DaoImplementationFactory contains static fabric method to retrieve RepositoryManagerFactory
 * of a particular type.
 */
public class DaoImplementationFactory {

    /**
     * Returns a RepositoryManagerFactory corresponding to the indicated type.
     * @param type of RepositoryManagerFactory
     * @param dataSource to build the repository from
     * @return repository manager factory
     */
    public static RepositoryManagerFactory getRepositoryManagerFactory(Type type, DataSource dataSource) {
        if (type == null) throw new NullPointerException();
        return type.getAccess(dataSource);
    }

    /**
     * Contains enumeration of all repository types supported.
     */
    public enum Type {

        JDBC {
            @Override
            public RepositoryManagerFactory getAccess(DataSource dataSource) {
                return new JdbcRepositoryManagerFactory(dataSource);
            }
        };

        public abstract RepositoryManagerFactory getAccess(DataSource dataSource);

    }
}
