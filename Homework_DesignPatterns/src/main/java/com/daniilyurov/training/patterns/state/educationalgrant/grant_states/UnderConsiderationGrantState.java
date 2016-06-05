package com.daniilyurov.training.patterns.state.educationalgrant.grant_states;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import com.daniilyurov.training.patterns.state.educationalgrant.GrantState;

/**
 * Concrete State
 */
public class UnderConsiderationGrantState implements GrantState {
    @Override
    public void cancel(GrantApplication context) {
        System.out.println("Application №" + context.getId() + " cancelled. " +
                "You've been under consideration. You could have received the grant!");
        context.setState(new CancelledGrantState());
    }

    @Override
    public void consider(GrantApplication context) {
        System.out.println("Sorry. You cannot consider this application  №" + context.getId() +
                ". It is already under consideration");
    }

    @Override
    public void confirm(GrantApplication context) {
        System.out.println("Application №" + context.getId() + " has been confirmed. " +
                "Congratulations!");
        context.setState(new ConfirmedGrantState());
    }

    @Override
    public void decline(GrantApplication context) {
        System.out.println("Application №" + context.getId() + " has been declined. " +
                "You may try next time.");
        context.setState(new DeclinedGrantState());
    }

    @Override
    public void suspend(GrantApplication context) {
        System.out.println("Application №" + context.getId() + " has been suspended. " +
                "It means you still have the chance. Just wait.");
        context.setState(new SuspendedGrantState());
    }
}
