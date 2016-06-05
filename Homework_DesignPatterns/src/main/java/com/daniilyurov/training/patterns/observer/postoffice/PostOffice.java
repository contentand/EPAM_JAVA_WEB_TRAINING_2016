package com.daniilyurov.training.patterns.observer.postoffice;

import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;
import com.daniilyurov.training.patterns.observer.postoffice.publications.Publication;

/**
 * Subject
 */
public interface PostOffice {
    // attach()
    void registerSubscriber(Subscriber subscriber, String publicationCode);
    // detach()
    void removeSubscriber(Subscriber subscriber, String publicationCode);
    // notifyObservers()
    void sendToSubscribers(Publication publication, Issue newIssue);

    void registerPublication(Publication publication);
}
