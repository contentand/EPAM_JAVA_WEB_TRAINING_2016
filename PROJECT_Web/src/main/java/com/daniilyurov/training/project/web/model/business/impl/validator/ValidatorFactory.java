package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.tool.InputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

public class ValidatorFactory {

    private ServicesFactory servicesFactory;
    private OutputToolFactory outputToolFactory;

    public ValidatorFactory(ServicesFactory servicesFactory, OutputToolFactory outputToolFactory) {
        this.servicesFactory = servicesFactory;
        this.outputToolFactory = outputToolFactory;
    }

    public ApplicationValidator getApplicationValidator(Request request) {
        return (ApplicationValidator) getValidator(ApplicationValidator::new, request);
    }

    public UserValidator getUserValidator(Request request) {
        return (UserValidator) getValidator(UserValidator::new, request);
    }

    public ResultValidator getResultValidator(Request request) {
        return (ResultValidator) getValidator(ResultValidator::new, request);
    }

    public FacultyValidator getFacultyValidator(Request request) {
        return (FacultyValidator) getValidator(FacultyValidator::new, request);
    }

    private Object getValidator(TripleSupplier<Object, InputTool, OutputTool, ServicesFactory> f, Request request) {
        InputTool inputTool = new InputTool(request);
        OutputTool outputTool = outputToolFactory.getInstance(request);
        return f.create(inputTool, outputTool, servicesFactory);
    }


    @FunctionalInterface
    interface TripleSupplier<R, A1, A2, A3> {
        R create(A1 a1, A2 a2, A3 a3);
    }


}
