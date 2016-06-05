package com.daniilyurov.training.patterns.state.educationalgrant.grant_states;

import com.daniilyurov.training.patterns.state.educationalgrant.GrantApplication;
import com.daniilyurov.training.patterns.state.educationalgrant.GrantState;

/**
 * Concrete State
 */
public class DeclinedGrantState implements GrantState {
    @Override
    public void cancel(GrantApplication context) {
        System.out.println("Sorry. You cannot cancel application №" + context.getId() +
                " as you have already received a decline!");
    }

    @Override
    public void consider(GrantApplication context) {
        System.out.println("Sorry. You cannot consider application №" + context.getId() +
                " as it is declined.");
    }

    @Override
    public void confirm(GrantApplication context) {
        System.out.println("Sorry. You cannot confirm application №" + context.getId() +
                " as it has been declined.");
    }

    @Override
    public void decline(GrantApplication context) {
        System.out.println("Sorry. You cannot decline application №" + context.getId() +
                " as it has been declined.");
    }

    @Override
    public void suspend(GrantApplication context) {
        System.out.println("Sorry. You cannot suspend application №" + context.getId() +
                " as it has been declined.");
    }
}
