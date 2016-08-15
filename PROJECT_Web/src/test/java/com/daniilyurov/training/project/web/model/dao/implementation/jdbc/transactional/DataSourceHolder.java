package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.Driver;
import org.junit.After;
import org.junit.Before;

import java.sql.SQLException;

/**
 * Utility abstraction for all Repository tests to
 * extend.
 */
public class DataSourceHolder {
    BasicDataSource dataSource;

    @Before
    public void setup() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:h2:mem:test;INIT=runscript from 'classpath:sript.sql'\\;"); // runs init script
    }

    @After
    public void tearDown() throws SQLException {
        dataSource.close();
    }
}
