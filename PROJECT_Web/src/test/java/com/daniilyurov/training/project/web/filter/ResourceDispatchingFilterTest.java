package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

import javax.servlet.RequestDispatcher;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_CURRENT_PAGE_LINK;
import static org.junit.Assert.*;

public class ResourceDispatchingFilterTest extends AbstractFilter{

    @Test // should not do anything if request is for static resource that starts with "/resource"
    public void requestsStaticResources_doesNothing() throws Exception {

        // setup
        when(request.getServletPath()).thenReturn("/resource/style/awesome.css");

        // execute
        new ResourceDispatchingFilter().doFilter(request, response, chain);

        // verify
        InOrder inOrder = inOrder(request, chain);
        inOrder.verify(request, times(1)).getServletPath();
        inOrder.verify(chain, times(1)).doFilter(eq(request), eq(response));
        inOrder.verifyNoMoreInteractions();
    }

    @Test // should append "/app" to the request url and forward if the request is for dynamic resource
    public void requestDynamicResources_forwardToNewUrlLeadingToFrontController() throws Exception {

        // setup
        ArgumentCaptor<String> newPath = ArgumentCaptor.forClass(String.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getServletPath()).thenReturn("/source");
        when(request.getMethod()).thenReturn("POST");
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);

        // execute
        new ResourceDispatchingFilter().doFilter(request, response, chain);

        // verify
        verify(request, times(1)).getRequestDispatcher(newPath.capture());
        verify(dispatcher, times(1)).forward(eq(request), eq(response));
        assertEquals("/app/source", newPath.getValue());
    }

    @Test // urls for get requests to dynamic resources should be saved in session
    public void getRequestToDynamicRequest_savesRequestUrlInSession() throws Exception {

        // setup
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(request.getServletPath()).thenReturn("/source");
        when(request.getRequestURI()).thenReturn("/APPLICATION/source");
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);

        // execute
        new ResourceDispatchingFilter().doFilter(request, response, chain);

        // verify
        verify(session, times(1)).setAttribute(ATTRIBUTE_CURRENT_PAGE_LINK, "/APPLICATION/source");
    }
}