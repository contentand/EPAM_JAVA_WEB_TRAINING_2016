package com.daniilyurov.training.project.web.filter;

import com.daniilyurov.training.project.web.utility.SessionAttributes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import static org.junit.Assert.*;

public class AttributeKillerFilterTest extends Mockito {

    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain chain;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
    }

    @Test // should touch request only after the doFilter
    public void touchesRequestAfterDoFilter() throws Exception {

        // setup
        when(request.getMethod()).thenReturn("POST");

        // execute
        new AttributeKillerFilter().doFilter(request, response, chain);

        // verify
        InOrder inOrder = inOrder(request, chain);
        inOrder.verify(chain).doFilter(request, response);
        inOrder.verify(request).getMethod();
    }

    @Test // should not touch response
    public void doesNotTouchResponse() throws Exception {

        // setup
        when(request.getMethod()).thenReturn("POST");

        // execute
        new AttributeKillerFilter().doFilter(request, response, chain);

        // verify
        verifyZeroInteractions(response);
    }

    @Test // should remove only non core session attributes
    public void shouldTouchOnlyNonCoreAttributes() throws Exception {

        // setup
        String NON_CORE_ATTRIBUTE = "nonCore";
        HttpSession session = mock(HttpSession.class);
        when(request.getMethod()).thenReturn("GET");
        when(request.getSession()).thenReturn(session);
        Enumeration<String> keys = Collections.enumeration(new ArrayList<String>(){{
            add(NON_CORE_ATTRIBUTE); // not core
            add(SessionAttributes.USER_ID); // is core
        }});
        when(session.getAttributeNames()).thenReturn(keys);

        // execute
        new AttributeKillerFilter().doFilter(request, response, chain);

        // verify
        verify(session, times(1)).removeAttribute(eq(NON_CORE_ATTRIBUTE));
        verify(session, never()).removeAttribute(eq(SessionAttributes.USER_ID));
    }
}