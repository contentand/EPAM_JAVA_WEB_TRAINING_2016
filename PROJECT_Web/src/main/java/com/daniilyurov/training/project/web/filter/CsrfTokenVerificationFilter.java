package com.daniilyurov.training.project.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

/**
 * This filter ensures that for all POST/PUT/DELETE requests,
 * CSRF tokens contained in cookies and in request parameter match.
 * If they do not match, a redirect to Unauthorized error page is made.
 *
 * @author Daniil Yurov
 */
public class CsrfTokenVerificationFilter extends HttpFilter {

    static Logger logger = Logger.getLogger(CsrfTokenVerificationFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        if (!request.getMethod().equals("GET")) { // only POST/PUT/DELETE requests are verified

            logger.debug("Verifying CSRF-TOKEN...");

            String expectedToken = (String) request.getSession().getAttribute(CSRF_TOKEN_SERVER);

            String actualToken = Optional.ofNullable(request.getParameter(CSRF_TOKEN))
                    .orElse("NO_CSRF_PARAMETER");

            if (!expectedToken.equals(actualToken)) {
                logger.debug("CSRF-TOKEN is invalid!");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            logger.debug("CSRF-TOKEN is valid.");
        }

        chain.doFilter(request, response);

    }
}
