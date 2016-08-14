package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class TransactionalJdbcFacultyRepositoryTest extends DataSourceHolder {

    @Test
    public void testCreate() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setEnName("NameEnglish");
        faculty.setDeName("NameDeutsch");
        faculty.setRuName("Название");
        faculty.setEnDescription("EnglshDescription");
        faculty.setDeDescription("DeutscheBeschreibung");
        faculty.setRuDescription("Русское описание");
        faculty.setDateRegistrationStarts(Date.valueOf("2016-10-10"));
        faculty.setDateRegistrationEnds(Date.valueOf("2016-11-10"));
        faculty.setDateStudiesStart(Date.valueOf("2016-12-10"));
        faculty.setStudents(20);
        faculty.setMonthsToStudy(50L);

        // execute
        try(Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcFacultyRepository(connection).create(faculty);
            connection.commit();
        }

        // verify
        assertNotNull(faculty.getId());
        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT * FROM faculty WHERE id=" + faculty.getId();
            ResultSet rs = connection.createStatement().executeQuery(query);
            rs.next(); // there should only be one result
            assertEquals("NameEnglish", rs.getString("en_name"));
            assertEquals("NameDeutsch", rs.getString("de_name"));
            assertEquals("Название", rs.getString("ru_name"));
            assertEquals("EnglshDescription", rs.getString("en_description"));
            assertEquals("DeutscheBeschreibung", rs.getString("de_description"));
            assertEquals("Русское описание", rs.getString("ru_description"));
            assertEquals(Date.valueOf("2016-10-10"), rs.getDate("date_registration_starts"));
            assertEquals(Date.valueOf("2016-11-10"), rs.getDate("date_registration_ends"));
            assertEquals(Date.valueOf("2016-12-10"), rs.getDate("date_studies_start"));
            assertTrue(20 == rs.getLong("students"));
            assertTrue(50L ==  rs.getLong("months_to_study"));
        }
    }

    @Test
    public void testUpdate() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(1L);  // identifier
        faculty.setEnName("NameEnglish"); // new value
        faculty.setDeName("NameDeutsch"); // new value
        faculty.setRuName("Название"); // new value
        faculty.setEnDescription("EnglshDescription"); // new value
        faculty.setDeDescription("DeutscheBeschreibung"); // new value
        faculty.setRuDescription("Русское описание"); // new value
        faculty.setDateRegistrationStarts(Date.valueOf("2016-10-10")); // new value
        faculty.setDateRegistrationEnds(Date.valueOf("2016-11-10")); // new value
        faculty.setDateStudiesStart(Date.valueOf("2016-12-10")); // new value
        faculty.setStudents(90); // new value
        faculty.setMonthsToStudy(90L); // new value

        // execute
        try(Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcFacultyRepository(connection).update(faculty);
            connection.commit();
        }

        // verify
        try (Connection connection = dataSource.getConnection()){
            String query = "SELECT * FROM faculty WHERE id=" + faculty.getId();
            ResultSet rs = connection.createStatement().executeQuery(query);
            rs.next(); // there should only be one result
            assertEquals("NameEnglish", rs.getString("en_name"));
            assertEquals("NameDeutsch", rs.getString("de_name"));
            assertEquals("Название", rs.getString("ru_name"));
            assertEquals("EnglshDescription", rs.getString("en_description"));
            assertEquals("DeutscheBeschreibung", rs.getString("de_description"));
            assertEquals("Русское описание", rs.getString("ru_description"));
            assertEquals(Date.valueOf("2016-10-10"), rs.getDate("date_registration_starts"));
            assertEquals(Date.valueOf("2016-11-10"), rs.getDate("date_registration_ends"));
            assertEquals(Date.valueOf("2016-12-10"), rs.getDate("date_studies_start"));
            assertTrue(90 == rs.getLong("students"));
            assertTrue(90L ==  rs.getLong("months_to_study"));
        }
    }

    @Test
    public void testDelete() throws Exception {
        // setup
        Faculty faculty = new Faculty();
        faculty.setId(1L);

        // execute
        try(Connection connection = dataSource.getConnection()) {
            new TransactionalJdbcFacultyRepository(connection).delete(faculty);
            connection.commit();
        }

        // verify
        try(Connection connection = dataSource.getConnection()) {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM faculty WHERE id=1");
            assertFalse(rs.next());
        }
    }

    @Test
    public void testGetById() throws Exception {
        // execute
        Faculty faculty;
        try(Connection connection = dataSource.getConnection()) {
            faculty = new TransactionalJdbcFacultyRepository(connection).getById(1L);
            connection.commit();
        }

        // verify
        assertTrue(1L == faculty.getId());
        assertTrue(1L == faculty.getStudents());
        assertTrue(50L == faculty.getMonthsToStudy());
        assertEquals("Faculty1", faculty.getEnName());
        assertEquals("Факультет1", faculty.getRuName());
        assertEquals("Fakultät1", faculty.getDeName());
        assertEquals("Description", faculty.getEnDescription());
        assertEquals("Описание", faculty.getRuDescription());
        assertEquals("Beschreibung", faculty.getDeDescription());
        assertEquals(Date.valueOf("2016-08-10"), faculty.getDateRegistrationStarts());
        assertEquals(Date.valueOf("2016-09-29"), faculty.getDateRegistrationEnds());
        assertEquals(Date.valueOf("2016-10-10"), faculty.getDateStudiesStart());
    }

    @Test
    public void testGetAll() throws Exception {
        // execute
        Faculty[] faculties;
        try(Connection connection = dataSource.getConnection()) {
            faculties = new TransactionalJdbcFacultyRepository(connection).getAll();
            connection.commit();
        }

        // verify
        assertTrue(2 == faculties.length);
    }

    @Test
    public void testDoesSuchEnNameExist_exists_returnsTrue() throws Exception {
        // setup
        String name = "Faculty1";

        // execute
        boolean result;
        try(Connection connection = dataSource.getConnection()) {
            result = new TransactionalJdbcFacultyRepository(connection).doesSuchEnNameExist(name);
            connection.commit();
        }

        // verify
        assertTrue(result);
    }

    @Test
    public void testDoesSuchEnNameExist_doesNotExist_returnsFalse() throws Exception {
        // setup
        String name = "Brand New Name"; // such does not exist

        // execute
        boolean result;
        try(Connection connection = dataSource.getConnection()) {
            result = new TransactionalJdbcFacultyRepository(connection).doesSuchEnNameExist(name);
            connection.commit();
        }

        // verify
        assertFalse(result);
    }

    @Test
    public void testDoesSuchDeNameExist_exists_returnsTrue() throws Exception {
        // setup
        String name = "Fakultät1";

        // execute
        boolean result;
        try(Connection connection = dataSource.getConnection()) {
            result = new TransactionalJdbcFacultyRepository(connection).doesSuchDeNameExist(name);
            connection.commit();
        }

        // verify
        assertTrue(result);
    }

    @Test
    public void testDoesSuchDeNameExist_doesNotExist_returnsFalse() throws Exception {
        // setup
        String name = "Neue Fakultät"; // such does not exist

        // execute
        boolean result;
        try(Connection connection = dataSource.getConnection()) {
            result = new TransactionalJdbcFacultyRepository(connection).doesSuchDeNameExist(name);
            connection.commit();
        }

        // verify
        assertFalse(result);
    }

    @Test
    public void testDoesSuchRuNameExist_exists_returnsTrue() throws Exception {
        // setup
        String name = "Факультет1";

        // execute
        boolean result;
        try(Connection connection = dataSource.getConnection()) {
            result = new TransactionalJdbcFacultyRepository(connection).doesSuchRuNameExist(name);
            connection.commit();
        }

        // verify
        assertTrue(result);
    }

    @Test
    public void testDoesSuchRuNameExist_doesNotExist_returnsFalse() throws Exception {
        // setup
        String name = "Новое Русское Название"; // such does not exist

        // execute
        boolean result;
        try(Connection connection = dataSource.getConnection()) {
            result = new TransactionalJdbcFacultyRepository(connection).doesSuchRuNameExist(name);
            connection.commit();
        }

        // verify
        assertFalse(result);
    }
}