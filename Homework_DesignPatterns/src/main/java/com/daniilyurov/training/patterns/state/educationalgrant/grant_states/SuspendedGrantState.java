package com.daniilyurov.training.patterns.state.educationalgrant.grant_states;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import com.daniilyurov.training.patterns.state.educationalgrant.GrantState;

/**
 * Concrete State
 */
public class SuspendedGrantState implements GrantState {
    @Override
    public void cancel(GrantApplication context) {
        System.out.println("Grant application №" + context.getId() + " is now cancelled. " +
                "Maybe its right. It has been suspended for too long.");
        context.setState(new CancelledGrantState());
    }

    @Override
    public void consider(GrantApplication context) {
        System.out.println("The suspended application №" + context.getId() +
                " is now under consideration again.");
        context.setState(new UnderConsiderationGrantState());
    }

    @Override
    public void confirm(GrantApplication context) {
        System.out.println("Sorry. You cannot confirm suspended application №" + context.getId() +
                " without prior consideration.");
    }

    @Override
    public void decline(GrantApplication context) {
        System.out.println("Sorry. You cannot decline suspended application №" + context.getId() +
                " without prior consideration.");
    }

    @Override
    public void suspend(GrantApplication context) {
        System.out.println("Sorry. You cannot suspend application №" + context.getId() +
                " as it has already been suspended.");
    }
}
