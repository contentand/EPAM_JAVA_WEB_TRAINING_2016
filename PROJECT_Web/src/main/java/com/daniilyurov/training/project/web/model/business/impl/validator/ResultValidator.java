package com.daniilyurov.training.project.web.model.business.impl.validator;

import com.daniilyurov.training.project.web.model.business.impl.tool.InputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.dao.api.DaoException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Result;
import com.daniilyurov.training.project.web.model.dao.api.entity.Subject;
import com.daniilyurov.training.project.web.model.dao.api.entity.User;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import static com.daniilyurov.training.project.web.utility.RequestParameters.*;
import static com.daniilyurov.training.project.web.i18n.Value.*;


public class ResultValidator extends AbstractValidator {

    protected InputTool input;
    protected ServicesFactory servicesFactory;

    public ResultValidator(InputTool input, OutputTool output, ServicesFactory servicesFactory) {
        this.output = output;
        this.input = input;
        this.servicesFactory = servicesFactory;
    }

    public Set<Result> parseResultsForUser(User user) throws ValidationException, DaoException {
        Set<Result> newUserResults = new LinkedHashSet<>();

        Subject[] allSubjects = servicesFactory.getSubjectService().getAll();
        Stream.of(allSubjects).forEach(subject -> {
            String subjectResultToParse = input.getParameter(PREFIX_PARAMETER_SUBJECT_ID + subject.getId());
            if (subjectResultToParse != null) {
                shouldNotBeEmpty(subjectResultToParse);
                double subjectResult = parseDouble(subjectResultToParse);
                currentField = FIELD_SUBJECT_RESULT;
                shouldBeWithinRange(subjectResult, 100, 200);
                Result result = new Result();
                result.setApplicant(user);
                result.setSubject(subject);
                result.setResult(subjectResult);
                newUserResults.add(result);

            }
        });

        return newUserResults;
    }
}
