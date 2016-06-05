package com.daniilyurov.training.patterns.state.educationalgrant;

import com.daniilyurov.training.patterns.state.educationalgrant.grant_states.CreatedGrantState;

/**
 * Context
 */
public class GrantApplication {

    private GrantState state;
    private int id;

    public GrantApplication(int id) {
        this.state = new CreatedGrantState();
        this.id = id;
        System.out.println("Application №" + id + " has been created.");
    }

    public void cancel() {
        state.cancel(this);
    }

    public void consider() {
        state.consider(this);
    }

    public void confirm() {
        state.confirm(this);
    }

    public void decline() {
        state.decline(this);
    }

    public void suspend() {
        state.suspend(this);
    }

    public GrantState getState() {
        return state;
    }

    public void setState(GrantState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }
}
