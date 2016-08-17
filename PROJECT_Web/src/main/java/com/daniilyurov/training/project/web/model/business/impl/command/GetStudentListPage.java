package com.daniilyurov.training.project.web.model.business.impl.command;

import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.Provided;
import com.daniilyurov.training.project.web.model.business.impl.output.ApplicantInfoItem;
import com.daniilyurov.training.project.web.model.business.impl.service.ApplicationService;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.FacultyValidator;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import java.util.TreeSet;

import static com.daniilyurov.training.project.web.i18n.Value.*;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_MAIN_PAGE;
import static com.daniilyurov.training.project.web.model.business.impl.Key.GET_STUDENT_LIST;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_FACULTY_NAME;
import static com.daniilyurov.training.project.web.utility.SessionAttributes.ATTRIBUTE_STUDENTS_OF_FACULTY;

public class GetStudentListPage implements Command {

    protected OutputToolFactory outputToolFactory;
    protected ValidatorFactory validatorFactory;
    protected ServicesFactory servicesFactory;

    @Provided
    public void setOutputToolFactory(OutputToolFactory outputToolFactory,
                                     ValidatorFactory validatorFactory,
                                     ServicesFactory servicesFactory) {
        this.outputToolFactory = outputToolFactory;
        this.validatorFactory = validatorFactory;
        this.servicesFactory = servicesFactory;
    }

    @Override
    public String execute(Request request) {

        // setup dependencies
        FacultyValidator facultyValidator = validatorFactory.getFacultyValidator(request);
        ApplicationService applicationService = servicesFactory.getApplicationService();
        OutputTool output = outputToolFactory.getInstance(request);

        try {
            // get faculty
            Faculty faculty = facultyValidator.parseExistingFacultyFromUrl();

            // get students from last selection
            TreeSet<ApplicantInfoItem> facultyStudents = applicationService
                    .collectStudentsSortedByTotalScore(faculty, output); // output for localization

            output.set(ATTRIBUTE_STUDENTS_OF_FACULTY,
                    facultyStudents);
            output.set(ATTRIBUTE_FACULTY_NAME, output.getLocalName(faculty));

            return GET_STUDENT_LIST;

        } catch (Exception e) {
            output.setErrorMsg(ERR_SYSTEM_ERROR);
            return GET_MAIN_PAGE;
        }
    }
}
