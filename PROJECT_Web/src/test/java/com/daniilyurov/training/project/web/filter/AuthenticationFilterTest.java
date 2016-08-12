package com.daniilyurov.training.project.web.filter;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Role;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.utility.ContextAttributes.*;
import static org.junit.Assert.*;

public class AuthenticationFilterTest extends Mockito {

    HttpServletRequest request;
    HttpServletResponse response;
    HttpSession session;
    FilterChain chain;
    ServletContext context;
    Localizer localizer;

    @Before
    public void setup() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        session = mock(HttpSession.class);
        context = mock(ServletContext.class);
        localizer = mock(Localizer.class);

        when(request.getLocale()).thenReturn(Locale.ENGLISH);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(context);
        when(context.getAttribute(LOCALIZER)).thenReturn(localizer);
    }

    @Test // should touch request only before the doFilter
    public void always_touchesRequestAfterDoFilterCall() throws Exception {

        // setup
        when(session.getAttribute(eq(ROLE))).thenReturn(Role.GUEST);

        // execute
        new AuthenticationFilter().doFilter(request, response, chain);

        // verify
        InOrder inOrder = inOrder(request, session, chain);
        inOrder.verify(request).getSession();
        inOrder.verify(session).getAttribute(eq(ROLE));
        inOrder.verify(chain).doFilter(request, response);
    }

    @Test // should assign guest role and reset userId if none is assigned
    public void roleUndefined_assignsGuestRole() throws Exception {

        // setup
        ArgumentCaptor<Role> role = ArgumentCaptor.forClass(Role.class);
        ArgumentCaptor<Long> userId = ArgumentCaptor.forClass(Long.class);

        // execute
        new AuthenticationFilter().doFilter(request, response, chain);

        // verify
        verify(session).setAttribute(eq(USER_ID), userId.capture());
        verify(session).setAttribute(eq(ROLE), role.capture());
        assertEquals(Role.GUEST, role.getValue());
        assertNull(userId.getValue());
    }

    @Test // should set locale and bundle if role is unidentified
    public void roleUndefined_assignsAdjustedBrowserLocaleAndCorrespondingResourceBundle() throws Exception {

        // setup
        ResourceBundle resourceBundle = mock(ResourceBundle.class);
        ArgumentCaptor<Locale> locale = ArgumentCaptor.forClass(Locale.class);
        ArgumentCaptor<ResourceBundle> bundle = ArgumentCaptor.forClass(ResourceBundle.class);
        when(localizer.adjustLocale(eq(Locale.ENGLISH))).thenReturn(Locale.ENGLISH);
        when(localizer.getBundle(eq(Locale.ENGLISH))).thenReturn(resourceBundle);

        // execute
        new AuthenticationFilter().doFilter(request, response, chain);

        // verify
        verify(session).setAttribute(eq(LOCALE), locale.capture());
        verify(session).setAttribute(eq(BUNDLE), bundle.capture());
        assertEquals(Locale.ENGLISH, locale.getValue());
        assertEquals(resourceBundle, bundle.getValue());
    }

    @Test // should not change anything if role is defined
    public void roleDefined_doesNothing() throws Exception {

        // setup
        when(session.getAttribute(eq(ROLE))).thenReturn(Role.GUEST);

        // execute
        new AuthenticationFilter().doFilter(request, response, chain);

        // verify
        verify(request).getSession();
        verify(session).getAttribute(eq(ROLE));
        verify(chain).doFilter(request, response);
        verifyNoMoreInteractions(request, response, session, context, localizer, chain);
    }
}