package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.*;


public class HttpFilterTest extends Mockito {

    HttpFilter filter;
    HttpServletRequest request;
    HttpServletResponse response;
    FilterChain chain;

    @Before
    public void setup() throws Exception {
        this.request = mock(HttpServletRequest.class);
        this.response = mock(HttpServletResponse.class);
        this.chain = mock(FilterChain.class);
        this.filter = spy(HttpFilter.class);
    }


    @Test // no action when the Filter initializes
    public void always_noActionUponInit() throws Exception {

        // setup
        FilterConfig config = mock(FilterConfig.class);

        // execute
        filter.init(config);

        // verify
        verifyZeroInteractions(config);
    }

    @Test
    public void always_castsAndCallsAbstractMethod() throws Exception {

        // execute
        filter.doFilter(request, response, chain);

        // verify
        verify(filter, times(1)).doFilter(eq(request), eq(response), eq(chain));
    }

    @Test(expected = ServletException.class)
    public void castFails_throwsServletException() throws Exception {

        // setup
        ServletRequest wrongRequest = mock(ServletRequest.class);
        ServletResponse wrongResponse = mock(ServletResponse.class);

        // execute
        filter.doFilter(wrongRequest, wrongResponse, chain);
    }
}