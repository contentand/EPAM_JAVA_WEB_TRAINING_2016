package com.daniilyurov.training.project.web.model.dao.api.repository;

import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.util.Collection;

/**
 * Defines the concrete SubjectRepository interface.
 * It declares additional unique methods not defined in the GenericRepository super-interface.
 *
 * @author Daniil Yurov
 */

public interface SubjectRepository extends GenericRepository<Subject> {


    /**
     * Persists all instances of Subject passed.
     *
     * @param newSubjects subjects to persist
     * @throws DaoException if it fails during creation process.
     */
    void createAll(Collection<Subject> newSubjects) throws DaoException;
}
