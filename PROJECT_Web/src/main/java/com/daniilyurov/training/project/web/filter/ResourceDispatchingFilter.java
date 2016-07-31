package com.daniilyurov.training.project.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

/**
 * This filter lets requests for static resources (css, js, etc) through.
 * It forwards all other requests to the FrontController.
 *
 * @author Daniil Yurov
 */
public class ResourceDispatchingFilter extends HttpFilter {

    static Logger logger = Logger.getLogger(ResourceDispatchingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {


        if (!request.getServletPath().startsWith("/resource")) {

            if (request.getMethod().equals("GET")) {
                request.getSession().setAttribute(ATTRIBUTE_CURRENT_PAGE_LINK, request.getRequestURI());
            }

            logger.debug("Request for " + request.getRequestURI() + " [it is not request for static content!]");
            logger.debug("... so forwarding to /app");
            request.getRequestDispatcher("/app" + request.getServletPath()).forward(request, response);
            return;
        }

        logger.debug("Request for static resource received " + request.getRequestURI());

        chain.doFilter(request, response);
    }
}
