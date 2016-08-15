package com.daniilyurov.training.project.web.utility;


/**
 * Summarises enumeration of all keys used as request parameters
 * across the application.
 */

public interface RequestParameters {
    String PREFIX_PARAMETER_SUBJECT_ID = "SUBJECT"; // prefix
    String PARAMETER_APPLICATION_ID = "application";
    String PARAMETER_EMAIL = "email";
    String PARAMETER_LATIN_FIRST_NAME = "latinFirstName";
    String PARAMETER_LATIN_LAST_NAME = "latinLastName";
    String PARAMETER_CYRILLIC_FIRST_NAME = "cyrillicFirstName";
    String PARAMETER_CYRILLIC_LAST_NAME = "cyrillicLastName";
    String PARAMETER_AVERAGE_SCHOOL_RESULT = "averageSchoolResult";
    String PARAMETER_LOGIN = "login";
    String PARAMETER_PASSWORD = "password";
    String PARAMETER_LANGUAGE = "language";
    String PARAMETER_FACULTY_ID = "faculty";
    String PARAMETER_FACULTY_EN_NAME = "enName";
    String PARAMETER_FACULTY_DE_NAME = "deName";
    String PARAMETER_FACULTY_RU_NAME = "ruName";
    String PARAMETER_FACULTY_EN_DESCRIPTION = "enDescription";
    String PARAMETER_FACULTY_DE_DESCRIPTION = "deDescription";
    String PARAMETER_FACULTY_RU_DESCRIPTION = "ruDescription";
    String PARAMETER_MONTHS_TO_STUDY = "monthsToStudy";
    String PARAMETER_STUDENTS_AMOUNT = "maxStudents";
    String PARAMETER_DATE_REGISTRATION_STARTS = "dateRegistrationStarts";
    String PARAMETER_DATE_REGISTRATION_ENDS = "dateRegistrationEnds";
    String PARAMETER_DATE_STUDIES_START = "dateStudiesStart";
    String PARAMETER_NEW_SUBJECT_EN_NAME = "newSubjectEnName";
    String PARAMETER_NEW_SUBJECT_DE_NAME = "newSubjectDeName";
    String PARAMETER_NEW_SUBJECT_RU_NAME = "newSubjectRuName";
    String PARAMETER_ACTION = "action";
    String PARAMETER_AFTER_PROCESS_DESTINATION_PATH = "afterProcessDestinationPath";
}
