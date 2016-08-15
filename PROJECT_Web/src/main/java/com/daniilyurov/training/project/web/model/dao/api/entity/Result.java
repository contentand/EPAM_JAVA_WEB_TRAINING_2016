package com.daniilyurov.training.project.web.model.dao.api.entity;

import java.util.Optional;

/**
 * Result stores information about the owner of the result,
 * the subject and the score for the subject
 */
public class Result extends Entity {

    private User applicant; // owner of result
    private Subject subject; // subject in question
    private double result; // score for the subject

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
