package com.daniilyurov.training.project.web.filter;

import org.apache.log4j.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter ensures that request and response are
 * represented via UTF-8 character encoding.
 *
 * @author Daniil Yurov
 */
public class CharacterEncodingFilter extends HttpFilter {

    static Logger logger = Logger.getLogger(CharacterEncodingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        logger.debug("Adjusting encoding to UTF-8");

        chain.doFilter(request, response);
    }
}
