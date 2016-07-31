package com.daniilyurov.training.project.web.model.dao.api.entity;

import java.sql.Date;
import java.util.Optional;


public class Application extends Entity {

    private Faculty faculty;
    private User user;
    private Status status;
    private Date dateStudiesStart;
    private Long monthsToStudy;


    public enum Status {
        APPLIED,             // applicant wants to study; administrator has not registered him/her yet.
        UNDER_CONSIDERATION, // applicant wants to study; administrator has registered him/her.
        CANCELLED,           // applicant withdraws his/her candidacy.
        REJECTED,            // applicant did not pass selection and is rejected. S/he should try again next time.
        ACCEPTED,            // applicant passed selection and is accepted. S/he is now a student!
        EXPELLED,            // student did not do well; Administrator expelled him/her.
        QUIT,                // student cannot study; Student quits before finishing his/her studies.
        GRADUATED            // student finished his studies successfully.
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDateStudiesStart() {
        return dateStudiesStart;
    }

    public void setDateStudiesStart(Date dateStudiesStart) {
        this.dateStudiesStart = dateStudiesStart;
    }

    public Long getMonthsToStudy() {
        return monthsToStudy;
    }

    public void setMonthsToStudy(Long monthsToStudy) {
        this.monthsToStudy = monthsToStudy;
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id).orElse(0L).intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Application)) return false;
        Application other = (Application) obj;
        return this.id.equals(other.id);
    }
}
