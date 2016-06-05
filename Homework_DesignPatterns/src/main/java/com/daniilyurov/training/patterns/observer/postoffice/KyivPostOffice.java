package com.daniilyurov.training.patterns.observer.postoffice;

import com.daniilyurov.training.patterns.observer.postoffice.issues.Issue;
import com.daniilyurov.training.patterns.observer.postoffice.publications.Publication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Concrete Subject.
 * Current implementation is stateless due to the nature of the task!
 * Thus there is no field "state" and no getters and setters for it.
 */
public class KyivPostOffice implements PostOffice {

    // key: publicationCode of the publication.
    // value: list of subscribers for the publication.
    private Map<String, List<KyivSubscriber>> publicationSubscriptions;

    public KyivPostOffice() {
        this.publicationSubscriptions = new HashMap<>();
    }

    @Override
    public void registerSubscriber(Subscriber subscriber, String publicationCode) {
        if (!publicationSubscriptions.containsKey(publicationCode)) {
            throw new IllegalArgumentException("Such publication does not exist!");
        }
        List<KyivSubscriber> subscribers = publicationSubscriptions.get(publicationCode);
        subscribers.add((KyivSubscriber) subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber, String publicationCode) {
        if (!publicationSubscriptions.containsKey(publicationCode)) {
            throw new IllegalArgumentException("Such publication does not exist!");
        }

        List<KyivSubscriber> subscribers = publicationSubscriptions.get(publicationCode);
        KyivSubscriber kyivSubscriber = (KyivSubscriber) subscriber;

        if (!subscribers.contains(kyivSubscriber)) {
            throw new IllegalArgumentException("This subscriber is not subscribed for this publication.");
        }

        subscribers.remove(kyivSubscriber);
    }

    @Override
    public void registerPublication(Publication publication) {
        String publicationCode = publication.getCode();
        if (publicationSubscriptions.containsKey(publicationCode)) {
            throw new IllegalArgumentException("Such publication is already registered!");
        }
        publicationSubscriptions.put(publicationCode, new ArrayList<>());
    }

    @Override
    public void sendToSubscribers(Publication publication, Issue newIssue) {
        String publicationCode = publication.getCode();
        if (!publicationSubscriptions.containsKey(publicationCode)) {
            throw new IllegalArgumentException("No such publication is registered!");
        }
        publicationSubscriptions.get(publicationCode)
                .forEach(subscriber -> subscriber.receive(newIssue));
    }
}
