package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

/**
 * ResultsService encapsulates all utility methods
 * that work with repository required by business logic.
 */
public class ResultsService {

    protected RepositoryTool repository;

    public ResultsService(RepositoryTool repositoryTool) {
        this.repository = repositoryTool;
    }

    /**
     * Returns all results of particular user.
     * @param user to search for
     * @return result array of user
     * @throws DaoException if repository layer fails.
     */
    public Result[] getAllOf(User user) throws DaoException {
        return repository.getAutoCommittalResultRepository().getAllOf(user);
    }

    /**
     * Returns all subjects of particular user.
     * @param user to search for
     * @return subject array of user
     * @throws DaoException if repository layer fails.
     */
    public Set<Subject> getSubjectsOf(User user) throws DaoException {
        Result[] applicantResults = repository.getAutoCommittalResultRepository().getAllOf(user);
        return Stream.of(applicantResults).map(Result::getSubject).collect(toSet());
    }
}
