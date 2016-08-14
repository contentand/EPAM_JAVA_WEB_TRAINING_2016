package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class ValidatorFactoryTest  extends Mockito {

    ValidatorFactory factory;
    Request request;

    @Before
    public void setup() {
        ServicesFactory servicesFactory = mock(ServicesFactory.class);
        OutputToolFactory outputToolFactory = mock(OutputToolFactory.class);
        factory = new ValidatorFactory(servicesFactory, outputToolFactory);
        request = mock(Request.class);
        when(outputToolFactory.getInstance(eq(request))).thenReturn(mock(OutputTool.class));
    }

    @Test
    public void testGetApplicationValidator() throws Exception {
        // execute
        ApplicationValidator validator = factory.getApplicationValidator(request);
        // verify
        assertNotNull(validator);
    }

    @Test
    public void testGetUserValidator() throws Exception {
        // execute
        UserValidator validator = factory.getUserValidator(request);
        // verify
        assertNotNull(validator);
    }

    @Test
    public void testGetResultValidator() throws Exception {
        // execute
        ResultValidator validator = factory.getResultValidator(request);
        // verify
        assertNotNull(validator);
    }

    @Test
    public void testGetFacultyValidator() throws Exception {
        // execute
        FacultyValidator validator = factory.getFacultyValidator(request);
        // verify
        assertNotNull(validator);
    }
}