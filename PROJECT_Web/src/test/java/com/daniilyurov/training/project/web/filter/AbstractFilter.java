package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * An abstraction to prevent code repetition.
 */
public class AbstractFilter extends Mockito {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    FilterChain chain;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        chain = mock(FilterChain.class);

        when(request.getSession()).thenReturn(session);
    }
}
