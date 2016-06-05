package com.daniilyurov.training.patterns.state.educationalgrant.grant_states;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import com.daniilyurov.training.patterns.state.educationalgrant.GrantState;

/**
 * Concrete State
 */
public class CreatedGrantState implements GrantState {
    @Override
    public void cancel(GrantApplication context) {
        System.out.println("Grant application №" + context.getId() + " cancelled.");
        context.setState(new CancelledGrantState());
    }

    @Override
    public void consider(GrantApplication context) {
        System.out.println("The application №" + context.getId() + " is now under consideration!");
        context.setState(new UnderConsiderationGrantState());
    }

    @Override
    public void confirm(GrantApplication context) {
        System.out.println("Sorry. You cannot confirm application №" + context.getId() +
                " without prior consideration!");
    }

    @Override
    public void decline(GrantApplication context) {
        System.out.println("Sorry. You cannot decline application №" + context.getId() +
                " without prior consideration!");
    }

    @Override
    public void suspend(GrantApplication context) {
        System.out.println("Sorry. You cannot suspend application №" + context.getId() +
                " without prior consideration!");
    }
}
