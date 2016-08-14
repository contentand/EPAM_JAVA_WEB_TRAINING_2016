DROP SCHEMA IF EXISTS `project4`;

CREATE SCHEMA `project4`;

USE `project4`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(150) NOT NULL,
  `authority` varchar(45) NOT NULL,
  `locale` varchar(45) NOT NULL,
  `latin_first_name` varchar(45) NOT NULL,
  `latin_last_name` varchar(45) NOT NULL,
  `cyrillic_first_name` varchar(45) NOT NULL,
  `cyrillic_last_name` varchar(45) NOT NULL,
  `average_school_result` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_login_uindex` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

CREATE TABLE `subject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(255) NOT NULL,
  `ru_name` varchar(255) NOT NULL,
  `de_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `subject_id_uindex` (`id`),
  UNIQUE KEY `subject_en_name_uindex` (`en_name`),
  UNIQUE KEY `subject_ru_name_uindex` (`ru_name`),
  UNIQUE KEY `subject_de_name_uindex` (`de_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `applicant_id` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL,
  `result` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `result_id_uindex` (`id`),
  CONSTRAINT `result_subject_id_fk` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`),
  CONSTRAINT `result_user_id_fk` FOREIGN KEY (`applicant_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `faculty` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(255) NOT NULL,
  `ru_name` varchar(255) NOT NULL,
  `de_name` varchar(255) NOT NULL,
  `en_description` text NOT NULL,
  `ru_description` text NOT NULL,
  `de_description` text NOT NULL,
  `students` int(11) NOT NULL,
  `date_registration_starts` date NOT NULL,
  `date_registration_ends` date NOT NULL,
  `date_studies_start` date NOT NULL,
  `months_to_study` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `faculty_id_uindex` (`id`),
  UNIQUE KEY `faculty_en_name_uindex` (`en_name`),
  UNIQUE KEY `faculty_ru_name_uindex` (`ru_name`),
  UNIQUE KEY `faculty_de_name_uindex` (`de_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_id` int(11) NOT NULL,
  `applicant_id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `date_studies_start` date NOT NULL,
  `months_to_study` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `application_id_uindex` (`id`),
  CONSTRAINT `application_faculty_id_fk` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `application_user_id_fk` FOREIGN KEY (`applicant_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


CREATE TABLE `requirements` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `faculty_id` int(11) NOT NULL,
  `subject_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `requirements_id_uindex` (`id`),
  CONSTRAINT `requirements_faculty_id_fk` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`),
  CONSTRAINT `requirements_subject_id_fk` FOREIGN KEY (`subject_id`) REFERENCES `subject` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

INSERT INTO user (id, login, password, email, authority, locale, latin_first_name, latin_last_name,
                  cyrillic_first_name, cyrillic_last_name, average_school_result)
    VALUES
      (1, 'login1', 'password1', 'email1@email.em', 'APPLICANT', 'ru', 'Kirill', 'Smirnov',
        'Кирилл', 'Смирнов', 180),
      (2, 'login2', 'password2', 'email2@email.em', 'APPLICANT', 'ru', 'Vladimir', 'Smirnov',
        'Владимир', 'Смирнов', 190);

INSERT INTO faculty (id, en_name, ru_name, de_name, en_description, ru_description, de_description,
                     students, date_registration_starts, date_registration_ends, date_studies_start,
                     months_to_study)
    VALUES
      (1, 'Faculty1', 'Факультет1', 'Fakultät1', 'Description', 'Описание', 'Beschreibung',
        1, '2016-08-10', '2016-09-29', '2016-10-10', 50),
      (2, 'Faculty2', 'Факультет2', 'Fakultät2', 'Description', 'Описание', 'Beschreibung',
        1, '2016-08-10', '2016-09-29', '2016-10-10', 50);

INSERT INTO application (id, faculty_id, applicant_id, status, date_studies_start, months_to_study)
    VALUES
      (1, 1, 1, 'QUIT', '2013-10-10', 50),
      (2, 1, 1, 'CANCELLED', '2016-10-10', 50),
      (3, 2, 1, 'APPLIED', '2016-10-10', 50),
      (4, 1, 2, 'APPLIED', '2016-10-10', 50),
      (5, 2, 2, 'CANCELLED', '2016-10-10', 50);
