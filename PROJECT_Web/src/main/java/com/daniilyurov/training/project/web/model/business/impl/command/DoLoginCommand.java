package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.tool.*;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;
import org.apache.log4j.Logger;

import static com.daniilyurov.training.project.web.i18n.Value.WELCOME;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Key.REDIRECT_TO_WHERE_HE_CAME_FROM;
import static com.daniilyurov.training.project.web.i18n.Value.ERR_SYSTEM_ERROR;

/**
 * This command performs authentication of the user.
 * It sets persisted language preference.
 */
public class DoLoginCommand implements Command {

    static Logger logger = Logger.getLogger(DoLoginCommand.class);
    private ValidatorFactory validatorFactory;
    private OutputToolFactory outputToolFactory;

    @Provided
    public void setDependencies(ValidatorFactory validatorFactory,
                                OutputToolFactory outputToolFactory) {
        this.validatorFactory = validatorFactory;
        this.outputToolFactory = outputToolFactory;
    }

    @Override
    public String execute(Request request) {

        try {
            // setup dependencies
            OutputTool output = outputToolFactory.getInstance(request);
            UserValidator validator = validatorFactory.getUserValidator(request);

            // getting authorized user.
            User user = validator.parseAuthenticatedUser();

            // setting up the user in session
            output.setRole(user.getRole());
            output.setUserId(user.getId());
            output.setLocale(user.getLocale());

            // welcome user
            String userName = output.getLocalFirstName(user);
            output.setMsg(WELCOME, userName);

            return GET_MAIN_PAGE;

        }
        catch (ValidationException e) {
                return REDIRECT_TO_WHERE_HE_CAME_FROM;
        }
        catch (Exception e) {
            logger.error("Error processing command. Business logic or Repository failed.", e);
            OutputTool output = outputToolFactory.getInstance(request);
            output.setErrorMsg(ERR_SYSTEM_ERROR);
            return GET_MAIN_PAGE;
        }
    }
}
