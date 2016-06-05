package com.daniilyurov.training.patterns.state.educationalgrant.grant_states;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import com.daniilyurov.training.patterns.state.educationalgrant.GrantState;

/**
 * Concrete State
 */
public class ConfirmedGrantState implements GrantState {
    @Override
    public void cancel(GrantApplication context) {
        System.out.println("Sorry. Not possible to cancel application №" + context.getId() + ". " +
                "You have already received the grant!");
    }

    @Override
    public void consider(GrantApplication context) {
        System.out.println("Sorry. Not possible to consider application №" + context.getId() + ". " +
                "The applicant has already received the grant!");
    }

    @Override
    public void confirm(GrantApplication context) {
        System.out.println("Sorry. The applicant has already received the grant №" + context.getId() + "!");
    }

    @Override
    public void decline(GrantApplication context) {
        System.out.println("Sorry. Not possible to decline application №" + context.getId() + ". " +
                "The applicant has already received the grant!");
    }

    @Override
    public void suspend(GrantApplication context) {
        System.out.println("Sorry. Not possible to suspend application №" + context.getId() + ". " +
                "The applicant has already received the grant!");
    }
}
