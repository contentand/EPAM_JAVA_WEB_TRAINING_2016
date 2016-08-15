package com.daniilyurov.training.project.web.model.business.impl.output;

import com.daniilyurov.training.project.web.model.dao.api.entity.Application;
import com.daniilyurov.training.project.web.model.dao.api.entity.Faculty;

import java.util.Date;

/**
 * A wrapper for encapsulating information
 * about a particular faculty.
 * Motivation: pass such item to view for display.
 */
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
        SCHEDULED, IN_PROGRESS, OVER_UNSET, OVER_SET
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

    // Getters and setters are listed below

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

    @SuppressWarnings("unused") // used by view
    public String getLocalDescription() {
        return localDescription;
    }

    public void setLocalDescription(String localDescription) {
        this.localDescription = localDescription;
    }

    @SuppressWarnings("unused") // used by view
    public Date getRegistrationStart() {
        return registrationStart;
    }

    public void setRegistrationStart(Date registrationStart) {
        this.registrationStart = registrationStart;
    }

    @SuppressWarnings("unused") // used by view
    public Date getRegistrationEnd() {
        return registrationEnd;
    }

    public void setRegistrationEnd(Date registrationEnd) {
        this.registrationEnd = registrationEnd;
    }

    @SuppressWarnings("unused") // used by view
    public Date getStudyStart() {
        return studyStart;
    }

    public void setStudyStart(Date studyStart) {
        this.studyStart = studyStart;
    }

    @SuppressWarnings("unused") // used by view
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @SuppressWarnings("unused") // used by view
    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    @SuppressWarnings("unused") // used by view
    public Long getNumberOfGraduates() {
        return numberOfGraduates;
    }

    public void setNumberOfGraduates(Long numberOfGraduates) {
        this.numberOfGraduates = numberOfGraduates;
    }

    @SuppressWarnings("unused") // used by view
    public Long getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Long numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    @SuppressWarnings("unused") // used by view
    public Long getNumberOfAppliedStudents() {
        return numberOfAppliedStudents;
    }

    public void setNumberOfAppliedStudents(Long numberOfAppliedStudents) {
        this.numberOfAppliedStudents = numberOfAppliedStudents;
    }

    @SuppressWarnings("unused") // used by view
    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(RegistrationStatus registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    @SuppressWarnings("unused") // used by view
    public Long getNumberOfStudentsToAddUnderConsideration() {
        return numberOfStudentsToAddUnderConsideration;
    }

    public void setNumberOfStudentsToAddUnderConsideration(Long numberOfStudentsToAddUnderConsideration) {
        this.numberOfStudentsToAddUnderConsideration = numberOfStudentsToAddUnderConsideration;
    }

    @SuppressWarnings("unused") // used by view
    public Long getApplicationIdForCurrentSelection() {
        return applicationIdForCurrentSelection;
    }

    public void setApplicationIdForCurrentSelection(Long applicationIdForCurrentSelection) {
        this.applicationIdForCurrentSelection = applicationIdForCurrentSelection;
    }

    @SuppressWarnings("unused") // used by view
    public Application.Status getLatestStatusOfCurrentUser() {
        return latestStatusOfCurrentUser;
    }

    public void setLatestStatusOfCurrentUser(Application.Status latestStatusOfCurrentUser) {
        this.latestStatusOfCurrentUser = latestStatusOfCurrentUser;
    }

    @SuppressWarnings("unused") // used by view
    public String[] getRequiredSubjects() {
        return requiredSubjects;
    }

    public void setRequiredSubjects(String[] requiredSubjects) {
        this.requiredSubjects = requiredSubjects;
    }
}
