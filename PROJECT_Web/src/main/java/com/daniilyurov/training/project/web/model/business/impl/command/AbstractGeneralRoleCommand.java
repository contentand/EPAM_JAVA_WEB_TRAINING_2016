package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.api.Role;

/**
 * Encapsulates role definition steps.
 * Defines three abstract methods for descendants to override:
 *
 * 1. executeAsAdministrator() -- instructions if the invoker is an administrator
 * 2. executeAsApplicant() -- instructions if the invoker is an ordinary authorized user
 * 3. executeAsGuest() -- instructions if the invoker is a guest
 *
 * @author Daniil Yurov
 */
public abstract class AbstractGeneralRoleCommand extends AbstractSafeCommand {
    @Override
    public final String safeExecute(Provider provider) throws Exception {
        String intent;
        Role role = getRole(provider);
        switch (role) {
            case ADMINISTRATOR:
                return executeAsAdministrator(provider);
            case APPLICANT:
                return executeAsApplicant(provider);
            case GUEST:
                return executeAsGuest(provider);
        }

        // It should not get here.
        throw new IllegalStateException("Unidentifiable role : " + role);
    }

    /**
     * Abstract method for descendants to define logic if the user is
     * executing the command as a Guest.
     *
     * @return String representing further action.
     * @throws Exception if system fails.
     */
    protected abstract String executeAsGuest(Provider provider) throws Exception;

    /**
     * Abstract method for descendants to define logic if the user is
     * executing the command as an Applicant/Student.
     *
     * @return String representing further action.
     * @throws Exception if system fails.
     */
    protected abstract String executeAsApplicant(Provider provider) throws Exception;

    /**
     * Abstract method for descendants to define logic if the user is
     * executing the command as an Administrator.
     *
     * @return String representing further action.
     * @throws Exception if system fails.
     */
    protected abstract String executeAsAdministrator(Provider provider) throws Exception;

    // Helper methods below

    protected Role getRole(Provider provider) {
        return provider.getSessionManager().getRole();
    }
}
