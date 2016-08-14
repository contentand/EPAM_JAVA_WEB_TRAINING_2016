package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.FacultyRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.sql.SqlStatements.*;

/**
 * <p>This class provides core implementation for FacultyRepository interface using JDBC and SQL.
 *
 * <p><strong>WARNING:</strong> The implementation intentionally lacks commit/rollback logic,
 * it also does not handle connection closure.
 * <p>These concerns are left for the Connection instance provider.
 * Alternatively, one can extend this class and wrap all methods adding
 * appropriate commit/rollback/close logic.
 *
 * @author Daniil Yurov
 */
public class TransactionalJdbcFacultyRepository implements FacultyRepository {

    /**
     * Instance of connection that method implementation will use.
     * The connection should have disabled auto-commit.
     */
    protected Connection connection;

    /**
     * Creates an instance of FacultyRepository using the connection passed.
     * It disables auto-commit for the connection making the commits and closure
     * to be the concern of the invoker.
     *
     * @param connection Connection to be used to execute methods.
     * @throws SQLException if it fails to setup connection.
     */
    public TransactionalJdbcFacultyRepository(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.connection = connection;
    }

    @Override
    public void create(Faculty faculty) throws DaoException {

        if (faculty == null || faculty.getId() != null) {
            throw new DaoException("You cannot faculty instances with particular id!");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FACULTY,
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            // id is set by the database automatically. We are retrieving the assigned id below
            preparedStatement.setString(1, faculty.getEnName());
            preparedStatement.setString(2, faculty.getRuName());
            preparedStatement.setString(3, faculty.getDeName());
            preparedStatement.setString(4, faculty.getEnDescription());
            preparedStatement.setString(5, faculty.getRuDescription());
            preparedStatement.setString(6, faculty.getDeDescription());
            preparedStatement.setLong(7, faculty.getStudents());
            preparedStatement.setDate(8, faculty.getDateRegistrationStarts());
            preparedStatement.setDate(9, faculty.getDateRegistrationEnds());
            preparedStatement.setDate(10, faculty.getDateStudiesStart());
            preparedStatement.setLong(11, faculty.getMonthsToStudy());

            preparedStatement.execute();

            // get the id database assigned and set it into the faculty instance.
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    faculty.setId(generatedKeys.getLong(1));
                }
            }

