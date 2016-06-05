package com.daniilyurov.training.patterns.observer.postoffice.publications;

import com.daniilyurov.training.patterns.observer.postoffice.PostOffice;
import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;
import com.daniilyurov.training.patterns.observer.postoffice.issues.SegodnyaIssue;

public class SegodnyaPublication extends Publication {


    public SegodnyaPublication(PostOffice office) {
        super(office);
    }

    @Override
    public void publish(Issue issue) {
        SegodnyaIssue newIssue = (SegodnyaIssue) issue;
        office.sendToSubscribers(this, newIssue);
    }

    @Override
    public String getCode() {
        return "segodnya";
    }
}
