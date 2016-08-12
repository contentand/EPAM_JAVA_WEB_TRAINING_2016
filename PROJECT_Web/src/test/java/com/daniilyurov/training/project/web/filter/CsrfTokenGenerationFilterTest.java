package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.CSRF_TOKEN_SERVER;
import static org.junit.Assert.*;

public class CsrfTokenGenerationFilterTest extends AbstractFilter {

    @Test // should perform all actions before doFilter
    public void always_performsBeforeDoFilter() throws Exception {

        // setup
        when(session.getAttribute(eq(CSRF_TOKEN_SERVER))).thenReturn(2.3333D);

        // execute
        new CsrfTokenGenerationFilter().doFilter(request, response, chain);

        // verify
        InOrder inOrder = inOrder(request, response, session, chain);
        inOrder.verify(chain).doFilter(eq(request), eq(response));
        inOrder.verifyNoMoreInteractions();
    }

    @Test // should not assign a token if one is already assigned
    public void tokenExists_doesNothing() throws Exception {

        // setup
        when(session.getAttribute(eq(CSRF_TOKEN_SERVER))).thenReturn(1.2322D);

        // execute
        new CsrfTokenGenerationFilter().doFilter(request, response, chain);

        // verify
        verify(session, times(1)).getAttribute(eq(CSRF_TOKEN_SERVER));
        verifyNoMoreInteractions(session);
    }

    @Test // should assign a token if one is absent
    public void tokenAbsent_generatesOne() throws Exception {

        // execute
        new CsrfTokenGenerationFilter().doFilter(request, response, chain);

        // verify
        verify(session, times(1)).setAttribute(eq(CSRF_TOKEN_SERVER), any(Double.class));
    }
}