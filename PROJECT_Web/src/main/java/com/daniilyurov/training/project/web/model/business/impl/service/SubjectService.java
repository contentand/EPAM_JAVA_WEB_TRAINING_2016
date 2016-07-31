package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Service;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.utility.ContextAttributes;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.daniilyurov.training.project.web.utility.RequestParameters.PREFIX_PARAMETER_SUBJECT_ID;

public class SubjectService implements Service {

    protected RepositoryTool repository;

    public SubjectService(Request request) {
        this.repository = new RepositoryTool((RepositoryManagerFactory) request.getContextAttribute(ContextAttributes.REPOSITORY_MANAGER_FACTORY));

    }

    public SubjectService(RepositoryTool repositoryTool) {
        this.repository = repositoryTool;
    }

    public List<Map.Entry<String, String>> getMapWithSubjectIdsAndTheirLocalNames(LocalizationTool localization)
            throws DaoException {
        Subject[] subjects = repository.getAutoCommittalSubjectRepository().getAll();
        List<Map.Entry<String, String>> subjectList = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectList.add(new AbstractMap.SimpleEntry<>(PREFIX_PARAMETER_SUBJECT_ID + subject.getId(),
                    localization.getLocalName(subject)));
        }
        return subjectList;
    }
}
