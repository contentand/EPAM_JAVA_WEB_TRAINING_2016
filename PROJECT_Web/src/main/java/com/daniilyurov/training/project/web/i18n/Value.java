package com.daniilyurov.training.project.web.i18n;

public enum Value {


    INFO_PLEASE_LOGIN, // Please login or register an account.

    SUC_USER_CREATED, // User has been created. You can now login.
    SUC_APPLICATION_FOR_X_CREATED, // You have successfully sent your application to our {0}.
    SUC_FACULTY_X_CREATED, // {0} has been successfully created.
    SUC_SUCCESSFUL_LOGOUT, // Good bye!
    SUC_APPLICATION_FOR_X_CANCELLED, // Your application for {0} has been successfully cancelled.
    SUC_QUIT_STUDIES_IN_X, // You have quit studies at {0}.
    SUC_APPLICANT_ACCEPTED, // Applicant has been accepted.
    SUC_APPLICANT_REJECTED, // Applicant has been rejected.
    SUC_STUDENT_EXPELLED, // Student has been expelled.
    SUC_APPLICANT_UNDER_CONSIDERATION, // The applicant is now under consideration.
    SUC_CANCELLED_SCHEDULED_SELECTION_FOR_X, // The scheduled registration for {0} has been cancelled.
    SUC_SCHEDULED_SELECTION_FOR_X, //
    SUC_SUCCESSFUL_SELECTION_OF_X_FOR_Y, // Successfully selected {0,number} students for {1}.

    INFO_MAXIMUM_X_PARALLEL_STUDIES, // One can do at most {0,number} parallel studies.

    ERR_UNAUTHORIZED_APPLICATION, // Unauthorized users cannot apply.
    ERR_ADMINS_CANNOT_APPLY, // Administrators cannot apply.
    ERR_X_SUBJECT_MISSING_IN_PROFILE, // {0} is missing in your profile.
    ERR_YOU_CANNOT_APPLY_FOR_X, // You cannot apply for {0}.
    ERR_ALREADY_A_STUDENT_AT_X_FACULTIES, // You are already studying at {0,number} faculties.
    ERR_REGISTRATION_STARTS_AFTER_X, // Registration starts on {0,date,short}.
    ERR_REGISTRATION_OVER, // Application period is over.
    ERR_ALREADY_A_STUDENT_AT_THIS_FACULTY, // You are already a student here.
    ERR_ALREADY_A_GRADUATE_OF_THIS_FACULTY, // You have already graduated it.
    ERR_ALREADY_BEING_APPLIED, // You are already applied.
    ERR_WRONG_FACULTY_IDENTIFIER, // You are trying to address the faculty that does not exist.
    ERR_UNAUTHORIZED_OPERATION, // Unauthorized operation.
    ERR_EN_NAME_TAKEN, // Such English name is already taken by other.
    ERR_DE_NAME_TAKEN, // Such German name is already taken by other.
    ERR_RU_NAME_TAKEN, // Such Russian name is already taken by other.
    ERR_LOGIN_IS_TAKEN, // Such login is already taken.
    ERR_SYSTEM_ERROR, // System Error.
    ERR_INSUFFICIENT_INPUT, // Insufficient input.
    ERR_WRONG_INPUT, // Wrong input format.
    ERR_START_IS_AFTER_END_DATE, // Start date should be before end date!
    ERR_START_IS_BEFORE_REGISTRATION, // Studies should start after registration ends!
    ERR_REGISTRATION_SHOULD_FINISH_AFTER_NOW, // Registration cannot end before now.
    ERR_SUBJECT_NAMES_SHOULD_BE_UNIQUE, // Subject names should be unique.
    ERR_EDITING_INVALID_APPLICATION, // You are trying to edit wrong application.
    ERR_NON_EXISTING_APPLICATION, // Application does not exist.
    ERR_X_IS_EMPTY, // "Field {0} should not be empty."
    ERR_X_IS_BELOW_Y, // Field {0} is less than {1,number}.
    ERR_X_IS_OVER_Y, // Field {0} is over {1,number}.
    ERR_X_INVALID_CYRILLIC, // Field {0} should contain only cyrillic characters.
    ERR_X_INVALID_LATIN, // Field {0} should contain only latin characters.
    ERR_X_INVALID, // Field {0} is invalid!
    ERR_CANNOT_PARSE_X, // Something is wrong with field {0}.
    ERR_PAGE_NOT_FOUND, // Could not find the page you requested.
    ERR_REGISTRATION_IS_ALREADY_SCHEDULED, // Registration is already scheduled.
    ERR_SOME_UNCONSIDERED_APPLICANTS_LEFT, //
    ERR_NOTHING_TO_SELECT_FROM, // There is no one to select from.

    FIELD_LAST_NAME, // "last name"
    FIELD_FIRST_NAME, // "first name"
    FIELD_LOGIN, // "login"
    FIELD_AVERAGE_SCHOOL_RESULT, // average school result
    FIELD_SUBJECT_RESULT, // subject result
    FIELD_MONTHS_TO_STUDY, // study period
    FIELD_DATE, // containing date value
    FIELD_NUMBER, // containing numeric value
    FIELD_NAME, // name
    FIELD_SUBJECTS_REQUIRED, // subjects required
    FIELD_DESCRIPTION, // description
    FIELD_PASSWORD, // password
    FIELD_EMAIL, // email
    FIELD_FACULTY, // faculty id
    FIELD_LOCALIZATION, // localization
    FIELD_APPLICATION_ID, // application

    WELCOME,
    WRONG_CREDENTIALS,

}
