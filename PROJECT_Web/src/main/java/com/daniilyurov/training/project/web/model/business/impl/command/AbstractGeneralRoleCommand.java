package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.api.Role;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import org.apache.log4j.Logger;

import static com.daniilyurov.training.project.web.i18n.Value.ERR_SYSTEM_ERROR;
import static com.daniilyurov.training.project.web.model.business.impl.Key.REDIRECT_TO_WHERE_HE_CAME_FROM;

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
public abstract class AbstractGeneralRoleCommand implements Command {

    static Logger logger = Logger.getLogger(AbstractGeneralRoleCommand.class);

    protected OutputToolFactory outputToolFactory;
    protected ValidatorFactory validatorFactory;

    @Provided
    public void setOutputToolFactory(OutputToolFactory outputToolFactory, ValidatorFactory validatorFactory) {
        this.outputToolFactory = outputToolFactory;
        this.validatorFactory = validatorFactory;
    }

    @Override
    public final String execute(Request request) {
        try {
            String intent;
            Role role = getRole(request);
            switch (role) {
                case ADMINISTRATOR:
                    return executeAsAdministrator(request);
                case APPLICANT:
                    return executeAsApplicant(request);
                case GUEST:
                    return executeAsGuest(request);
            }

            // It should not get here.
            throw new IllegalStateException("Unidentifiable role : " + role);
        } catch (Exception e) {
            logger.error("Error processing command. Business logic or Repository failed.", e);
            OutputTool output = outputToolFactory.getInstance(request);
            output.setErrorMsg(ERR_SYSTEM_ERROR);
            return REDIRECT_TO_WHERE_HE_CAME_FROM; // TODO check if correct redirect
        }

    }

    /**
     * Abstract method for descendants to define logic if the user is
     * executing the command as a Guest.
     *
     * @return String representing further action.
     * @throws Exception if system fails.
     */
    protected abstract String executeAsGuest(Request request) throws Exception;

    /**
     * Abstract method for descendants to define logic if the user is
     * executing the command as an Applicant/Student.
     *
     * @return String representing further action.
     * @throws Exception if system fails.
     */
    protected abstract String executeAsApplicant(Request request) throws Exception;

    /**
     * Abstract method for descendants to define logic if the user is
     * executing the command as an Administrator.
     *
     * @return String representing further action.
     * @throws Exception if system fails.
     */
    protected abstract String executeAsAdministrator(Request request) throws Exception;

    // Helper methods below

    protected Role getRole(Request request) {
        return validatorFactory.getUserValidator(request).getCurrentUserRole();
    }
}
