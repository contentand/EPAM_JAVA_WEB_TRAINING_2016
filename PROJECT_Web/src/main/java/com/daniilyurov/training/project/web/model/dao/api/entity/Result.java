package com.daniilyurov.training.project.web.model.dao.api.entity;

import com.daniilyurov.training.project.web.i18n.Value;

import java.util.Optional;


public class Result extends Entity {

    private User applicant;
    private Subject subject;
    private double result;

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id).orElse(0L).intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Result)) return false;
        Result other = (Result) obj;
        return Optional.ofNullable(this.id).orElse(0L).equals(other.id);
    }
}
