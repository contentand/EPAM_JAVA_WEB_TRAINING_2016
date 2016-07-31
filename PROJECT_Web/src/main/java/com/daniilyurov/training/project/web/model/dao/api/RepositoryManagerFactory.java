package com.daniilyurov.training.project.web.model.dao.api;


/**
 * <p>Provides methods to obtain repository managers:</p>
 * <ul>
 *      <li>AutoCommittalRepositoryManager;</li>
 *      <li>TransactionalRepositoryManager.</li>
 * </ul>
 * @author Daniil Yurov
 */

public interface RepositoryManagerFactory {

    /**
     * Returns a new instance of AutoCommittalRepositoryManager that can provide concrete repositories
     * that has methods that will auto-commit changes upon each call.
     *
     * @return AutoCommittalRepositoryManager
     */
    AutoCommittalRepositoryManager getAutoCommittalRepositoryManager();

    /**
     * Returns a new instance of TransactionalRepositoryManager and stating the transaction.
     * For all operations invoked via that instance to be committed, one should explicitly call
     * its commit method thus finishing the transaction.
     * Alternatively, one can call its rollback method to finish the transaction by discard all changes
     * invoked by it.
     *
     * @return TransactionalRepositoryManager
     * @throws DaoException if it failed to start a transaction.
     */
    TransactionalRepositoryManager getTransactionalRepositoryManager() throws DaoException;

}
