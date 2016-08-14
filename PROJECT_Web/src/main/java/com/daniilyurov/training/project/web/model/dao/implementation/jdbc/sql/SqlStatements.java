package com.daniilyurov.training.project.web.model.dao.implementation.jdbc.sql;

/**
 * An enumeration of all SQL Statements used in the application assembled.
 */
public interface SqlStatements {
    String CREATE_APPLICATION = "INSERT INTO application (faculty_id, applicant_id, status, " +
            "date_studies_start, months_to_study) VALUES (?,?,?,?,?)";

    String UPDATE_APPLICATION = "UPDATE application SET " +
            "status = ?, date_studies_start = ?, months_to_study = ? " +
            "WHERE id = ?";

    String DELETE_APPLICATION = "DELETE FROM application WHERE id = ?";

    String GET_APPLICATION_BY_ID = "SELECT application.id, application.faculty_id, application.applicant_id, " +
            "application.status, application.date_studies_start, application.months_to_study, faculty.en_name, " +
            "faculty.ru_name, faculty.de_name, faculty.en_description, faculty.ru_description, " +
            "faculty.de_description, faculty.students, faculty.date_registration_starts, " +
            "faculty.date_registration_ends, faculty.date_studies_start, faculty.months_to_study, " +
            "user.login, user.password, user.email, user.authority, user.locale, user.latin_first_name, " +
            "user.latin_last_name, user.cyrillic_first_name, user.cyrillic_last_name, user.average_school_result " +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "JOIN user ON application.applicant_id = user.id " +
            "WHERE application.id = ?";

    String GET_ALL_APPLICATIONS = "SELECT application.id, application.faculty_id, application.applicant_id, " +
            "application.status, application.date_studies_start, application.months_to_study, " +
            "faculty.en_name, faculty.ru_name, faculty.de_name, faculty.en_description, " +
            "faculty.ru_description, faculty.de_description, faculty.students, " +
            "faculty.date_registration_starts, faculty.date_registration_ends, " +
            "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
            "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
            "user.cyrillic_last_name, user.average_school_result " +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "JOIN user ON application.applicant_id = user.id";

    String GET_ALL_APPLICATIONS_OF_USER = "SELECT application.id, application.faculty_id, application.applicant_id, " +
            "application.status, application.date_studies_start, application.months_to_study, " +
            "faculty.en_name, faculty.ru_name, faculty.de_name, faculty.en_description, " +
            "faculty.ru_description, faculty.de_description, faculty.students, " +
            "faculty.date_registration_starts, faculty.date_registration_ends, " +
            "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
            "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
            "user.cyrillic_last_name, user.average_school_result " +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "JOIN user ON application.applicant_id = user.id " +
            "WHERE application.applicant_id = ?";

    String GET_LAST_APPLICATION_OF_USER_FOR_FACULTY = "SELECT application.id, application.faculty_id, " +
            "application.applicant_id, " +
            "application.status, application.date_studies_start, application.months_to_study, " +
            "faculty.en_name, faculty.ru_name, faculty.de_name, faculty.en_description, " +
            "faculty.ru_description, faculty.de_description, " +
            "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
            "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
            "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
            "user.cyrillic_last_name, user.average_school_result " +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "JOIN user ON application.applicant_id = user.id " +
            "WHERE application.faculty_id = ? AND application.applicant_id = ?" +
            "AND application.date_studies_start IN (SELECT MAX(date_studies_start) FROM application " +
            "WHERE faculty_id=? AND applicant_id=?)";

    String GET_ALL_APPLICATIONS_FOR_FACULTY_WITH_STATUS = "SELECT application.id, application.faculty_id, " +
            "application.applicant_id, application.status, " +
            "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
            "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
            "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
            "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
            "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
            "user.cyrillic_last_name, user.average_school_result " +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "JOIN user ON application.applicant_id = user.id " +
            "WHERE application.faculty_id = ? AND application.status = ?";

    String GET_ALL_APPLICATIONS_FOR_FACULTY_OF_PARTICULAR_SELECTION = "SELECT application.id, " +
            "application.faculty_id, application.applicant_id, application.status, " +
            "application.date_studies_start, application.months_to_study, faculty.en_name, faculty.ru_name, " +
            "faculty.de_name, faculty.en_description, faculty.ru_description, faculty.de_description, " +
            "faculty.students, faculty.date_registration_starts, faculty.date_registration_ends, " +
            "faculty.date_studies_start, faculty.months_to_study, user.login, user.password, user.email, " +
            "user.authority, user.locale, user.latin_first_name, user.latin_last_name, user.cyrillic_first_name, " +
            "user.cyrillic_last_name, user.average_school_result " +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "JOIN user ON application.applicant_id = user.id " +
            "WHERE application.faculty_id = ? AND application.date_studies_start = ?";

    String COUNT_APPLICATIONS_FOR_FACULTY_OF_STATUS = "SELECT COUNT(*)" +
            "FROM application " +
            "JOIN faculty ON application.faculty_id = faculty.id " +
            "WHERE application.faculty_id = ? AND application.status = ?";

    String COUNT_APPLICATIONS_OF_USER_WITH_STATUS = "SELECT COUNT(*)" +
            "FROM application " +
            "WHERE application.applicant_id = ? AND application.status = ?";

    String UPDATE_STATUS_OF_ALL_APPLICATIONS = "UPDATE application SET status = ? WHERE id = ?";

    String CREATE_FACULTY = "INSERT INTO faculty " +
            "(en_name, ru_name, de_name, en_description, ru_description, de_description, students, " +
            "date_registration_starts, date_registration_ends, date_studies_start, months_to_study) " +
            "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

    String UPDATE_FACULTY = "UPDATE faculty SET " +
            "en_name = ?, ru_name = ?, de_name = ?, en_description = ?, ru_description = ?, " +
            "de_description = ?, students = ?, date_registration_starts = ?, date_registration_ends = ?, " +
            "date_studies_start = ?, months_to_study = ? " +
            "WHERE id = ?";

    String DELETE_FACULTY = "DELETE FROM faculty WHERE id = ?";

    String GET_FACULTY_BY_ID = "SELECT * FROM faculty WHERE id = ?";

    String GET_ALL_FACULTIES = "SELECT * FROM faculty";

    String COUNT_FACULTIES_WITH_EN_NAME = "SELECT COUNT(en_name) FROM faculty WHERE en_name = ?";

    String COUNT_FACULTIES_WITH_DE_NAME = "SELECT COUNT(de_name) FROM faculty WHERE de_name = ?";

    String COUNT_FACULTIES_WITH_RU_NAME = "SELECT COUNT(ru_name) FROM faculty WHERE ru_name = ?";

    String SET_REQUIRED_SUBJECTS_FOR_FACULTY = "INSERT INTO requirements (faculty_id, subject_id) VALUES(?,?)";

    String DELETE_REQUIRED_SUBJECTS_FOR_FACULTY = "DELETE FROM requirements WHERE faculty_id = ?";

    String DELETE_APPLICATIONS_FOR_FACULTY = "DELETE FROM application WHERE faculty_id = ?";

    String GET_REQUIRED_SUBJECTS_FOR_FACULTY = "SELECT subject.id, subject.en_name, " +
            "subject.de_name, subject.ru_name " +
            "FROM requirements JOIN subject ON subject.id = requirements.subject_id " +
            "WHERE requirements.faculty_id = ?";
}
