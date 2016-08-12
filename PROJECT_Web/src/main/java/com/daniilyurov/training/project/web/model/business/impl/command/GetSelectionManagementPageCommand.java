package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.*;

public class GetSelectionManagementPageCommand extends AbstractAdminOnlyCommand {

    @Override
    protected String executeAsAdministrator(Request request) throws Exception {

        // setup dependencies
        FacultyValidator facultyValidator = validatorFactory.getFacultyValidator(request);
        FacultyService facultyService = servicesFactory.getFacultyService();
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);

        // get faculty
        Faculty faculty = facultyValidator.parseExistingFacultyFromUrl();

        // get info about faculty
        FacultyInfoItem facultyInfo = new FacultyInfoItem();
        facultyService.fillWithStatistics(facultyInfo, faculty, output);

        // get info about applicants
        ApplicationService.Applicants applicants = applicationService
                .collectApplicants(faculty, output);

        // output data
        output.set(ATTRIBUTE_FACULTY, facultyInfo);
        output.set(ATTRIBUTE_UNCONSIDERED_APPLICANTS,
                applicants.getUnconsideredApplicants());
        output.set(ATTRIBUTE_APPLICATIONS_UNDER_CONSIDERATION,
                applicants.getApplicantsUnderConsideration());

        return GET_CURRENT_FACULTY_SELECTION_MANAGEMENT_PAGE;

    }
}
