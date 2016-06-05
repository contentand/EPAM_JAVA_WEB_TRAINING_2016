package com.daniilyurov.training.patterns.observer.postoffice.publications;

import com.daniilyurov.training.patterns.observer.postoffice.PostOffice;
import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;
import com.daniilyurov.training.patterns.observer.postoffice.issues.KyivPostIssue;

public class KyivPostPublication extends Publication {

    public KyivPostPublication(PostOffice office) {
        super(office);
    }

    @Override
    public void publish(Issue issue) {
        KyivPostIssue newIssue = (KyivPostIssue) issue;
        office.sendToSubscribers(this, newIssue);
    }

    @Override
    public String getCode() {
        return "kyivPost";
    }
}
