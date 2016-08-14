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

/**
 * ResultValidator is designed to analyze input parameters
 * and locate, validate and create instances of subject results.
 *
 * @author Daniil Yurov
 */
public class ResultValidator extends AbstractValidator {

    protected InputTool input;
    protected ServicesFactory servicesFactory;

    public ResultValidator(InputTool input, OutputTool output, ServicesFactory servicesFactory) {
        if (input == null || output == null || servicesFactory == null) throw new NullPointerException();
        this.output = output;
        this.input = input;
        this.servicesFactory = servicesFactory;
    }

    /**
     * Looks for subject results from the parameters,
     * validates the data, creates instances of results and returns a set of them.
     * @param user the results will belong to
     * @return a set of valid result instances assigned to the user
     * @throws ValidationException if any user input is inconsistent
     * @throws DaoException if repository layer fails
     * @throws NullPointerException if null argument is passed
     */
    public Set<Result> parseResults(User user) throws ValidationException, DaoException, NullPointerException {
        if (user == null) throw new NullPointerException();
        Set<Result> userResults = new LinkedHashSet<>(); // should be linked to avoid shuffling

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
                userResults.add(result);
            }
        });

        return userResults;
    }
}
