package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.autocommittable;

import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional.TransactionalJdbcSubjectRepository;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.DaoSupplier;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.EmptyDaoOperator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.executeOperationThenCommitAndCloseConnection;
import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.getFromSourceThenCloseConnection;

/**
 * <p>This class wraps bare TransactionalJdbcSubjectRepository methods.
 *
 * <p>It ensures that after each method call, changes are committed (where appropriate) and connection is closed.
 * Thus releasing the SubjectRepository invoker from this concern.
 *
 * @author Daniil Yurov
 */
public class AutoCommittalJdbcSubjectRepository extends TransactionalJdbcSubjectRepository {

    public AutoCommittalJdbcSubjectRepository(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public void create(Subject subject) throws DaoException {
        EmptyDaoOperator operation = () -> super.create(subject);
        String commitFailureMessage = "Failed to create a subject.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void update(Subject subject) throws DaoException {
        EmptyDaoOperator operation = () -> super.update(subject);
        String commitFailureMessage = "Failed to update the subject.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void delete(Subject subject) throws DaoException {
        EmptyDaoOperator operation = () -> super.delete(subject);
        String commitFailureMessage = "Failed to delete the subject.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public Subject getById(long id) throws DaoException {
        DaoSupplier<Subject> source = () -> super.getById(id);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Subject[] getAll() throws DaoException {
        DaoSupplier<Subject[]> source = super::getAll;
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public void createAll(Collection<Subject> subjects) throws DaoException {
        EmptyDaoOperator operation = () -> super.createAll(subjects);
        String commitFailureMessage = "Failed to create the subjects.";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

}
