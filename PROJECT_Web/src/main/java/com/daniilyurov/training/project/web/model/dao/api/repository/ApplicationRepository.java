package com.daniilyurov.training.project.web.model.dao.api.repository;

import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;

import java.sql.Date;
import java.util.Set;

/**
 * Defines the concrete ApplicationRepository interface.
 * It declares additional unique methods not defined in the GenericRepository super-interface.
 *
 * @author Daniil Yurov
 */

public interface ApplicationRepository extends GenericRepository<Application> {

    /**
     * Gets all applications of the user passed.
     *
     * @param user to search
     * @throws DaoException if it fails during lookup process
     */
    Application[] getAllOf(User user) throws DaoException;

    /**
     * Gets the last application for particular faculty of particular user.
     *
     * @param faculty to search
     * @param userId to search
     * @throws DaoException if it fails during lookup process
     */
    Application getLastOf(Faculty faculty, Long userId) throws DaoException;

    /**
     * Gets all applications for particular faculty with particular status.
     *
     * @param faculty to search
     * @param status to search
     * @throws DaoException if it fails during lookup process
     */
    Application[] getAllOf(Faculty faculty, Application.Status status) throws DaoException;

    /**
     * Gets all applications for particular faculty with particular studyStartDate.
     *
     * @param faculty to search
     * @param studyStartDate to search
     * @throws DaoException if it fails during lookup process
     */
    Application[] getAllOf (Faculty faculty, Date studyStartDate) throws DaoException;

    /**
     * Counts all applications for particular faculty with particular status.
     *
     * @param faculty to search
     * @param status to search
     * @throws DaoException if it fails during lookup process
     */
    long countAllOf(Faculty faculty, Application.Status status) throws DaoException;

    /**
     * Counts all applications of particular user with particular status.
     *
     * @param userId to search
     * @param status to search
     * @throws DaoException if it fails during lookup process
     */
    long countAllOf(Long userId, Application.Status status) throws DaoException;

    /**
     * Updates status of all applications with id passed in set. (performed in batch)
     *
     * @param applicationIds indicating the applications to update
     * @param status new value for all applications listed
     * @throws DaoException
     */
    void updateAll(Set<Long> applicationIds, Application.Status status) throws DaoException;


}
