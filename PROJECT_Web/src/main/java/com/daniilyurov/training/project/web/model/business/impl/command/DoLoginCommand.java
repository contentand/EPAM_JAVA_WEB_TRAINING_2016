package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.tool.*;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.business.impl.validator.UserValidator;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import static com.daniilyurov.training.project.web.i18n.Value.WELCOME;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.REDIRECT_TO_WHERE_HE_CAME_FROM;

public class DoLoginCommand extends AbstractSafeCommand {
    @Override
    public String safeExecute(Provider provider) throws Exception {

        // setup dependencies
        OutputTool output = provider.getOutputTool();
        SessionManager manager = provider.getSessionManager();
        LocalizationTool localization = provider.getLocalizationTool();
        UserValidator validator = provider.getUserValidator();

        try {

            // getting authorized user.
            User user = validator.parseAuthenticatedUser();

            // setting up the user in session
            manager.setRole(user.getRole());
            manager.setUserId(user.getId());
            manager.setLocale(user.getLocale());

            // welcome user
            String userName = localization.getLocalFirstName(user);
            output.setMsg(WELCOME, userName);

            return GET_MAIN_PAGE;

        } catch (ValidationException e) {
            return REDIRECT_TO_WHERE_HE_CAME_FROM;
        }

    }
}
