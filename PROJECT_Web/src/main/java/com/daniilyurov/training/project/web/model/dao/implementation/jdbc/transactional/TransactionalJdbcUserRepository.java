package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional;

import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * <p>This class provides core implementation for UserRepository interface using JDBC and SQL.
 *
 * <p><strong>WARNING:</strong> The implementation intentionally lacks commit/rollback logic,
 * it also does not handle connection closure.
 * <p>These concerns are left for the Connection instance provider.
 * Alternatively, one can extend this class and wrap all methods adding
 * appropriate commit/rollback/close logic.
 *
 * @author Daniil Yurov
 */

public class TransactionalJdbcUserRepository implements UserRepository {

    /**
     * Instance of connection that method implementation will use.
     * The connection should have disabled auto-commit.
     */
    protected Connection connection;

    /**
     * Creates an instance of UserRepository using the connection passed.
     * It disables auto-commit for the connection making the commits and closure
     * to be the concern of the invoker.
     *
     * @param connection Connection to be used to execute methods.
     * @throws SQLException if it fails to setup connection.
     */
    public TransactionalJdbcUserRepository(Connection connection) throws SQLException {
        connection.setAutoCommit(false);
        this.connection = connection;
    }

    @Override
    public void create(User user) throws DaoException {

        if (user.getId() != null) {
            throw new DaoException("You cannot create users with particular id!");
        }

        String query =
                "INSERT INTO user " +

                        "(login, password, email, authority, locale, latin_first_name, latin_last_name, " +
                        "cyrillic_first_name, cyrillic_last_name, average_school_result)" +

                        "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query,
                    PreparedStatement.RETURN_GENERATED_KEYS))
        {
            // id is set by the database automatically. We are retrieving the assigned id below
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getLocale().getLanguage());
            preparedStatement.setString(6, user.getLatinFirstName());
            preparedStatement.setString(7, user.getLatinLastName());
            preparedStatement.setString(8, user.getCyrillicFirstName());
            preparedStatement.setString(9, user.getCyrillicLastName());
            preparedStatement.setDouble(10, user.getAverageSchoolResult());

            preparedStatement.execute();

            // get the id database assigned and set it into the user instance.
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to create user. " + e.getMessage(), e);
        }

    }

    @Override
    public void update(User user) throws DaoException {

        if (user.getId() == null) {
            throw new DaoException("You cannot update users with no id!");
        }

        String query =
                "UPDATE user SET " +

                        "login = ?, password = ?, email = ?, authority = ?, locale = ?, latin_first_name = ?," +
                        "latin_last_name = ?, cyrillic_first_name = ?, cyrillic_last_name = ?, " +
                        "average_school_result = ?" +

                        "WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getLocale().getLanguage());
            preparedStatement.setString(6, user.getLatinFirstName());
            preparedStatement.setString(7, user.getLatinLastName());
            preparedStatement.setString(8, user.getCyrillicFirstName());
            preparedStatement.setString(9, user.getCyrillicLastName());
            preparedStatement.setDouble(10, user.getAverageSchoolResult());
            preparedStatement.setLong(11, user.getId());

            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to update user. " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {

        if (user.getId() == null) {
            throw new DaoException("You cannot delete users with no id!");
        }

        String query = "DELETE FROM user WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.execute();

        } catch (SQLException | NullPointerException e) {
            throw new DaoException("Unable to delete user. " + e.getMessage(), e);
        }
    }

    @Override
    public User getById(long id) throws DaoException {

        User user;

        String query = "SELECT * FROM user WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            preparedStatement.setLong(1, id);
            user = extractUserFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve user. " + e.getMessage(), e);
        }

        return user;
    }

    @Override
    public User[] getAll() throws DaoException {

        Set<User> users = new HashSet<>();

        String query = "SELECT * FROM user";

        try(PreparedStatement preparedStatement = connection.prepareStatement(query))
        {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setLogin(resultSet.getString("page.commons.login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setLatinFirstName(resultSet.getString("latin_first_name"));
                    user.setLatinLastName(resultSet.getString("latin_last_name"));
                    user.setCyrillicFirstName(resultSet.getString("cyrillic_first_name"));
                    user.setCyrillicLastName(resultSet.getString("cyrillic_last_name"));
                    user.setRole(resultSet.getString("authority"));
                    user.setLocale(new Locale(resultSet.getString("locale")));
                    user.setAverageSchoolResult(resultSet.getDouble("average_school_result"));
                    users.add(user);
                }
            }

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve user. " + e.getMessage(), e);
        }

        User[] result = new User[users.size()];
        return users.toArray(result);
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) throws DaoException {

        User user;

        if (login == null || password == null) {
            throw new DaoException("You cannot get user without passing login and/or password!");
        }

        String query = "SELECT * FROM user WHERE login = ? AND password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            user = extractUserFromResultSet(preparedStatement);

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to retrieve user. " + e.getMessage(), e);
        }

        return user;
    }

    @Override
    public boolean doesSuchLoginExist(String login) throws DaoException {

        boolean exists = false;

        if (login == null) {
            throw new DaoException("You cannot get check without passing the login!");
        }

        String query = "SELECT login FROM user WHERE login = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, login);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    exists = true;
                }
            }

        } catch (SQLException | NullPointerException | IllegalArgumentException e) {
            throw new DaoException("Unable to check login. " + e.getMessage(), e);
        }

        return exists;
    }

    // Helper method that executes query of the PreparedStatement and retrieves User from the ResultSet.
    private User extractUserFromResultSet(PreparedStatement preparedStatement) throws SQLException {
        User user = null;
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setLatinFirstName(resultSet.getString("latin_first_name"));
                user.setLatinLastName(resultSet.getString("latin_last_name"));
                user.setCyrillicFirstName(resultSet.getString("cyrillic_first_name"));
                user.setCyrillicLastName(resultSet.getString("cyrillic_last_name"));
                user.setRole(resultSet.getString("authority"));
                user.setLocale(new Locale(resultSet.getString("locale")));
                user.setAverageSchoolResult(resultSet.getDouble("average_school_result"));
            }
        }
        return user;
    }

}
