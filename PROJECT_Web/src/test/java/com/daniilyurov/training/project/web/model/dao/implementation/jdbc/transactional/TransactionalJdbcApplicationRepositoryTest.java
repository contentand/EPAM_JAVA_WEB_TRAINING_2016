package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.apache.commons.dbcp2.BasicDataSource;
import org.h2.Driver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TransactionalJdbcApplicationRepositoryTest {

    BasicDataSource dataSource;

    @Before
    public void setup() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:h2:mem:test;INIT=runscript from 'classpath:base.sql'\\;"); // runs init script
    }

    @After
    public void tearDown() throws SQLException {
        dataSource.close();
    }

    @Test
    public void create_always_creates() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        User user = new User();
        user.setId(2L);
        Date date = Date.valueOf("2016-10-10");

        Application application = new Application();
        application.setFaculty(faculty);
        application.setUser(user);
        application.setStatus(Application.Status.APPLIED);
        application.setDateStudiesStart(date);
        application.setMonthsToStudy(50L);

        // execute
        try (Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcApplicationRepository(connection).create(application);
            connection.commit();
        }

        // verify
        assertNotNull(application.getId());
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM application WHERE id=" + application.getId();
            ResultSet rs = connection.createStatement().executeQuery(query);
            rs.next();
            assertTrue(rs.getLong("faculty_id") == faculty.getId());
            assertTrue(rs.getLong("applicant_id") == user.getId());
            assertEquals(Application.Status.APPLIED.name(), rs.getString("status"));
            assertEquals(date, rs.getDate("date_studies_start"));
            assertTrue(rs.getLong("months_to_study") == 50L);
        }
    }

    @Test
    public void update_always_updates() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(2L);
        User user = new User();
        user.setId(1L);
        Date date = Date.valueOf("2016-10-10");

        Application application = new Application();
        application.setId(1L); // identifier
        application.setFaculty(faculty); // same
        application.setUser(user); // same
        application.setStatus(Application.Status.APPLIED); // !different: used to be CANCELLED
        application.setDateStudiesStart(date); // same
        application.setMonthsToStudy(50L); // same

        // execute
        try (Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcApplicationRepository(connection).update(application);
            connection.commit();
        }

        // verify
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM application WHERE id=" + application.getId();
            ResultSet rs = connection.createStatement().executeQuery(query);
            rs.next();
            assertEquals(Application.Status.APPLIED.name(), rs.getString("status"));
        }
    }

    @Test
    public void delete_always_deletes() throws Exception {
        // setup
        Application application = new Application();
        application.setId(1L); // identifier

        // execute
        try (Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcApplicationRepository(connection).delete(application);
            connection.commit();
        }

        // verify
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM application WHERE id=" + application.getId();
            ResultSet rs = connection.createStatement().executeQuery(query);
            assertFalse(rs.next());
        }
    }

    @Test
    public void testGetById() throws Exception {
        // setup
        Long applicationId = 2L; // valid identifier

        // execute
        Application application;
        try (Connection connection = dataSource.getConnection()) {
            application = new TransactionalJdbcApplicationRepository(connection).getById(applicationId);
            connection.commit();
        }

        // verify
        assertNotNull(application);
        assertEquals(applicationId, application.getId());
        assertTrue(1L == application.getFaculty().getId());
        assertTrue(1L == application.getUser().getId());
        assertTrue(50L == application.getMonthsToStudy());
        assertEquals(Date.valueOf("2016-10-10"), application.getDateStudiesStart());
        assertEquals(Application.Status.CANCELLED, application.getStatus());
    }

    @Test
    public void testGetAll() throws Exception {
        // execute
        Application[] application;
        try (Connection connection = dataSource.getConnection()) {
            application = new TransactionalJdbcApplicationRepository(connection).getAll();
            connection.commit();
        }

        // verify
        assertTrue(5 == application.length);
    }

    @Test
    public void testGetAllOfUser() throws Exception {
        // setup
        User user = new User();
        user.setId(1L);

        Application[] application;
        try (Connection connection = dataSource.getConnection()) {
            application = new TransactionalJdbcApplicationRepository(connection).getAllOf(user);
            connection.commit();
        }

        // verify
        assertTrue(3 == application.length);
    }

    @Test
    public void testGetLastOfUserForFaculty() throws Exception {
        // setup
        Long userId = 1L;
        Faculty faculty = new Faculty();
        faculty.setId(1L);

        // execute
        Application application;
        try (Connection connection = dataSource.getConnection()) {
            application = new TransactionalJdbcApplicationRepository(connection).getLastOf(faculty, userId);
            connection.commit();
        }

        // verify
        assertNotNull(application);
        assertTrue(2L == application.getId());
    }

    @Test
    public void testGetAllOfFacultyAndStatus() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        Application.Status status = Application.Status.APPLIED;

        // execute
        Application[] applications;
        try (Connection connection = dataSource.getConnection()) {
            applications = new TransactionalJdbcApplicationRepository(connection).getAllOf(faculty, status);
            connection.commit();
        }

        // verify
        assertTrue(1 == applications.length);
        assertTrue(4L == applications[0].getId());
    }

    @Test
    public void testGetAllOfFacultyOfDate() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        Date date = Date.valueOf("2013-10-10");

        // execute
        Application[] applications;
        try (Connection connection = dataSource.getConnection()) {
            applications = new TransactionalJdbcApplicationRepository(connection).getAllOf(faculty, date);
            connection.commit();
        }

        // verify
        assertTrue(1 == applications.length);
        assertTrue(1L == applications[0].getId());
    }

    @Test
    public void testCountAllOfFacultyAndStatus() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        Application.Status status = Application.Status.APPLIED;

        // execute
        Long count;
        try (Connection connection = dataSource.getConnection()) {
            count = new TransactionalJdbcApplicationRepository(connection).countAllOf(faculty, status);
            connection.commit();
        }

        // verify
        assertTrue(1L == count);
    }

    @Test
    public void testCountAllOfUserStatus() throws Exception {
        // setup
        Long userId = 1L;
        Application.Status status = Application.Status.APPLIED;

        // execute
        Long count;
        try (Connection connection = dataSource.getConnection()) {
            count = new TransactionalJdbcApplicationRepository(connection).countAllOf(userId, status);
            connection.commit();
        }

        // verify
        assertTrue(1L == count);
    }

    @Test
    public void testUpdateAll() throws Exception {
        // setup
        Set<Long> ids = new HashSet<>();
        ids.add(1L); // used to be QUIT
        ids.add(2L); // used to be CANCELLED
        Application.Status status = Application.Status.APPLIED;

        // execute
        try (Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcApplicationRepository(connection).updateAll(ids, status);
            connection.commit();
        }

        // verify
        try (Connection connection = dataSource.getConnection()) {
            String query = "SELECT * FROM application WHERE id=1 AND id=2";
            ResultSet rs = connection.createStatement().executeQuery(query);
            while(rs.next()) {
                assertEquals(Application.Status.APPLIED.name(), rs.getString("status"));
            }
        }
    }
}