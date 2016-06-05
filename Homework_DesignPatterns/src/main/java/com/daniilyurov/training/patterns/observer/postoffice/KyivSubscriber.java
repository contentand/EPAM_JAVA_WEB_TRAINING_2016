package com.daniilyurov.training.patterns.observer.postoffice;

import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;

/**
 * Concrete Observer.
 */
public class KyivSubscriber implements Subscriber {

    private String name;

    public KyivSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void receive(Issue newIssue) {
        System.out.println(name + " received " + newIssue.getInfo());
    }
}
