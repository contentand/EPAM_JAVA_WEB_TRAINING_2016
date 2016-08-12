package com.daniilyurov.training.project.web.model.business.impl;

/**
 * This utility interface encapsulates all key constants.
 * Keys are strings. They are used as communication medium between:
 * 1. class-mapping.properties
 * 2. jsp-mapping.properties
 * 3. url-mapping.properties
 *
 * Caution: change strings here only if something in the above mentioned
 * files changes. Not vise-versa!
 *
 * Motivation: to avoid typing String values into code directly.
 * This allows us to prevent typos and to simplify refactoring in the future.
 *
 * @author Daniil Yurov
 */
public interface Key {
    String REDIRECT_TO_WHERE_HE_CAME_FROM = null;
    String EMPTY = "EMPTY";
    String DO_CHANGE_LANGUAGE = "DO_CHANGE_LANGUAGE";
    String DO_LOGIN = "DO_LOGIN";
    String DO_LOGOUT = "DO_LOGOUT";
    String DO_CREATE_USER = "DO_CREATE_USER";
    String DO_APPLY = "DO_APPLY";
    String DO_REAPPLY = "DO_REAPPLY";
    String DO_CANCEL_APPLICATION = "DO_CANCEL_APPLICATION";
    String DO_CONSIDER_APPLICATION = "DO_CONSIDER_APPLICATION";
    String DO_EXPEL_STUDENT = "DO_EXPEL_STUDENT";
    String DO_CREATE_FACULTY = "DO_CREATE_FACULTY";
    String DO_SCHEDULE_SELECTION = "DO_SCHEDULE_SELECTION";
    String DO_DELETE_SCHEDULED_SELECTION = "DO_DELETE_SCHEDULED_SELECTION";
    String DO_UPDATE_CURRENT_USER = "DO_UPDATE_CURRENT_USER";
    String DO_UPDATE_FACULTY = "DO_UPDATE_FACULTY";
    String DO_ACCEPT_BEST_STUDENTS = "DO_ACCEPT_BEST_STUDENTS";
    String DO_UPDATE_CURRENT_SELECTION = "DO_UPDATE_CURRENT_SELECTION";
    String GET_LOGIN_PAGE = "GET_LOGIN_PAGE";
    String GET_USER_REGISTRATION_FORM = "GET_USER_REGISTRATION_FORM";
    String GET_CURRENT_USER_INFO = "GET_CURRENT_USER_INFO";
    String GET_MAIN_PAGE = "GET_MAIN_PAGE";
    String GET_FACULTY_CREATION_FORM = "GET_FACULTY_CREATION_FORM";
    String GET_FACULTY_EDIT_PAGE = "GET_FACULTY_EDIT_PAGE";
    String GET_CURRENT_FACULTY_SELECTION_MANAGEMENT_PAGE = "GET_CURRENT_FACULTY_SELECTION_MANAGEMENT_PAGE";
    String GET_SELECTION_CREATION_FORM = "GET_SELECTION_CREATION_FORM";
    String GET_FACULTY_STUDENTS_AND_GRADUATES_MANAGEMENT_CONSOLE = "GET_FACULTY_STUDENTS_AND_GRADUATES_MANAGEMENT_CONSOLE";
    String GET_ERROR_500 = "GET_ERROR_500";




}
