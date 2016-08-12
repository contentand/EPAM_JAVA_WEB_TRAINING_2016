package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.CSRF_TOKEN;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.CSRF_TOKEN_SERVER;
import static org.junit.Assert.*;

@RunWith(Theories.class)
public class CsrfTokenVerificationFilterTest extends AbstractFilter {

    @Test // should perform all actions before doFilter
    public void always_performsBeforeDoFilterCall() throws Exception {

        // setup
        when(request.getMethod()).thenReturn("GET");

        // execute
        new CsrfTokenVerificationFilter().doFilter(request, response, chain);

        // verify
        InOrder inOrder = inOrder(request, response, session, chain);
        inOrder.verify(chain).doFilter(eq(request), eq(response));
        inOrder.verifyNoMoreInteractions();
    }

    @Test // should not verify if request method is GET
    public void methodGet_doesNothing() throws Exception {

        // setup
        when(request.getMethod()).thenReturn("GET");

        // execute
        new CsrfTokenVerificationFilter().doFilter(request, response, chain);

        // verify
        verify(request, times(1)).getMethod();
        verify(chain, times(1)).doFilter(eq(request), eq(response));
        verifyNoMoreInteractions(request, response, chain);
    }

    @DataPoints(value="nonGetMethods")
    public static String[] nonGetMethods() {
        return new String[]{"POST", "PUT", "DELETE"};
    }

    @Theory // should verify if request method is POST/PUT/DELETE
    public void notGetMethod_verifiesToken(@FromDataPoints("nonGetMethods") String method) throws Exception {

        // setup
        when(request.getMethod()).thenReturn(method);
        when(request.getParameter(eq(CSRF_TOKEN))).thenReturn("2.222");
        when(session.getAttribute(eq(CSRF_TOKEN_SERVER))).thenReturn("2.222");

        // execute
        new CsrfTokenVerificationFilter().doFilter(request, response, chain);

        // verify
        verify(request, times(1)).getParameter(eq(CSRF_TOKEN));
        verify(session, times(1)).getAttribute(eq(CSRF_TOKEN_SERVER));
    }

    @DataPoints(value = "wrongTokens")
    public static String[] wrongTokens() {
        return new String[] {null, "3.123"};
    }

    @Theory // should send error if tokens do not match
    public void tokensDoNotMatch_sendsError403(@FromDataPoints("wrongTokens") String wrongToken) throws Exception {

        // setup
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter(eq(CSRF_TOKEN))).thenReturn(wrongToken);
        when(session.getAttribute(eq(CSRF_TOKEN_SERVER))).thenReturn("2.222");

        // execute
        new CsrfTokenVerificationFilter().doFilter(request, response, chain);

        // verify
        verify(response, times(1)).sendError(eq(HttpServletResponse.SC_FORBIDDEN));
    }
}