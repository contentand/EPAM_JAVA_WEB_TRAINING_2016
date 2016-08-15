package com.daniilyurov.training.project.web.model.business.impl.output;

/**
 * A wrapper for encapsulating information
 * about a particular applicant.
 * Motivation: pass such item to view for display.
 */
public class ApplicantInfoItem implements Comparable<ApplicantInfoItem> {
    Long applicationId;
    String localFirstName;
    String localLastName;
    double totalScore;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getLocalFirstName() {
        return localFirstName;
    }

    public void setLocalFirstName(String localFirstName) {
        this.localFirstName = localFirstName;
    }

    public String getLocalLastName() {
        return localLastName;
    }

    public void setLocalLastName(String localLastName) {
        this.localLastName = localLastName;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    @Override // Natural order: by total score, descending.
    public int compareTo(ApplicantInfoItem o) {
        int scoreDifference = Double.compare(o.totalScore, this.totalScore);

        return scoreDifference == 0
                ? Long.compare(o.applicationId, this.applicationId)
                : scoreDifference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicantInfoItem that = (ApplicantInfoItem) o;

        return applicationId != null ? applicationId.equals(that.applicationId) : that.applicationId == null;
    }

    @Override
    public int hashCode() {
        return applicationId != null ? applicationId.hashCode() : 0;
    }
}
