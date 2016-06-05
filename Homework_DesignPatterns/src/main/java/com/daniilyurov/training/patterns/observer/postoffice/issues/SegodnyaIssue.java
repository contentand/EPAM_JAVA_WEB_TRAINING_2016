package com.daniilyurov.training.patterns.observer.postoffice.issues;

public class SegodnyaIssue implements Issue {

    String issueNumber;

    public SegodnyaIssue(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String getInfo() {
        return "Segodnya Issue " + issueNumber;
    }
}
