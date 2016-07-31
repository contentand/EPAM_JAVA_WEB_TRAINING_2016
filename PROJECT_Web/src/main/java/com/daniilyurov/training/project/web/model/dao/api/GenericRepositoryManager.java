package com.daniilyurov.training.project.web.model.dao.api;


import com.daniilyurov.training.project.web.model.dao.api.repository.*;

/**
 * <p>Abstract interface to provide methods shared between all
 * concrete RepositoryManager interfaces such as:</p>
 * <ul>
 *      <li>AutoCommittalRepositoryManager;</li>
 *      <li>TransactionalRepositoryManager.</li>
 * </ul>
 *
 * <p>Motivation: to prevent code repetition.</p>
 *
 * <p><strong>Note:</strong> this interface is intentionally package-protected:</p>
 * <ul>
 *      <li>It <em>should not</em> be implemented directly!</li>
 *      <li>It <em>should not</em> be visible for clients!</li>
 * </ul>
 *
 * @author Daniil Yurov
 */

interface GenericRepositoryManager {

    /**
     * Returns concrete UserRepository.
     * @return concrete UserRepository.
     * @throws DaoException if UserRepository failed to instantiate.
     */
    UserRepository getUserRepository() throws DaoException;

    /**
     * Returns concrete ResultRepository.
     * @return concrete ResultRepository.
     * @throws DaoException if ResultRepository failed to instantiate.
     */
    ResultRepository getResultRepository() throws DaoException;

    /**
     * Returns concrete FacultyRepository.
     * @return concrete FacultyRepository.
     * @throws DaoException if FacultyRepository failed to instantiate.
     */
    FacultyRepository getFacultyRepository() throws DaoException;

    /**
     * Returns concrete ApplicationRepository.
     * @return concrete ApplicationRepository.
     * @throws DaoException if ApplicationRepository failed to instantiate.
     */
    ApplicationRepository getApplicationRepository() throws DaoException;

    /**
     * Returns concrete SubjectRepository.
     * @return concrete SubjectRepository.
     * @throws DaoException if SubjectRepository failed to instantiate.
     */
    SubjectRepository getSubjectRepository() throws DaoException;

}
