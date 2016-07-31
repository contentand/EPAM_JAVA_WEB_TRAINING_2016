package com.daniilyurov.training.project.web.model.business.impl.output;

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

    @Override // Descending order
    public int compareTo(ApplicantInfoItem o) {
        return Double.compare(o.totalScore, this.totalScore);
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
