package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.ApplicationRepository;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * <p>This class provides core implementation for ApplicationRepository interface using JDBC and SQL.
 *
 * <p><strong>WARNING:</strong> The implementation intentionally lacks commit/rollback logic,
 * it also does not handle connection closure.
 * <p>These concerns are left for the Connection instance provider.
 * Alternatively, one can extend this class and wrap all methods adding
 * appropriate commit/rollback/close logic.
 *
 * @author Daniil Yurov
 */
public class TransactionalJdbcApplicationRepository implements ApplicationRepository {

    /**
     * Instance of connection that method implementation will use.
     * The connection should have disabled auto-commit.
     */
    protected Connection connection;

    /**
     * Creates an instance of ApplicationRepository using the connection passed.
     * It disables auto-commit for the connection making the commits and closure
     * to be the concern of the invoker.
     *
     * @param connection Connection to be used to execute methods.
     * @throws SQLException if it fails to setup connection.
     */
    public TransactionalJdbcApplicationRepository(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.connection = connection;
    }

    @Override
    public void create(Application application) throws DaoException {
        if (application == null || application.getId() != null) {
            throw new DaoException("You cannot application instances with particular id!");
        }

        String query =
                "INSERT INTO application (faculty_id, applicant_id, status, date_studies_start, months_to_study) " +
                        "VALUES (?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            // id is set by the database automatically. We are retrieving the assigned id below
            preparedStatement.setLong(1, application.getFaculty().getId());
            preparedStatement.setLong(2, application.getUser().getId());
            preparedStatement.setString(3, application.getStatus().name());
            preparedStatement.setDate(4, application.getDateStudiesStart());
            preparedStatement.setLong(5, application.getMonthsToStudy());

            preparedStatement.execute();

            // get the id database assigned and set it into the application instance.
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    application.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to create application instance. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Application application) throws DaoException {
        if (application.getId() == null) {
            throw new DaoException("You cannot update applications with no id!");
        }

        String query =
                "UPDATE application SET " +
                        "status = ?, date_studies_start = ?, months_to_study = ? " +
                        "WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, application.getStatus().name());
            preparedStatement.setDate(2, application.getDateStudiesStart());
            preparedStatement.setLong(3, application.getMonthsToStudy());
            preparedStatement.setLong(4, application.getId());

            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to update application. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Application application) throws DaoException {
        if (application.getId() == null) {
            throw new DaoException("You cannot delete applications with no id!");
        }

        String query = "DELETE FROM application WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, application.getId());
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to delete application. " + e.getMessage(), e);
        }
    }

    @Override
    public Application getById(long id) throws DaoException {

        Application application;

        String query = "SELECT application.id, application.faculty_id, application.applicant_id, application.status, " +
                "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
                "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
                "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
                "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
                "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
                "user.cyrillic_last_name, user.average_school_result " +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "JOIN user ON application.applicant_id = user.id " +
                "WHERE application.id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            application = extractApplicationsFromResultSet(preparedStatement).stream().findFirst().orElse(null);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve application. " + e.getMessage(), e);
        }

        return application;
    }

    @Override
    public Application[] getAll() throws DaoException {
        Set<Application> applications;

        String query = "SELECT application.id, application.faculty_id, application.applicant_id, application.status, " +
                "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
                "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
                "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
                "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
                "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
                "user.cyrillic_last_name, user.average_school_result " +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "JOIN user ON application.applicant_id = user.id";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {

            preparedStatement.execute();

            applications = extractApplicationsFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve applications. " + e.getMessage(), e);
        }

        return applications.stream().toArray(Application[]::new);
    }

    @Override
    public Application[] getAllOf(User user) throws DaoException {

        if (user.getId() == null) {
            throw new DaoException("You cannot select applications of a user that has no id!");
        }

        Set<Application> applications;

        String query = "SELECT application.id, application.faculty_id, application.applicant_id, application.status, " +
                "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
                "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
                "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
                "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
                "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
                "user.cyrillic_last_name, user.average_school_result " +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "JOIN user ON application.applicant_id = user.id " +
                "WHERE application.applicant_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, user.getId());
            applications = extractApplicationsFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve applications of user. " + e.getMessage(), e);
        }

        return applications.stream().toArray(Application[]::new);
    }

    @Override
    public Application getLastOf(Faculty faculty, Long userId) throws DaoException {
        if (faculty.getId() == null) {
            throw new DaoException("You cannot select applications of a faculty that has no id!");
        }
        if (userId == null) {
            throw new DaoException("You cannot select applications of a user that has no id!");
        }

        Application application;

        String query = "SELECT application.id, application.faculty_id, application.applicant_id, application.status, " +
                "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
                "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
                "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
                "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
                "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
                "user.cyrillic_last_name, user.average_school_result " +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "JOIN user ON application.applicant_id = user.id " +
                "WHERE application.faculty_id = ? AND application.applicant_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, faculty.getId());
            preparedStatement.setLong(2, userId);
            application = extractApplicationsFromResultSet(preparedStatement).stream().findFirst().orElse(null);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve applications for particular faculty with particular status. "
                    + e.getMessage(), e);
        }

        return application;
    }

    @Override
    public Application[] getAllOf(Faculty faculty, Application.Status status) throws DaoException {
        if (faculty.getId() == null) {
            throw new DaoException("You cannot select applications of a faculty that has no id!");
        }
        if (status == null) {
            throw new DaoException("You cannot select applications of no status!");
        }

        Set<Application> applications;

        String query = "SELECT application.id, application.faculty_id, application.applicant_id, application.status, " +
                "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
                "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
                "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
                "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
                "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
                "user.cyrillic_last_name, user.average_school_result " +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "JOIN user ON application.applicant_id = user.id " +
                "WHERE application.faculty_id = ? AND application.status = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, faculty.getId());
            preparedStatement.setString(2, status.name());
            applications = extractApplicationsFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve applications for particular faculty with particular status. "
                    + e.getMessage(), e);
        }

        return applications.stream().toArray(Application[]::new);
    }

    @Override
    public Application[] getAllOf(Faculty faculty, Date studyStartDate) throws DaoException {
        if (faculty.getId() == null) {
            throw new DaoException("You cannot select applications of a faculty that has no id!");
        }
        if (studyStartDate == null) {
            throw new DaoException("You cannot select applications of no date studies start!");
        }

        Set<Application> applications;

        String query = "SELECT application.id, application.faculty_id, application.applicant_id, application.status, " +
                "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
                "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
                "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
                "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
                "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
                "user.cyrillic_last_name, user.average_school_result " +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "JOIN user ON application.applicant_id = user.id " +
                "WHERE application.faculty_id = ? AND application.date_studies_start = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, faculty.getId());
            preparedStatement.setDate(2, studyStartDate);
            applications = extractApplicationsFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve applications for particular faculty " +
                    "with particular studyStartDate. "
                    + e.getMessage(), e);
        }

        return applications.stream().toArray(Application[]::new);
    }

    @Override
    public long countAllOf(Faculty faculty, Application.Status status) throws DaoException {
        if (faculty.getId() == null) {
            throw new DaoException("You cannot select applications of a faculty that has no id!");
        }
        if (status == null) {
            throw new DaoException("You cannot select applications of no status!");
        }

        String query = "SELECT COUNT(*)" +
                "FROM application " +
                "JOIN faculty ON application.faculty_id = faculty.id " +
                "WHERE application.faculty_id = ? AND application.status = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, faculty.getId());
            preparedStatement.setString(2, status.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("COUNT(*)");
                } else {
                    throw new NullPointerException();
                }
            }

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to count applications for particular faculty with particular status.  "
                    + e.getMessage(), e);
        }
    }

    @Override
    public long countAllOf(Long userId, Application.Status status) throws DaoException {
        if (userId == null) {
            throw new DaoException("You cannot select applications of a user that has no id!");
        }
        if (status == null) {
            throw new DaoException("You cannot select applications of no status!");
        }

        String query = "SELECT COUNT(*)" +
                "FROM application " +
                "WHERE application.applicant_id = ? AND application.status = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, status.name());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("COUNT(*)");
                } else {
                    throw new NullPointerException();
                }
            }

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to count applications of particular user with particular status.  "
                    + e.getMessage(), e);
        }
    }

    @Override
    public void updateAll(Set<Long> applicationIds, Application.Status status) throws DaoException {
        if (applicationIds == null) {
            throw new DaoException("Null application is passed!");
        }
        if (status == null) {
            throw new DaoException("Null status is passed!");
        }

        String query = "UPDATE application SET status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Long id : applicationIds) {
                preparedStatement.setString(1, status.name());
                preparedStatement.setLong(2, id);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            throw new DaoException("Unable to update applications.  "
                    + e.getMessage(), e);
        }
    }

    private Set<Application> extractApplicationsFromResultSet(PreparedStatement preparedStatement) throws SQLException {

        Set<Application> applications = new LinkedHashSet<>();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Application application = new Application();
                application.setId(resultSet.getLong("application.id"));
                application.setStatus(Application.Status.valueOf(resultSet.getString("application.status")));
                application.setDateStudiesStart(resultSet.getDate("application.date_studies_start"));
                application.setMonthsToStudy(resultSet.getLong("application.months_to_study"));

                Faculty faculty = new Faculty();
                faculty.setId(resultSet.getLong("application.faculty_id"));
                faculty.setEnName(resultSet.getString("faculty.en_name"));
                faculty.setDeName(resultSet.getString("faculty.de_name"));
                faculty.setRuName(resultSet.getString("faculty.ru_name"));
                faculty.setEnDescription(resultSet.getString("faculty.en_description"));
                faculty.setDeDescription(resultSet.getString("faculty.de_description"));
                faculty.setRuDescription(resultSet.getString("faculty.ru_description"));
                faculty.setStudents(resultSet.getInt("faculty.students"));
                faculty.setDateRegistrationStarts(resultSet.getDate("faculty.date_registration_starts"));
                faculty.setDateRegistrationEnds(resultSet.getDate("faculty.date_registration_ends"));
                faculty.setDateStudiesStart(resultSet.getDate("faculty.date_studies_start"));
                faculty.setMonthsToStudy(resultSet.getLong("faculty.months_to_study"));

                application.setFaculty(faculty);

                User applicant = new User();
                applicant.setId(resultSet.getLong("application.applicant_id"));
                applicant.setLogin(resultSet.getString("user.login"));
                applicant.setPassword(resultSet.getString("user.password"));
                applicant.setEmail(resultSet.getString("user.email"));
                applicant.setRole(resultSet.getString("user.authority"));
                applicant.setLocale(new Locale(resultSet.getString("user.locale")));
                applicant.setLatinFirstName(resultSet.getString("user.latin_first_name"));
                applicant.setLatinLastName(resultSet.getString("user.latin_last_name"));
                applicant.setCyrillicFirstName(resultSet.getString("user.cyrillic_first_name"));
                applicant.setCyrillicLastName(resultSet.getString("user.cyrillic_last_name"));
                applicant.setAverageSchoolResult(resultSet.getDouble("user.average_school_result"));

                application.setUser(applicant);

                applications.add(application);
            }
        }

        return applications;
    }
}