            // persist faculty requirements SHOULD BE AFTER GETTING THE FACULTY ID!
            setAllRequiredSubjectsInBatch(faculty);

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to create faculty instance. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Faculty faculty) throws DaoException {

        if (faculty == null || faculty.getId() == null) {
            throw new DaoException("You cannot update faculties with no id!");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FACULTY))
        {
            preparedStatement.setString(1, faculty.getEnName());
            preparedStatement.setString(2, faculty.getRuName());
            preparedStatement.setString(3, faculty.getDeName());
            preparedStatement.setString(4, faculty.getEnDescription());
            preparedStatement.setString(5, faculty.getRuDescription());
            preparedStatement.setString(6, faculty.getDeDescription());
            preparedStatement.setLong(7, faculty.getStudents());
            preparedStatement.setDate(8, faculty.getDateRegistrationStarts());
            preparedStatement.setDate(9, faculty.getDateRegistrationEnds());
            preparedStatement.setDate(10, faculty.getDateStudiesStart());
            preparedStatement.setLong(11, faculty.getMonthsToStudy());
            preparedStatement.setLong(12, faculty.getId());
            preparedStatement.execute();

            deleteAllRequiredSubjects(faculty);
            setAllRequiredSubjectsInBatch(faculty);

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to update faculty. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Faculty faculty) throws DaoException {

        if (faculty.getId() == null) {
            throw new DaoException("You cannot delete faculties with no id!");
        }

        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_FACULTY))
        {
            preparedStatement.setLong(1, faculty.getId());
            deleteAllRequiredSubjects(faculty);
            deleteAllApplications(faculty);
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to delete the faculty. " + e.getMessage(), e);
        }
    }

    @Override
    public Faculty getById(long id) throws DaoException {
        Faculty faculty;

        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_FACULTY_BY_ID))
        {
            preparedStatement.setLong(1, id);
            faculty = extractFacultiesFromResultSet(preparedStatement).stream().findFirst().orElse(null);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve faculty instance. " + e.getMessage(), e);
        }

        return faculty;
    }

    @Override
    public Faculty[] getAll() throws DaoException {
        Set<Faculty> faculties;

        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FACULTIES))
        {
            faculties = extractFacultiesFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve faculty instances. " + e.getMessage(), e);
        }

        return faculties.stream().toArray(Faculty[]::new);
    }

    @Override
    public boolean doesSuchEnNameExist(String enName) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_FACULTIES_WITH_EN_NAME)){
            preparedStatement.setString(1, enName);
            return !isCountZero(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException("Unable to access faculty info. " + e.getMessage(), e);
        }
    }

    @Override
    public boolean doesSuchDeNameExist(String deName) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_FACULTIES_WITH_DE_NAME)){
            preparedStatement.setString(1, deName);
            return !isCountZero(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException("Unable to access faculty info. " + e.getMessage(), e);
        }
    }

    @Override
    public boolean doesSuchRuNameExist(String ruName) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(COUNT_FACULTIES_WITH_RU_NAME)){
            preparedStatement.setString(1, ruName);
            return !isCountZero(preparedStatement);
        } catch (SQLException e) {
            throw new DaoException("Unable to access faculty info. " + e.getMessage(), e);
        }
    }

    private Set<Faculty> extractFacultiesFromResultSet(PreparedStatement preparedStatement)
            throws SQLException, DaoException {
        Set<Faculty> faculties = new LinkedHashSet<>();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                faculty.setId(resultSet.getLong("id"));
                faculty.setEnName(resultSet.getString("en_name"));
                faculty.setRuName(resultSet.getString("ru_name"));
                faculty.setDeName(resultSet.getString("de_name"));
                faculty.setEnDescription(resultSet.getString("en_description"));
                faculty.setRuDescription(resultSet.getString("ru_description"));
                faculty.setDeDescription(resultSet.getString("de_description"));
                faculty.setStudents(resultSet.getInt("students"));
                faculty.setDateRegistrationStarts(resultSet.getDate("date_registration_starts"));
                faculty.setDateRegistrationEnds(resultSet.getDate("date_registration_ends"));
                faculty.setDateStudiesStart(resultSet.getDate("date_studies_start"));
                faculty.setMonthsToStudy(resultSet.getLong("months_to_study"));
                faculty.setRequiredSubjects(getRequiredSubjects(faculty));

                faculties.add(faculty);
            }
        }
        return faculties;
    }

    private boolean isCountZero(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count == 0) return true;
        }
        return false;
    }

    private void setAllRequiredSubjectsInBatch(Faculty faculty) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(SET_REQUIRED_SUBJECTS_FOR_FACULTY)) {

            for (Subject subject : faculty.getRequiredSubjects()) {
                preparedStatement.setLong(1, faculty.getId());
                preparedStatement.setLong(2, subject.getId());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to persists subjects required for the faculty. " + e.getMessage(), e);
        }
    }

    private void deleteAllRequiredSubjects(Faculty faculty) throws DaoException {

        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_REQUIRED_SUBJECTS_FOR_FACULTY)) {
            preparedStatement.setLong(1, faculty.getId());
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to remove old subjects required for the faculty. " + e.getMessage(), e);
        }
    }

    private void deleteAllApplications(Faculty faculty) throws DaoException {

        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE_APPLICATIONS_FOR_FACULTY)) {
            preparedStatement.setLong(1, faculty.getId());
            preparedStatement.execute();
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to remove applications for the faculty. " + e.getMessage(), e);
        }
    }

    private Set<Subject> getRequiredSubjects(Faculty faculty) throws DaoException {

        Set<Subject> subjects = new LinkedHashSet<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_REQUIRED_SUBJECTS_FOR_FACULTY)) {
            preparedStatement.setLong(1, faculty.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Subject subject = new Subject();
                    subject.setId(resultSet.getLong("id"));
                    subject.setEnName(resultSet.getString("en_name"));
                    subject.setDeName(resultSet.getString("de_name"));
                    subject.setRuName(resultSet.getString("ru_name"));
                    subjects.add(subject);
                }
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to read subjects required for the faculty. " + e.getMessage(), e);
        }

        return subjects;
    }

}
