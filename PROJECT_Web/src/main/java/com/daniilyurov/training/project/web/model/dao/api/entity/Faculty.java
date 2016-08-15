package com.daniilyurov.training.project.web.model.dao.api.entity;

import com.daniilyurov.training.project.web.i18n.DescriptionLocalizable;
import com.daniilyurov.training.project.web.i18n.NameLocalizable;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Faculty contains its names, descriptions and information about the latest
 * selection.
 */
public class Faculty extends Entity implements NameLocalizable, DescriptionLocalizable {

    private String enName; // faculty name in English
    private String ruName; // faculty name in Russian
    private String deName; // faculty name in German
    private String enDescription; // faculty description in English
    private String ruDescription; // faculty description in Russian
    private String deDescription; // faculty description in German
    private int students; // number of students that will be selected
    private Date dateRegistrationStarts; // date when candidates can start applying
    private Date dateRegistrationEnds; // date until which candidates can send their applications
    private Date dateStudiesStart; // date studies for current selection start
    private Long monthsToStudy; // the study period of current selection

    private Set<Subject> requiredSubjects;

    @Override
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Override
    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    @Override
    public String getDeName() {
        return deName;
    }

    public void setDeName(String deName) {
        this.deName = deName;
    }

    @Override
    public String getEnDescription() {
        return enDescription;
    }

    public void setEnDescription(String enDescription) {
        this.enDescription = enDescription;
    }

    @Override
    public String getRuDescription() {
        return ruDescription;
    }

    public void setRuDescription(String ruDescription) {
        this.ruDescription = ruDescription;
    }

    @Override
    public String getDeDescription() {
        return deDescription;
    }

    public void setDeDescription(String deDescription) {
        this.deDescription = deDescription;
    }

    public int getStudents() {
        return students;
    }

    public void setStudents(int students) {
        this.students = students;
    }

    public Date getDateRegistrationStarts() {
        return dateRegistrationStarts;
    }

    public void setDateRegistrationStarts(Date dateRegistrationStarts) {
        this.dateRegistrationStarts = dateRegistrationStarts;
    }

    public Date getDateRegistrationEnds() {
        return dateRegistrationEnds;
    }

    public void setDateRegistrationEnds(Date dateRegistrationEnds) {
        this.dateRegistrationEnds = dateRegistrationEnds;
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

    public Set<Subject> getRequiredSubjects() {
        return (requiredSubjects != null) ? requiredSubjects : Collections.emptySet();
    }

    public void setRequiredSubjects(Set<Subject> requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id).orElse(0L).intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Faculty)) return false;
        Faculty other = (Faculty) obj;
        return this.id.equals(other.id);
    }
}
