package com.daniilyurov.training.project.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;

/**
 * Filter removes all session attributes except the core ones
 * after the jsp page has been rendered.
 *
 * Note: In web.xml, make sure the filter reacts on FORWARD only
 * and on forwards to jsp pages only.
 *
 * @author Daniil Yurov
 */
public class AttributeKillerFilter extends HttpFilter {

    static Logger logger = Logger.getLogger(AttributeKillerFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        chain.doFilter(request, response);

        logger.debug("Removing non-core session attributes for request: |"
                + request.getMethod() + "| " + request.getRequestURI());

        if (isGet(request)) {
            HttpSession session = request.getSession();
            Enumeration<String> attributes = session.getAttributeNames();
            cleanUp(attributes, session);
        }

        logger.debug("Done.");
    }

    // Private helper methods are listed below

    private boolean isGet(HttpServletRequest request) {
        return request.getMethod().equals("GET");
    }

    private void cleanUp(Enumeration<String> attributeNames, HttpSession session) {
        while (attributeNames.hasMoreElements()) {
            String attribute = attributeNames.nextElement();
            if (!isCore(attribute)) {
                logger.debug("- Removing session attribute: " + attribute);
                session.removeAttribute(attribute);
            }
        }
    }

    private boolean isCore(String attribute) {
        return CORE_SESSION_ATTRIBUTES.contains(attribute);
    }
}