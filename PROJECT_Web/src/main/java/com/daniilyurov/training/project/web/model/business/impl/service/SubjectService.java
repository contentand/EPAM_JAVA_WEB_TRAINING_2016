package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.model.business.impl.tool.Localize;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;

import java.util.*;

import static com.daniilyurov.training.project.web.utility.RequestParameters.PREFIX_PARAMETER_SUBJECT_ID;

public class SubjectService {

    protected RepositoryTool repository;

    public SubjectService(RepositoryTool repositoryTool) {
        this.repository = repositoryTool;
    }

    public List<Map.Entry<String, String>> getMapWithSubjectIdsAndTheirLocalNames(Localize localization)
            throws DaoException {
        Subject[] subjects = repository.getAutoCommittalSubjectRepository().getAll();
        List<Map.Entry<String, String>> subjectList = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectList.add(new AbstractMap.SimpleEntry<>(PREFIX_PARAMETER_SUBJECT_ID + subject.getId(),
                    localization.getLocalName(subject)));
        }
        return subjectList;
    }

    public Subject[] getAll() throws DaoException {
        return repository.getAutoCommittalSubjectRepository().getAll();
    }
}
