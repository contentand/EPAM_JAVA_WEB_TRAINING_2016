package com.daniilyurov.training.project.web.model.dao.api;

/**
 * <p>Any class implementing this interface should ensure Repositories returned have auto-committing enabled.</p>
 *
 * <p>Example. Assuming that:</p>
 *
 * <ul>
 * <li> <em>autoCommittalRepositoryFactory</em> is an instance of AutoCommittalRepositoryManager implementation
 * <li> <em>user</em> is an instance of User to be persisted.
 * </ul>
 *
 * <p>Then the code below will persist the user (no need to worry about commits and rollbacks):</p>
 * <pre>
 *      UserRepository userRepository = autoCommittalRepositoryManager.getUserRepository();
 *      userRepository.create(user);
 * </pre>
 *
 * @author Daniil Yurov
 */

public interface AutoCommittalRepositoryManager extends GenericRepositoryManager {
}
