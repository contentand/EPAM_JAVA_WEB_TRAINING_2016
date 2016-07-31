package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Service;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.utility.ContextAttributes;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;


public class ResultsService implements Service {

    protected RepositoryTool repository;

    public ResultsService(Request request) {
        this.repository = new RepositoryTool((RepositoryManagerFactory) request.getContextAttribute(ContextAttributes.REPOSITORY_MANAGER_FACTORY));
    }

    public ResultsService(RepositoryTool repositoryTool) {
        this.repository = repositoryTool;
    }

    public Result[] getAllOf(User user) throws DaoException {
        return repository.getAutoCommittalResultRepository().getAllOf(user);
    }

    public Set<Subject> getSubjectsOf(User user) throws DaoException {

        Result[] applicantResults = repository.getAutoCommittalResultRepository().getAllOf(user);
        return Stream.of(applicantResults).map(Result::getSubject).collect(toSet());

    }
}
