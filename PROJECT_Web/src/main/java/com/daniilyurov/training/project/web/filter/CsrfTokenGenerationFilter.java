package com.daniilyurov.training.project.web.filter;

import org.apache.log4j.Logger;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.CSRF_TOKEN;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.CSRF_TOKEN_SERVER;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter ensures Csrf token is generated and put into
 * a cookie and into session container for the jsp to include
 * it into forms.
 *
 * @author Daniil Yurov
 */
public class CsrfTokenGenerationFilter extends HttpFilter {

    static Logger logger = Logger.getLogger(CsrfTokenGenerationFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        logger.debug("Is session-scoped CSRF-TOKEN present in session.");
        if (request.getSession().getAttribute(CSRF_TOKEN_SERVER) == null) {
            logger.debug("CSRF-TOKEN is absent. Creating new one.");
            String token = String.valueOf(Math.random());
            request.getSession().setAttribute(CSRF_TOKEN_SERVER, token);
        }
        logger.debug("CSRF-TOKEN is present.");

        chain.doFilter(request, response);

    }
}
