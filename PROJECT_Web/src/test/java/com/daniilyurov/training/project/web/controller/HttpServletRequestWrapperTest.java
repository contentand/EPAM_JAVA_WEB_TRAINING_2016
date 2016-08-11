package com.daniilyurov.training.project.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HttpServletRequestWrapperTest extends Mockito {

    private MockHttpServletRequest request;

    @Before
    public void setup() {
        this.request = new MockHttpServletRequest();
    }

    @Test // should get attribute from context container
    public void testGetContextAttribute() throws Exception {

        // setup
        String key = "key";
        String attribute = "attribute";
        request.getServletContext().setAttribute(key, attribute);

        // execute
        String result = (String) new HttpServletRequestWrapper(request).getContextAttribute(key);

        // verify
        assertEquals(attribute, result);
    }

    @Test // should get all parameter values
    public void testGetParameterValues() throws Exception {

        // setup
        String parameter = "param";
        String[] values = {"one", "two", "three"};
        request.addParameter(parameter, values);

        // execute
        String[] result = new HttpServletRequestWrapper(request).getParameterValues(parameter);

        // verify
        assertTrue(Arrays.equals(values, result));
    }

    @Test // should set session attribute
    public void testSetSessionAttribute() throws Exception {

        // execute
        String key = "key";
        String value = "value";
        new HttpServletRequestWrapper(request).setSessionAttribute(key, value);

        // verify
        String result = (String) request.getSession().getAttribute(key);
        assertNotNull(result);
        assertEquals(value, result);
    }

    @Test // should retrieve attribute value from session container
    public void testGetSessionAttribute() throws Exception {

        // setup
        String key = "key";
        String value = "value";
        request.getSession().setAttribute(key, value);

        // execute
        String result = (String) new HttpServletRequestWrapper(request).getSessionAttribute(key);

        // verify
        assertEquals(value, result);
    }

    @Test // should return path info from request
    public void testGetUrlPath() throws Exception {

        // setup
        String path = "/path";
        request.setPathInfo(path);

        // execute
        String result = new HttpServletRequestWrapper(request).getUrlPath();

        // verify
        assertEquals(path, result);
    }
}