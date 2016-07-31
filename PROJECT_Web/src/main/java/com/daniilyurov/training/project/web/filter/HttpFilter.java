package com.daniilyurov.training.project.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This abstract class provides basic implementation for Filter methods.
 * It also does the routine casts to HttpServletRequest and HttpServletResponse.
 * This makes it more adjusted for working with HTTP protocol.
 * The sole goal is to reduce the amount of unnecessary and repetitive code
 * in real implementations.
 *
 * Implementors only need to override the new overloaded doFilter method.
 *
 * @author Daniil Yurov
 */
public abstract class HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request;
        HttpServletResponse response;

        if (!(req instanceof HttpServletRequest &&
                resp instanceof HttpServletResponse)) {
            throw new ServletException("non-HTTP request or response");
        }

        request = (HttpServletRequest) req;
        response = (HttpServletResponse) resp;

        doFilter(request, response, chain);

    }

    /**
     *  Abstract method to be overridden by descendants.
     *  It already provides instances of HttpServletRequest and HttpServletResponse.
     */
    protected abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    @Override
    public void destroy() {}
}
