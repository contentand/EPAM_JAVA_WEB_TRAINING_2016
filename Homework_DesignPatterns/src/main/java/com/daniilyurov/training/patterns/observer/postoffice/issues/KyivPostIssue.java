package com.daniilyurov.training.patterns.observer.postoffice.issues;

public class KyivPostIssue implements Issue {

    String issueNumber;

    public KyivPostIssue(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String getInfo() {
        return "KyivPost Issue " + issueNumber;
    }

}
