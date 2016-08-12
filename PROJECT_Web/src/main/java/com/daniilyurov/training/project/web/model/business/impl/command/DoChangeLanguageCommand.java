package com.daniilyurov.training.project.web.model.business.impl.command;


import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import org.apache.log4j.Logger;

import static com.daniilyurov.training.project.web.model.business.impl.Key.REDIRECT_TO_WHERE_HE_CAME_FROM;
import static com.daniilyurov.training.project.web.i18n.Value.ERR_SYSTEM_ERROR;

import java.util.Locale;

public class DoChangeLanguageCommand implements Command {

    static Logger logger = Logger.getLogger(DoChangeLanguageCommand.class);
    private ServicesFactory servicesFactory;
    private ValidatorFactory validatorFactory;
    private OutputToolFactory outputToolFactory;

    @Provided
    public void setDependencies(ServicesFactory servicesFactory, ValidatorFactory validatorFactory,
                                OutputToolFactory outputToolFactory) {
        this.servicesFactory = servicesFactory;
        this.validatorFactory = validatorFactory;
        this.outputToolFactory = outputToolFactory;
    }


    @Override
    public String execute(Request request) {
        try {
            // setup dependencies
            UserValidator userValidator = validatorFactory.getUserValidator(request);
            UserService userService = servicesFactory.getUserService();
            OutputTool output = outputToolFactory.getInstance(request);

            // parse locale from parameter
            Locale locale = userValidator.parseValidLocale();

            // request management to change locale and get what it has been changed to
            Locale assignedLocale = output.setLocale(locale);

            // persist new language preferences if it is an authorized user.
            userValidator.getCurrentUser().ifPresent(user -> {
                userService.updateLocalePreferencesForUser(user, assignedLocale);
            });

            // finally
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        } catch (ValidationException e) {
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        } catch (Exception e) {
            logger.error("Error processing command. Business logic or Repository failed.", e);
            OutputTool output = outputToolFactory.getInstance(request);
            output.setErrorMsg(ERR_SYSTEM_ERROR);
            return REDIRECT_TO_WHERE_HE_CAME_FROM; // TODO check if correct redirect
        }


    }
}
