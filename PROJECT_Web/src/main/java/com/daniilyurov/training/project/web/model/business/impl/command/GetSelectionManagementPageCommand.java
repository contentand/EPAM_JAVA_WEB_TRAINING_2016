package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.impl.output.FacultyInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.FacultyService;
import com.daniilyurov.training.project.web.model.business.impl.tool.LocalizationTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import static com.daniilyurov.training.project.web.utility.SessionAttributes.*;
import static com.daniilyurov.training.project.web.model.business.impl.Intent.*;

public class GetSelectionManagementPageCommand extends AbstractAdminOnlyCommand {

    @Override
    protected String executeAsAdministrator(Provider provider) throws Exception {

        // setup dependencies
        FacultyValidator facultyValidator = provider.getFacultyValidator();
        FacultyService facultyService = provider.getFacultyService();
        ApplicationService applicationService = provider.getApplicationService();
        LocalizationTool localization = provider.getLocalizationTool();
        OutputTool output = provider.getOutputTool();

        // get faculty
        Faculty faculty = facultyValidator.parseExistingFacultyFromUrl();

        // get info about faculty
        FacultyInfoItem facultyInfo = new FacultyInfoItem();
        facultyService.fillWithStatistics(facultyInfo, faculty, localization);

        // get info about applicants
        ApplicationService.Applicants applicants = applicationService
                .collectApplicants(faculty, localization);

        // output data
        output.set(ATTRIBUTE_FACULTY, facultyInfo);
        output.set(ATTRIBUTE_UNCONSIDERED_APPLICANTS,
                applicants.getUnconsideredApplicants());
        output.set(ATTRIBUTE_APPLICATIONS_UNDER_CONSIDERATION,
                applicants.getApplicantsUnderConsideration());

        return GET_CURRENT_FACULTY_SELECTION_MANAGEMENT_PAGE;

    }
}
