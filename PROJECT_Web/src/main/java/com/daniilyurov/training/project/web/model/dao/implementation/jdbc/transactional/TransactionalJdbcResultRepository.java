package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.ResultRepository;

import java.sql.*;
import java.util.*;

/**
 * <p>This class provides core implementation for ResultRepository interface using JDBC and SQL.
 *
 * <p><strong>WARNING:</strong> The implementation intentionally lacks commit/rollback logic,
 * it also does not handle connection closure.
 * <p>These concerns are left for the Connection instance provider.
 * Alternatively, one can extend this class and wrap all methods adding
 * appropriate commit/rollback/close logic.
 *
 * @author Daniil Yurov
 */
public class TransactionalJdbcResultRepository implements ResultRepository {

    /**
     * Instance of connection that method implementation will use.
     * The connection should have disabled auto-commit.
     */
    protected Connection connection;

    /**
     * Creates an instance of ResultRepository using the connection passed.
     * It disables auto-commit for the connection making the commits and closure
     * to be the concern of the invoker.
     *
     * @param connection Connection to be used to execute methods.
     * @throws SQLException if it fails to setup connection.
     */
    public TransactionalJdbcResultRepository(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.connection = connection;
    }

    @Override
    public void create(Result result) throws DaoException {

        if (result == null || result.getId() != null) {
            throw new DaoException("You cannot result instances with particular id!");
        }

        String query =
                "INSERT INTO result " +
                        "(applicant_id, subject_id, result)" +
                        "VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            // id is set by the database automatically. We are retrieving the assigned id below
            preparedStatement.setLong(1, result.getApplicant().getId());
            preparedStatement.setLong(2, result.getSubject().getId());
            preparedStatement.setDouble(3, result.getResult());

            preparedStatement.execute();

            // get the id database assigned and set it into the result instance.
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    result.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to create result instance. " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Result result) throws DaoException {

        if (result == null || result.getId() == null) {
            throw new DaoException("You cannot update results with no id!");
        }

        String query =
                "UPDATE result SET " +
                        "result = ? " +
                        "WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setDouble(1, result.getResult());
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to update result. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(Result result) throws DaoException {

        if (result.getId() == null) {
            throw new DaoException("You cannot delete results with no id!");
        }

        String query = "DELETE FROM result WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, result.getId());
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to delete the result. " + e.getMessage(), e);
        }
    }

    @Override
    public Result getById(long id) throws DaoException {

        Result result;

        String query = "SELECT " +
                "result.id, result.applicant_id, result.subject_id, subject.en_name, subject.ru_name, " +
                "subject.de_name, user.average_school_result, user.login, user.password, user.email, user.authority," +
                "user.locale, user.latin_first_name , user.latin_last_name, user.cyrillic_first_name," +
                "user.cyrillic_last_name, result.result " +
                "FROM result JOIN user ON user.id = result.applicant_id JOIN subject ON subject.id = result.subject_id " +
                "WHERE result.id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            result = extractResultFromResultSet(preparedStatement).stream().findFirst().orElse(null);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve result instance. " + e.getMessage(), e);
        }

        return result;
    }

    @Override
    public Result[] getAll() throws DaoException {

        Set<Result> results;

        String query = "SELECT " +
                "result.id, result.applicant_id, result.subject_id, subject.en_name, subject.ru_name, " +
                "subject.de_name, user.average_school_result, user.login, user.password, user.email, user.authority," +
                "user.locale, user.latin_first_name , user.latin_last_name, user.cyrillic_first_name," +
                "user.cyrillic_last_name, result.result " +
                "FROM result JOIN user ON user.id = result.applicant_id JOIN subject ON subject.id = result.subject_id";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.execute();

            results = extractResultFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve result instances. " + e.getMessage(), e);
        }

        Result[] resultArray = new Result[results.size()];
        return results.toArray(resultArray);
    }

    @Override
    public Result[] getAllOf(User user) throws DaoException {

        if (user == null || user.getId() == null) {
            throw new DaoException("You cannot get results of unknown user!");
        }

        Set<Result> results;

        String query = "SELECT " +
                "result.id, result.applicant_id, result.subject_id, subject.en_name, subject.ru_name, " +
                "subject.de_name, user.average_school_result, user.login, user.password, user.email, user.authority," +
                "user.locale, user.latin_first_name , user.latin_last_name, user.cyrillic_first_name," +
                "user.cyrillic_last_name, result.result " +
                "FROM result JOIN user ON user.id = result.applicant_id JOIN subject ON subject.id = result.subject_id " +
                "WHERE result.applicant_id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, user.getId());

            results = extractResultFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve user. " + e.getMessage(), e);
        }

        Result[] resultArray = new Result[results.size()];
        return results.toArray(resultArray);
    }

    @Override
    public void createAll(Collection<Result> results) throws DaoException {

        if (results == null) {
            throw new DaoException("You cannot create null results!");
        }

        // To ensure data consistency and order we put values into an array
        Result[] resultArray = new Result[results.size()];
        resultArray = results.toArray(resultArray);

        String query =
                "INSERT INTO result " +
                        "(applicant_id, subject_id, result)" +
                        "VALUES (?,?,?)";

        // This operation is safe. No SQL injection is possible as no String is passed - only numeric values.
        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)){

            // adding all results into Batch List for later batch execution
            for (Result result : resultArray) {
                preparedStatement.setLong(1, result.getApplicant().getId());
                preparedStatement.setLong(2, result.getSubject().getId());
                preparedStatement.setDouble(3, result.getResult());
                preparedStatement.addBatch();
            }

            // executing batch
            preparedStatement.executeBatch();

            // assigning auto-generated ids
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                for (Result result : resultArray) {
                    generatedKeys.next(); // should always return true
                    result.setId(generatedKeys.getLong(1));
                }
            }


        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to create results. " + e.getMessage(), e);
        }

    }

    private Set<Result> extractResultFromResultSet(PreparedStatement preparedStatement) throws SQLException {

        Set<Result> results = new HashSet<>();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Result result = new Result();
                result.setId(resultSet.getLong("result.id"));
                result.setResult(resultSet.getDouble("result"));

                User applicant = new User();
                applicant.setId(resultSet.getLong("applicant_id"));
                applicant.setLogin(resultSet.getString("login"));
                applicant.setPassword(resultSet.getString("password"));
                applicant.setEmail(resultSet.getString("email"));
                applicant.setRole(resultSet.getString("authority"));
                applicant.setLocale(new Locale(resultSet.getString("locale")));
                applicant.setLatinFirstName(resultSet.getString("latin_first_name"));
                applicant.setLatinLastName(resultSet.getString("latin_last_name"));
                applicant.setCyrillicFirstName(resultSet.getString("cyrillic_first_name"));
                applicant.setCyrillicLastName(resultSet.getString("cyrillic_last_name"));
                applicant.setAverageSchoolResult(resultSet.getDouble("average_school_result"));
                result.setApplicant(applicant);

                Subject subject = new Subject();
                subject.setId(resultSet.getLong("subject_id"));
                subject.setRuName(resultSet.getString("ru_name"));
                subject.setDeName(resultSet.getString("de_name"));
                subject.setEnName(resultSet.getString("en_name"));
                result.setSubject(subject);

                results.add(result);
            }
        }
        return results;
    }

}
