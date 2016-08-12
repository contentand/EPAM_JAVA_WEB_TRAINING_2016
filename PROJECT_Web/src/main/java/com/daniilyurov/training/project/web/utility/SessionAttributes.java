package com.daniilyurov.training.project.web.utility;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Summarises enumeration of all keys used in Session Container
 * across the application.
 */

public interface SessionAttributes {

    String BUNDLE = "bundle";
    String LOCALE = "locale";
    String USER_ID = "userId";
    String ROLE = "authority";
    String CSRF_TOKEN_SERVER = "csrfTokenServer";

    String CSRF_TOKEN = "csrfToken";
    String ATTRIBUTE_MSG = "msg";
    String ATTRIBUTE_MSG_ERROR = "msg_error";
    String ATTRIBUTE_MSG_SUCCESS = "msg_success";
    String ATTRIBUTE_SUBJECT_LIST = "subjects";
    String ATTRIBUTE_FACULTY_LIST = "faculties";
    String ATTRIBUTE_SUBJECT_VALUES = "subjectValues";
    String ATTRIBUTE_CURRENT_PAGE_LINK = "currentPageLink";
    String ATTRIBUTE_EXCEEDED_STUDY_LIMIT = "exceededStudyLimit";
    String ATTRIBUTE_IS_MAIN = "isMain";
    String ATTRIBUTE_IS_LOGIN_PAGE = "isLoginPage";
    String ATTRIBUTE_IS_USER_INFO_PAGE = "isUserInfoPage";
    String ATTRIBUTE_IS_REGISTRATION_PAGE = "isRegistrationPage";
    String ATTRIBUTE_FACULTY_ID = "facultyId";
    String ATTRIBUTE_FACULTY_NAME = "facultyName";
    String ATTRIBUTE_FACULTY = "faculty";
    String ATTRIBUTE_UNCONSIDERED_APPLICANTS = "unconsideredApplicants";
    String ATTRIBUTE_APPLICATIONS_UNDER_CONSIDERATION = "applicantsUnderConsideration";
    String ATTRIBUTE_USER_INFO = "user";

    Set<String> CORE_SESSION_ATTRIBUTES = new HashSet<String>() {{
        addAll(Arrays.asList(BUNDLE, LOCALE, USER_ID, ROLE, CSRF_TOKEN_SERVER));
    }};

}
