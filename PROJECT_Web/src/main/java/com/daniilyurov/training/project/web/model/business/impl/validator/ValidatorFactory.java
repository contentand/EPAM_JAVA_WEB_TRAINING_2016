package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.tool.InputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;

/**
 * ValidatorFactory contains application scoped dependencies
 * required by concrete Validators.
 * It takes changeable part - request and creates an appropriate
 * instance of a Validator.
 *
 * @author Daniil Yurov
 */
public class ValidatorFactory {

    // unchangeable components required by all validators.
    private ServicesFactory servicesFactory;
    private OutputToolFactory outputToolFactory;

    public ValidatorFactory(ServicesFactory servicesFactory, OutputToolFactory outputToolFactory) {
        this.servicesFactory = servicesFactory;
        this.outputToolFactory = outputToolFactory;
    }

    /**
     * @param request containing request specific data.
     * @return ApplicationValidator able to validate the request data.
     */
    public ApplicationValidator getApplicationValidator(Request request) {
        return (ApplicationValidator) getValidator(ApplicationValidator::new, request);
    }

    /**
     * @param request containing request specific data.
     * @return UserValidator able to validate the request data.
     */
    public UserValidator getUserValidator(Request request) {
        return (UserValidator) getValidator(UserValidator::new, request);
    }

    /**
     * @param request containing request specific data.
     * @return ResultValidator able to validate the request data.
     */
    public ResultValidator getResultValidator(Request request) {
        return (ResultValidator) getValidator(ResultValidator::new, request);
    }

    /**
     * @param request containing request specific data.
     * @return FacultyValidator able to validate the request data.
     */
    public FacultyValidator getFacultyValidator(Request request) {
        return (FacultyValidator) getValidator(FacultyValidator::new, request);
    }

    // Private helper members are below ----------------------------------------------------

    @FunctionalInterface // to enable constructor reference for concrete Validators
    private interface TripleFunction<ReturnType, A1, A2, A3> {
        ReturnType apply(A1 argument1, A2 argument2, A3 argument3);
    }

    // extracted common procedures
    private Object getValidator(TripleFunction<Object, InputTool, OutputTool, ServicesFactory> function,
                                Request request) {
        InputTool inputTool = new InputTool(request);
        OutputTool outputTool = outputToolFactory.getInstance(request);
        return function.apply(inputTool, outputTool, servicesFactory);
    }


}
