package com.daniilyurov.training.patterns.observer.postoffice;

import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;

/**
 * Observer
 */
public interface Subscriber {
    // update()
    void receive(Issue newIssue);
}
