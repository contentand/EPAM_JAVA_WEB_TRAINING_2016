package com.daniilyurov.training.project.web.model.dao.api.repository;


import com.daniilyurov.training.project.web.model.dao.api.DaoException;

/**
 * <p>Abstract Repository interface that contains methods
 * shared by all concrete repository interfaces such as UserRepository, ... // TODO finish list here
 * The purpose is to provide basic Create Read Update Delete (CRUD) operations.</p>
 *
 * <p>Repository (also known as "Gateway" or "Domain Access Object")
 * is a special class that allows us to synchronize instance state between
 * data source and our application data.</p>
 *
 * <p><strong>Note:</strong> this interface is intentionally package-protected:</p>
 * <ul>
 *      <li>It SHOULD NOT be implemented directly!
 *         Only through descendant interfaces.</li>
 *      <li>It SHOULD NOT be visible for clients!</li>
 *</ul>
 *
 * @author Daniil Yurov
 */

interface GenericRepository<T> {

    /**
     * Persists the instance in the data source.
     *
     * @param instance instance to be persisted
     * @throws DaoException if it fails during creation process
     */
    void create(T instance) throws DaoException;

    /**
     * Changes the data in the data source so that it
     * corresponds to the information set into the instance.
     *
     * @param instance instance to be updated
     * @throws DaoException if no such instance exists in the data source
     * ot if it fails during the update process
     */
    void update(T instance) throws DaoException;

    /**
     * Deletes the instance from the data source.
     *
     * @param instance instance to be deleted
     * @throws DaoException if it fails during deletion process
     */
    void delete(T instance) throws DaoException;

    /**
     * Returns the instance that has specified id from the data source or null if no instance with such id found.
     *
     * @param id id to be found
     * @return the instance with the specified id or null if such instance does not exist
     * @throws DaoException if it fails during instance retrieval
     */
    T getById(long id) throws DaoException;

    /**
     * Returns all instances of type in the data source.
     *
     * @return the instance with the specified id or null if such instance does not exist
     * @throws DaoException if it fails during instance retrieval
     */
    T[] getAll() throws DaoException;

}
