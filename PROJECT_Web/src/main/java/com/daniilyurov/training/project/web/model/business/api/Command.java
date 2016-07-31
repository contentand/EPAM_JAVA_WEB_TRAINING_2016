package com.daniilyurov.training.project.web.model.business.api;

public interface Command {
    String execute(Request request);
}
