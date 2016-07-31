package com.daniilyurov.training.project.web.model.dao.api.repository;

import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;


/**
 * Defines the concrete FacultyRepository interface.
 * It declares additional unique methods not defined in the GenericRepository super-interface.
 *
 * @author Daniil Yurov
 */

public interface FacultyRepository extends GenericRepository<Faculty> {

    /**
     * Checks if there is a Faculty with such English name.
     *
     * @param enName English name to be checked
     * @return true if there is already a Faculty with such English name or false otherwise.
     * @throws DaoException if it fails during lookup process
     */
    boolean doesSuchEnNameExist(String enName) throws DaoException;;

    /**
     * Checks if there is a Faculty with such German name.
     *
     * @param deName German name to be checked
     * @return true if there is already a Faculty with such German name or false otherwise.
     * @throws DaoException if it fails during lookup process
     */
    boolean doesSuchDeNameExist(String deName) throws DaoException;;

    /**
     * Checks if there is a Faculty with such Russian name.
     *
     * @param ruName Russian name to be checked
     * @return true if there is already a Faculty with such Russian name or false otherwise.
     * @throws DaoException if it fails during lookup process
     */
    boolean doesSuchRuNameExist(String ruName) throws DaoException;;
}
