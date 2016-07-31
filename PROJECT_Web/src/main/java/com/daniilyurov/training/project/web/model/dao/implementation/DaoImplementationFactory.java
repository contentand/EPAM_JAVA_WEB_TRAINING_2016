package com.daniilyurov.training.project.web.model.dao.implementation;


import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.JdbcRepositoryManagerFactory;

import javax.sql.DataSource;

public class DaoImplementationFactory {

    public static RepositoryManagerFactory getRepositoryManagerFactory(Type type, DataSource dataSource) {

        if (type == null) throw new NullPointerException();
        return type.getAccess(dataSource);

    }

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
