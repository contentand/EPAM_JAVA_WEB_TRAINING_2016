package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.output.ApplicantInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidationException;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import java.util.TreeSet;

import static com.daniilyurov.training.project.web.model.business.impl.Key.*;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_FACULTY_NAME;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_STUDENTS_FROM_LAST_SELECTION;

public class GetLastAcceptedPageCommand extends AbstractAdminOnlyCommand {
    @Override
    protected String executeAsAdministrator(Request request) throws Exception {
        // setup dependencies
        FacultyValidator facultyValidator = validatorFactory.getFacultyValidator(request);
        FacultyService facultyService = servicesFactory.getFacultyService();
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);

        // get faculty
        Faculty faculty = facultyValidator.parseExistingFacultyFromUrl();

        // get students from last selection
        TreeSet<ApplicantInfoItem> studentsOfLatestSelection = applicationService
                .collectStudentsOfLastSelection(faculty, output);

        // notify
        output.set(ATTRIBUTE_STUDENTS_FROM_LAST_SELECTION,
                studentsOfLatestSelection);
        output.set(ATTRIBUTE_FACULTY_NAME, output.getLocalName(faculty));

        return GET_LAST_ACCEPTED_PAGE;
    }
}
