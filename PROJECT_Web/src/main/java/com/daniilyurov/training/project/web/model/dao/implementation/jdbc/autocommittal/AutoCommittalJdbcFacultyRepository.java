package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.autocommittal;

import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.transactional.TransactionalJdbcFacultyRepository;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.DaoSupplier;
import com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.EmptyDaoOperator;

import java.sql.Connection;
import java.sql.SQLException;

import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.executeOperationThenCommitAndCloseConnection;
import static com.daniilyurov.training.project.web.model.dao.implementation.jdbc.util.CommitAndCloseUtil.getFromSourceThenCloseConnection;


public class AutoCommittalJdbcFacultyRepository extends TransactionalJdbcFacultyRepository {

    public AutoCommittalJdbcFacultyRepository(Connection connection) throws SQLException {
        super(connection);
    }

    @Override
    public void create(Faculty faculty) throws DaoException {
        EmptyDaoOperator operation = () -> super.create(faculty);
        String commitFailureMessage = "Failed to create faculty";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void update(Faculty faculty) throws DaoException {
        EmptyDaoOperator operation = () -> super.update(faculty);
        String commitFailureMessage = "Failed to update faculty";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public void delete(Faculty faculty) throws DaoException {
        EmptyDaoOperator operation = () -> super.delete(faculty);
        String commitFailureMessage = "Failed to delete faculty";
        executeOperationThenCommitAndCloseConnection(operation, connection, commitFailureMessage);
    }

    @Override
    public Faculty getById(long id) throws DaoException {
        DaoSupplier<Faculty> source = () -> super.getById(id);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public Faculty[] getAll() throws DaoException {
        DaoSupplier<Faculty[]> source = super::getAll;
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public boolean doesSuchEnNameExist(String enName) throws DaoException {
        DaoSupplier<Boolean> source = () -> super.doesSuchEnNameExist(enName);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public boolean doesSuchDeNameExist(String deName) throws DaoException {
        DaoSupplier<Boolean> source = () -> super.doesSuchDeNameExist(deName);
        return getFromSourceThenCloseConnection(source, connection);
    }

    @Override
    public boolean doesSuchRuNameExist(String ruName) throws DaoException {
        DaoSupplier<Boolean> source = () -> super.doesSuchRuNameExist(ruName);
        return getFromSourceThenCloseConnection(source, connection);
    }
}
