package com.daniilyurov.training.patterns.state.educationalgrant.grant_states;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import com.daniilyurov.training.patterns.state.educationalgrant.GrantState;

/**
 * Concrete State
 */
public class CancelledGrantState implements GrantState {
    @Override
    public void cancel(GrantApplication context) {
        System.out.println("Sorry. The grant application №" + context.getId() + " has already been cancelled.");
    }

    @Override
    public void consider(GrantApplication context) {
        System.out.println("Sorry. Not possible to consider application №" + context.getId() + ". " +
                "The applicant has cancelled its application!");
    }

    @Override
    public void confirm(GrantApplication context) {
        System.out.println("Sorry. Not possible to confirm application №" + context.getId() + ". " +
                "The applicant has cancelled its application!");
    }

    @Override
    public void decline(GrantApplication context) {
        System.out.println("Sorry. Not possible to decline application №" + context.getId() + ". " +
                "The applicant has cancelled its application!");
    }

    @Override
    public void suspend(GrantApplication context) {
        System.out.println("Sorry. Not possible to suspend application №" + context.getId() + ". " +
                "The applicant has cancelled its application!");
    }
}
