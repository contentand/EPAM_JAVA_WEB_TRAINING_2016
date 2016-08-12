package com.daniilyurov.training.project.web.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Tests that the XSS filter works properly.
 * Should replace <,> with &lt; and &gt;
 * And then continue the chain.
 */
public class XssProtectionFilterTest extends AbstractFilter {

    @Test // should replace <,> with &lt; and &gt;
    public void always_replacesXmlCharactersWithEscapes() throws Exception {

        // setup
        Map<String, String[]> maliciousParameters = new HashMap<>();
        maliciousParameters.put("param1", new String[]{"valid123", "<script>ouch!</script>"});
        maliciousParameters.put("param2", new String[]{"hello"});

        Map<String, String[]> expectedEscapedParams = new HashMap<>();
        expectedEscapedParams.put("param1", new String[]{"valid123", "&lt;script&gt;ouch!&lt;/script&gt;"});
        expectedEscapedParams.put("param2", new String[]{"hello"});

        when(request.getParameterMap()).thenReturn(maliciousParameters);

        // execution
        new XssProtectionFilter().doFilter(request, response, chain);

        // verification
        expectedEscapedParams.keySet().forEach(key -> {
            shouldBeEqualValues(expectedEscapedParams, maliciousParameters, key);
        });
    }

    @Test
    public void always_callsFilterChainToContinue() throws Exception {
        new XssProtectionFilter().doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(eq(request), eq(response));
        verifyNoMoreInteractions(chain);
    }

    @Test
    public void always_doesNotTouchHttpServletResponse() throws Exception {
        new XssProtectionFilter().doFilter(request, response, chain);
        verifyZeroInteractions(response);
    }

    // Private helper methods are listed below

    private void shouldBeEqualValues(Map<String, String[]> expectedEscapedParams,
                                     Map<String, String[]> processedMaliciousParameters,
                                     String key) {

        String[] expected = expectedEscapedParams.get(key);
        String[] actual = processedMaliciousParameters.get(key);
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }
}