package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.autocommittable;

import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional.TransactionalJdbcApplicationRepository;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.DaoSupplier;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.EmptyDaoOperator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.executeOperationThenCommitAndCloseConnection;
import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.getFromSourceThenCloseConnection;

/**
 * <p>This class wraps bare TransactionalJdbcApplicationRepository methods.
 *
 * <p>It ensures that after each method call, changes are committed (where appropriate) and connection is closed.
 * Thus releasing the ApplicationRepository invoker from this concern.
 *
 * @author Daniil Yurov
 */
public class AutoCommittalJdbcApplicationRepository extends TransactionalJdbcApplicationRepository {

    public AutoCommittalJdbcApplicationRepository(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public void create(Application application) throws DaoException {
        EmptyDaoOperator operation = () -> super.create(application);
        String commitFailureMessage = "Failed to create a application.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void update(Application application) throws DaoException {
        EmptyDaoOperator operation = () -> super.update(application);
        String commitFailureMessage = "Failed to update the application.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void delete(Application application) throws DaoException {
        EmptyDaoOperator operation = () -> super.delete(application);
        String commitFailureMessage = "Failed to delete the application.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public Application getById(long id) throws DaoException {
        DaoSupplier<Application> source = () -> super.getById(id);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Application[] getAll() throws DaoException {
        DaoSupplier<Application[]> source = super::getAll;
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Application[] getAllOf(User user) throws DaoException {
        DaoSupplier<Application[]> source = () -> super.getAllOf(user);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Application getLastOf(Faculty faculty, Long userId) throws DaoException {
        DaoSupplier<Application> source = () -> super.getLastOf(faculty, userId);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Application[] getAllOf(Faculty faculty, Application.Status status) throws DaoException {
        DaoSupplier<Application[]> source = () -> super.getAllOf(faculty, status);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Application[] getAllOf(Faculty faculty, Date studyStartDate) throws DaoException {
        DaoSupplier<Application[]> source = () -> super.getAllOf(faculty, studyStartDate);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public long countAllOf(Faculty faculty, Application.Status status) throws DaoException {
        DaoSupplier<Long> source = () -> super.countAllOf(faculty, status);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public long countAllOf(Long userId, Application.Status status) throws DaoException {
        DaoSupplier<Long> source = () -> super.countAllOf(userId, status);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public void updateAll(Set<Long> applicationIds, Application.Status status) throws DaoException {
        EmptyDaoOperator operation = () -> super.updateAll(applicationIds, status);
        String commitFailureMessage = "Failed to update applications.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }
}
