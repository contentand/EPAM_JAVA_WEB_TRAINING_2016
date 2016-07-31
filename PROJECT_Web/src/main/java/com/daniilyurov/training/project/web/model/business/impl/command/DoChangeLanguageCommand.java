package com.daniilyurov.training.project.web.model.business.impl.command;


import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.service.UserService;
import com.daniilyurov.training.project.web.model.business.impl.tool.SessionManager;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;

import static com.daniilyurov.training.project.web.model.business.impl.Intent.REDIRECT_TO_WHERE_HE_CAME_FROM;

import java.util.Locale;

public class DoChangeLanguageCommand extends AbstractSafeCommand {

    @Override
    public String safeExecute(Provider provider) throws Exception {

        // setup dependencies
        UserValidator userValidator = provider.getUserValidator();
        UserService userService = provider.getUserService();
        SessionManager manager = provider.getSessionManager();

        // parse locale from parameter
        Locale locale = userValidator.parseValidLocale();

        // request management to change locale and get what it has been changed to
        Locale assignedLocale = manager.setLocale(locale);

        // persist new language preferences if it is an authorized user.
        manager.getUserId().ifPresent(id -> {
            userService.updateLocalePreferencesForUser(id, assignedLocale);
        });

        // finally
        return REDIRECT_TO_WHERE_HE_CAME_FROM;

    }
}
