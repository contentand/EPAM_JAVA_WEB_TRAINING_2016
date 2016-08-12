package com.daniilyurov.training.project.web.controller;

import com.daniilyurov.training.project.web.model.business.api.Request;

import javax.servlet.http.HttpServletRequest;

/**
 * This class provides basic request wrapping for HttpServletRequest.
 */
public class HttpServletRequestWrapper implements Request {

    private HttpServletRequest request;

    public HttpServletRequestWrapper(HttpServletRequest request) throws NullPointerException {
        if (request == null) throw new NullPointerException();
        this.request = request;
    }

    @Override
    public String[] getParameterValues(String key) {
        return request.getParameterValues(key);
    }

    @Override
    public void setSessionAttribute(String key, Object value) {
        request.getSession().setAttribute(key, value);
    }

    @Override
    public Object getSessionAttribute(String key) {
        return request.getSession().getAttribute(key);
    }

    @Override
    public String getUrlPath() {
        return request.getPathInfo();
    }
}
