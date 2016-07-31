package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.SubjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>This class provides core implementation for SubjectRepository interface using JDBC and SQL.
 *
 * <p><strong>WARNING:</strong> The implementation intentionally lacks commit/rollback logic,
 * it also does not handle connection closure.
 * <p>These concerns are left for the Connection instance provider.
 * Alternatively, one can extend this class and wrap all methods adding
 * appropriate commit/rollback/close logic.
 *
 * @author Daniil Yurov
 */
public class TransactionalJdbcSubjectRepository implements SubjectRepository {

    /**
     * Instance of connection that method implementation will use.
     * The connection should have disabled auto-commit.
     */
    protected Connection connection;

    /**
     * Creates an instance of SubjectRepository using the connection passed.
     * It disables auto-commit for the connection making the commits and closure
     * to be the concern of the invoker.
     *
     * @param connection Connection to be used to execute methods.
     * @throws SQLException if it fails to setup connection.
     */
    public TransactionalJdbcSubjectRepository(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.connection = connection;
    }

    @Override
    public void create(Subject subject) throws DaoException {

        if (subject.getId() != null) {
            throw new DaoException("You cannot create subjects with particular id!");
        }

        String query =
                "INSERT INTO subject " +
                        "(en_name, ru_name, de_name)" +
                        "VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            // id is set by the database automatically. We are retrieving the assigned id below
            preparedStatement.setString(1, subject.getEnName());
            preparedStatement.setString(2, subject.getRuName());
            preparedStatement.setString(3, subject.getDeName());

            preparedStatement.execute();

            // get the id database assigned and set it into the subject instance.
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subject.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to create subject. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Subject subject) throws DaoException {

        if (subject.getId() == null) {
            throw new DaoException("You cannot update subjects with no id!");
        }

        String query =
                "UPDATE subject SET " +
                        "en_name = ?, ru_name = ?, de_name = ?" +
                        "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, subject.getEnName());
            preparedStatement.setString(2, subject.getRuName());
            preparedStatement.setString(3, subject.getDeName());

            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to update subject. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Subject subject) throws DaoException {

        if (subject.getId() == null) {
            throw new DaoException("You cannot delete subjects with no id!");
        }

        String query = "DELETE FROM subject WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, subject.getId());
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to delete subject. " + e.getMessage(), e);
        }
    }

    @Override
    public Subject getById(long id) throws DaoException {

        Subject subject;

        String query = "SELECT * FROM subject WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            subject = extractSubjectFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve subject. " + e.getMessage(), e);
        }

        return subject;
    }

    @Override
    public Subject[] getAll() throws DaoException {

        Set<Subject> subjects = new HashSet<>();
        String query = "SELECT * FROM subject";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Subject subject = new Subject();
                    subject.setId(resultSet.getLong("id"));
                    subject.setRuName(resultSet.getString("ru_name"));
                    subject.setEnName(resultSet.getString("en_name"));
                    subject.setDeName(resultSet.getString("de_name"));
                    subjects.add(subject);
                }
            }

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve subject. " + e.getMessage(), e);
        }

        Subject[] subjectArray = new Subject[subjects.size()];
        return subjects.toArray(subjectArray);
    }

    @Override
    public void createAll(Collection<Subject> subjects) throws DaoException {

        if (subjects == null) {
            throw new DaoException("You cannot create null subjects!");
        }

        // To ensure data consistency and order we put values into an array
        Subject[] subjectArray = new Subject[subjects.size()];
        subjectArray = subjects.toArray(subjectArray);

        String query =
                "INSERT INTO subject " +
                        "(en_name, ru_name, de_name) " +
                        "VALUES (?,?,?)";

        // This operation is safe. No SQL injection is possible as no String is passed - only numeric values.
        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)){

            // adding all subjects into Batch List for later batch execution
            for (Subject subject : subjectArray) {
                preparedStatement.setString(1, subject.getEnName());
                preparedStatement.setString(2, subject.getRuName());
                preparedStatement.setString(3, subject.getDeName());
                preparedStatement.addBatch();
            }

            // executing batch
            preparedStatement.executeBatch();

            // assigning auto-generated ids
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                for (Subject subject : subjectArray) {
                    generatedKeys.next(); // should always return true
                    subject.setId(generatedKeys.getLong(1));
                }
            }


        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to create subjects. " + e.getMessage(), e);
        }

    }

    private Subject extractSubjectFromResultSet(PreparedStatement preparedStatement) throws SQLException {
        Subject subject = null;
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                subject = new Subject();
                subject.setId(resultSet.getLong("id"));
                subject.setEnName(resultSet.getString("en_name"));
                subject.setDeName(resultSet.getString("de_name"));
                subject.setRuName(resultSet.getString("ru_name"));
            }
        }
        return subject;
    }
}
