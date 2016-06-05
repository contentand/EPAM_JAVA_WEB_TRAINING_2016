package com.daniilyurov.training.patterns.observer.postoffice.publications;

import com.daniilyurov.training.patterns.observer.postoffice.PostOffice;
import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;

public abstract class Publication {

    protected PostOffice office;

    public Publication(PostOffice office) {
        this.office = office;
        office.registerPublication(this);
    }

    public abstract void publish(Issue issue);

    public abstract String getCode();
}
