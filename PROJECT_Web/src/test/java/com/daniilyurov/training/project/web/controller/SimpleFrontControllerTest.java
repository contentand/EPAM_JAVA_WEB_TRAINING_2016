package com.daniilyurov.training.project.web.controller;

import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import com.daniilyurov.training.project.web.model.business.api.Role;
import com.daniilyurov.training.project.web.utility.SessionAttributes;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;

import java.util.Properties;

import static com.daniilyurov.training.project.web.utility.ContextAttributes.ACTION_COMMAND_FACTORY;
import static com.daniilyurov.training.project.web.utility.ContextAttributes.URL_MAPPING;
import static com.daniilyurov.training.project.web.utility.RequestParameters.PARAMETER_AFTER_PROCESS_DESTINATION_PATH;
import static org.junit.Assert.assertEquals;

public class SimpleFrontControllerTest {

    @Test
    public void testGetServletInfo() throws Exception {
        String result = new FrontController().getServletInfo();
        assertEquals("Front Controller, version: 1.0, author: Daniil Yurov (c) 2016", result);
    }

    @Test // FrontController.Dispatcher never persists parameters that have names that collide with attribute names
    public void coreAttributeNamesAsParameterName_ignoresSuchParameters() throws Exception {

        // setup
        Properties urlMapping = new Properties(){{
            setProperty("[POST]/lang", "LANG");
        }};

        CommandFactory cmdFactory = (intent) -> {
            if (intent == null) return req -> "404";
            if (intent.equals("LANG")) return req -> null; // command doesn't care
            return req -> "505";
        };

        MockServletContext servletContext = new MockServletContext();
        servletContext.setAttribute(URL_MAPPING, urlMapping); // placing pseudo url-mapping into pseudo context
        servletContext.setAttribute(ACTION_COMMAND_FACTORY, cmdFactory); // placing pseudo business implementation

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
        MockHttpSession session = new MockHttpSession();
        request.setSession(session);
        request.setMethod("POST");
        request.setPathInfo("/lang");
        request.setParameter(PARAMETER_AFTER_PROCESS_DESTINATION_PATH, "/..."); // exact destination does not matter

        request.addParameter("validParam", "validValue");
        request.addParameter(SessionAttributes.ROLE, "Administrator"); // override core attribute threat
        session.setAttribute(SessionAttributes.ROLE, Role.GUEST); // core attribute that should survive

        // execute
        new FrontController().service(request, response);

        // verify
        assertEquals("validValue", session.getAttribute("validParam"));
        assertEquals(Role.GUEST, session.getAttribute(SessionAttributes.ROLE)); // core attribute survives
    }
}
