package com.daniilyurov.training.project.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This filter provides very simple XSS Attack protection.
 * It basically replaces all <, > characters in values passed as parameters by
 * their corresponding escape: &lt; and &gt;
 *
 * @author Daniil Yurov
 */
public class XssProtectionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        request.getParameterMap().values().forEach(values -> {
            for (int i = 0; i < values.length; i++) {
                values[i] = values[i].replaceAll("<", "&lt;");
                values[i] = values[i].replaceAll(">", "&gt;");
            }
        });

        chain.doFilter(request, response);
    }
}
