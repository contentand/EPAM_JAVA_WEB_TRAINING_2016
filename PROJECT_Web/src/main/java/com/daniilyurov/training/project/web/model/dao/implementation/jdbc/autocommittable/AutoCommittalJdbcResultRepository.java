package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.autocommittable;

import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional.TransactionalJdbcResultRepository;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.DaoSupplier;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.EmptyDaoOperator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.executeOperationThenCommitAndCloseConnection;
import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.getFromSourceThenCloseConnection;

/**
 * <p>This class wraps bare TransactionalJdbcResultRepository methods.
 *
 * <p>It ensures that after each method call, changes are committed (where appropriate) and connection is closed.
 * Thus releasing the ResultRepository invoker from this concern.
 *
 * @author Daniil Yurov
 */

public class AutoCommittalJdbcResultRepository extends TransactionalJdbcResultRepository {

    public AutoCommittalJdbcResultRepository(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public void create(Result result) throws DaoException {
        EmptyDaoOperator operation = () -> super.create(result);
        String commitFailureMessage = "Failed to create a result.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void update(Result result) throws DaoException {
        EmptyDaoOperator operation = () -> super.update(result);
        String commitFailureMessage = "Failed to update the result.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void delete(Result result) throws DaoException {
        EmptyDaoOperator operation = () -> super.delete(result);
        String commitFailureMessage = "Failed to delete the result.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public Result getById(long id) throws DaoException {
        DaoSupplier<Result> source = () -> super.getById(id);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Result[] getAll() throws DaoException {
        DaoSupplier<Result[]> source = super::getAll;
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Result[] getAllOf(User user) throws DaoException {
        DaoSupplier<Result[]> source = () -> super.getAllOf(user);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public void createAll(Collection<Result> results) throws DaoException {
        EmptyDaoOperator operation = () -> super.createAll(results);
        String commitFailureMessage = "Failed to create the results.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }
}
