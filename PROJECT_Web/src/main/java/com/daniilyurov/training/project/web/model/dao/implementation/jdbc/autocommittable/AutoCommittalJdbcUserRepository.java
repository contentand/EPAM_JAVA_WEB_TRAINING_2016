package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.autocommittable;

import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional.TransactionalJdbcUserRepository;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.DaoSupplier;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.EmptyDaoOperator;

import java.sql.Connection;
import java.sql.SQLException;

import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.executeOperationThenCommitAndCloseConnection;
import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.getFromSourceThenCloseConnection;

/**
 * <p>This class wraps bare TransactionalJdbcUserRepository methods.
 *
 * <p>It ensures that after each method call, changes are committed (where appropriate) and connection is closed.
 * Thus releasing the UserRepository invoker from this concern.
 *
 * @author Daniil Yurov
 */

public class AutoCommittalJdbcUserRepository extends TransactionalJdbcUserRepository {

    public AutoCommittalJdbcUserRepository(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public void create(User user) throws DaoException {
        EmptyDaoOperator operation = () -> super.create(user);
        String commitFailureMessage = "Failed to create a user.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void update(User user) throws DaoException {
        EmptyDaoOperator operation = () -> super.update(user);
        String commitFailureMessage = "Failed to update the user.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void delete(User user) throws DaoException {
        EmptyDaoOperator operation = () -> super.delete(user);
        String commitFailureMessage = "Failed to delete the user.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public User getById(long id) throws DaoException {
        DaoSupplier<User> source = () -> super.getById(id);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public User[] getAll() throws DaoException {
        DaoSupplier<User[]> source = super::getAll;
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) throws DaoException {
        DaoSupplier<User> source = () -> super.getUserByLoginAndPassword(login, password);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public boolean doesSuchLoginExist(String login) throws DaoException {
        DaoSupplier<Boolean> source = () -> super.doesSuchLoginExist(login);
        return getFromSourceThenCloseConnection(source, connection);
    }
}
