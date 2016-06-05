package com.daniilyurov.training.patterns.state.educationalgrant;

/**
 * State
 */
public interface GrantState {
    void cancel(GrantApplication context);
    void consider(GrantApplication context);
    void confirm(GrantApplication context);
    void decline(GrantApplication context);
    void suspend(GrantApplication context);
}
