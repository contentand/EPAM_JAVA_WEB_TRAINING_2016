package com.daniilyurov.training.project.web.controller;

import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import static com.daniilyurov.training.project.web.utility.ContextAttributes.ACTION_COMMAND_FACTORY;
import static com.daniilyurov.training.project.web.utility.ContextAttributes.JSP_MAPPING;
import static com.daniilyurov.training.project.web.utility.ContextAttributes.URL_MAPPING;
import static com.daniilyurov.training.project.web.utility.RequestParameters.PARAMETER_AFTER_PROCESS_DESTINATION_PATH;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class FrontControllerTest {

    private static Properties urlMapping;
    private static Properties jspMapping;
    private static CommandFactory cmdFactory;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private String expectedForward;
    private String expectedRedirect;

    @BeforeClass
    public static void setup() {
        // Pseudo url-mapping.properties
        urlMapping = new Properties(){{
            setProperty("[GET]/", "REJECT");
            setProperty("[GET]/login", "LOGIN");
            setProperty("[GET]/404", "404");
            setProperty("[GET]/505", "505");
            setProperty("[GET]/info", "INFO");
            setProperty("[GET]/user/{id}", "APPLICANT");
            setProperty("[POST]/lang", "LANG");
        }};

        // Pseudo jsp-mapping.properties
        jspMapping = new Properties(){{
            setProperty("LOGIN","login.jsp");
            setProperty("INFO", "info.jsp");
            setProperty("APPLICANT", "user.jsp");
        }};

        // Pseudo ActionCommandFactory implementation and concrete commands
        cmdFactory = (intent) -> {
            if (intent == null) return req -> "404";
            switch (intent) {
                case "REJECT": return req -> "LOGIN"; // command redirects
                case "INFO": return req -> "INFO"; // command lets the request through
                case "APPLICANT": return req -> "APPLICANT"; // command lets the request through
                case "LANG": return req -> null; // command doesn't care
                default: return req -> "505";
            }
        };
    }

    public FrontControllerTest(String method, String url, String afterProcessDestinationPath,
                               String expectedRedirect, String expectedForward) {

        MockServletContext servletContext = new MockServletContext();
        servletContext.setAttribute(URL_MAPPING, urlMapping); // placing pseudo url-mapping into pseudo context
        servletContext.setAttribute(JSP_MAPPING, jspMapping); // placing pseudo jsp-mapping into pseudo context
        servletContext.setAttribute(ACTION_COMMAND_FACTORY, cmdFactory); // placing pseudo business implementation

        request = new MockHttpServletRequest(servletContext);
        request.setMethod(method);
        request.setPathInfo(url);
        request.setParameter(PARAMETER_AFTER_PROCESS_DESTINATION_PATH, afterProcessDestinationPath);

        this.response = new MockHttpServletResponse();
        this.expectedForward = expectedForward;
        this.expectedRedirect = expectedRedirect;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                //----------CONDITIONS--------------|------EXPECTED_RESULTS---------|
                //Method      Path         APDP*        RedirectTo      ForwardTo        Comments
                {"GET",       "/info",     null,        null,           "info.jsp"},  // command forwards to jsp
                {"GET",       "/",         null,        "/login",       null},        // command decides to redirect
                {"GET",       "/sdfsfsfs", null,        "/404",         null},        // unmapped link
                {"POST",      "/lang",     "/info",     "/info",        null},        // command lets redirect to APDP
                {"GET",       "/user/32",  null,        null,           "user.jsp"}   // numeric ids handled properly

                // * APDP = afterProcessDestinationPath - is an obligatory parameter sent with POST/PUT/DELETE requests.
        });
    }

    @Test
    public void test() throws Exception {
        new FrontController().service(request, response);
        assertEquals("forward match", expectedForward, response.getForwardedUrl());
        assertEquals("redirect match", expectedRedirect, response.getRedirectedUrl());
    }

}