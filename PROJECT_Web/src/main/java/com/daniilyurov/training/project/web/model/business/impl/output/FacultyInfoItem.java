package com.daniilyurov.training.project.web.model.business.impl.output;

import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import java.util.Date;

public class FacultyInfoItem {

    private Long id;
    private String localName;
    private String localDescription;
    private Date registrationStart;
    private Date registrationEnd;
    private Date studyStart;
    private Long duration;
    private String[] requiredSubjects;
    private Integer maxStudents;
    private Long numberOfGraduates;
    private Long numberOfStudents;
    private Long numberOfAppliedStudents;
    private Long numberOfStudentsToAddUnderConsideration;
    private RegistrationStatus registrationStatus;
    private Long applicationIdForCurrentSelection;
    private Application.Status latestStatusOfCurrentUser;

    public enum RegistrationStatus {
        SCHEDULED, IN_PROGRESS, OVER_UNSET, OVER_SET;
    }

    public static RegistrationStatus getStatus(Faculty faculty, Long applicants) {

        Date today = new Date();
        Date registrationStart = faculty.getDateRegistrationStarts();
        Date registrationEnd = faculty.getDateRegistrationEnds();

        if (registrationStart.after(today)) {
            return RegistrationStatus.SCHEDULED;
        }
        if (registrationStart.before(today) && registrationEnd.after(today)) {
            return RegistrationStatus.IN_PROGRESS;
        }
        if (registrationEnd.before(today) && applicants > 0) {
            return RegistrationStatus.OVER_UNSET;
        }
        if (registrationEnd.before(today) && applicants == 0) {
            return RegistrationStatus.OVER_SET;
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalDescription() {
        return localDescription;
    }

    public void setLocalDescription(String localDescription) {
        this.localDescription = localDescription;
    }

    public Date getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(Date registrationStart) {
        this.registrationStart = registrationStart;
    }

    public Date getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(Date registrationEnd) {
        this.registrationEnd = registrationEnd;
    }

    public Date getStudyStart() {
        return studyStart;
    }

    public void setStudyStart(Date studyStart) {
        this.studyStart = studyStart;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Long getNumberOfGraduates() {
        return numberOfGraduates;
    }

    public void setNumberOfGraduates(Long numberOfGraduates) {
        this.numberOfGraduates = numberOfGraduates;
    }

    public Long getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Long numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public Long getNumberOfAppliedStudents() {
        return numberOfAppliedStudents;
    }

    public void setNumberOfAppliedStudents(Long numberOfAppliedStudents) {
        this.numberOfAppliedStudents = numberOfAppliedStudents;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Long getNumberOfStudentsToAddUnderConsideration() {
        return numberOfStudentsToAddUnderConsideration;
    }

    public void setNumberOfStudentsToAddUnderConsideration(Long numberOfStudentsToAddUnderConsideration) {
        this.numberOfStudentsToAddUnderConsideration = numberOfStudentsToAddUnderConsideration;
    }

    public Long getApplicationIdForCurrentSelection() {
        return applicationIdForCurrentSelection;
    }

    public void setApplicationIdForCurrentSelection(Long applicationIdForCurrentSelection) {
        this.applicationIdForCurrentSelection = applicationIdForCurrentSelection;
    }

    public Application.Status getLatestStatusOfCurrentUser() {
        return latestStatusOfCurrentUser;
    }

    public void setLatestStatusOfCurrentUser(Application.Status latestStatusOfCurrentUser) {
        this.latestStatusOfCurrentUser = latestStatusOfCurrentUser;
    }

    public String[] getRequiredSubjects() {
        return requiredSubjects;
    }

    public void setRequiredSubjects(String[] requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }
}
