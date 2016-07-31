package com.daniilyurov.training.project.web.model.business.api;


public interface CommandFactory {
    Command defineCommand(String intent);
}
