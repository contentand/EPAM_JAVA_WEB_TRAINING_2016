package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;

public class CharacterEncodingFilterTest extends AbstractFilter {

    @Test // should touch request and response only before the doFilter
    public void always_touchesRequestAndResponseBeforeDoFilter() throws Exception {

        // execute
        new CharacterEncodingFilter().doFilter(request, response, chain);

        // verify
        InOrder inOrder = inOrder(response, request, chain);
        inOrder.verify(chain, times(1)).doFilter(eq(request), eq(response));
        inOrder.verifyNoMoreInteractions();
    }

    @Test // should change encoding for both request and response to UTF-8
    public void always_changesEncodingToUtf8() throws Exception {

        // setup
        ArgumentCaptor<String> requestEncoding = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> responseEncoding = ArgumentCaptor.forClass(String.class);

        // execute
        new CharacterEncodingFilter().doFilter(request, response, chain);

        // verify
        verify(request, times(1)).setCharacterEncoding(requestEncoding.capture());
        verify(response, times(1)).setCharacterEncoding(responseEncoding.capture());
        assertEquals("UTF-8", requestEncoding.getValue());
        assertEquals("UTF-8", responseEncoding.getValue());
    }
}