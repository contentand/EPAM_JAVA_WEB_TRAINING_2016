package com.daniilyurov.training.project.web.model.business.impl.service;

import com.daniilyurov.training.project.web.i18n.Localize;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;

import java.util.*;

import static com.daniilyurov.training.project.web.utility.RequestParameters.PREFIX_PARAMETER_SUBJECT_ID;

/**
 * SubjectService encapsulates all utility methods
 * that work with repository required by business logic.
 */
public class SubjectService {

    protected RepositoryTool repository;

    public SubjectService(RepositoryTool repositoryTool) {
        this.repository = repositoryTool;
    }

    /**
     * Returns list of map entries: subject id with parameter prefix - local subject name
     * @param localization to localize subject names
     * @return list of subject id parameter names and local subject names
     * @throws DaoException if repository layer fails.
     */
    public List<Map.Entry<String, String>> getListOfSubjectIdsAndTheirLocalNames(Localize localization)
            throws DaoException {
        Subject[] subjects = repository.getAutoCommittalSubjectRepository().getAll();
        List<Map.Entry<String, String>> subjectList = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectList.add(new AbstractMap.SimpleEntry<>(PREFIX_PARAMETER_SUBJECT_ID + subject.getId(),
                    localization.getLocalName(subject)));
        }
        return subjectList;
    }

    /**
     * Returns an array of all subjects in repository.
     * @return all subjuects
     * @throws DaoException if repository layer fails.
     */
    public Subject[] getAll() throws DaoException {
        return repository.getAutoCommittalSubjectRepository().getAll();
    }
}
