package com.daniilyurov.training.project.web.filter;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Role;
import com.daniilyurov.training.project.web.utility.ContextAttributes;
import com.daniilyurov.training.project.web.utility.SessionAttributes;
import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

/**
 * This filter ensures that all requests that reach the FrontController
 * do have the defined role. If this is the very first request, it is assigned the role of GUEST.
 *
 * @author Daniil Yurov
 */
public class AuthenticationFilter extends HttpFilter {

    static Logger logger = Logger.getLogger(AuthenticationFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.debug("Checking role.");
        HttpSession session = request.getSession();

        if (session.getAttribute(ROLE) == null) {
            synchronized (session) {

                if (session.getAttribute(ROLE) == null) {

                    logger.debug("Role is absent. So assigning Guest role along with browser-default Locale");

                    session.setAttribute(USER_ID, null);
                    setDefaultLocalization(request);
                    session.setAttribute(ROLE, Role.GUEST);

                }
            }
        }

        logger.debug("Role is identifiable");

        chain.doFilter(request, response);
    }

    // Private helper methods are listed below

    private void setDefaultLocalization(HttpServletRequest request) {
        Localizer localizer = (Localizer) request.getServletContext()
                .getAttribute(ContextAttributes.LOCALIZER);
        Locale locale = localizer.adjustLocale(request.getLocale());
        ResourceBundle bundle = localizer.getBundle(locale);
        request.getSession().setAttribute(SessionAttributes.BUNDLE, bundle);
        request.getSession().setAttribute(SessionAttributes.LOCALE, locale);
    }
}
