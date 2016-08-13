package com.daniilyurov.training.project.web.model.business.api;

/**
 * Factory that provides the client with concrete instances of Command
 * depending on the String representing the key of the command passed.
 * The class implementation should use some commonly known Mapping to distinguish
 * which concrete command corresponds to the concrete String passed as argument.
 */
public interface CommandFactory {
    /**
     *
     * @param requestKey a String representation of key
     * @return concrete Command
     */
    Command defineCommand(String requestKey);
}
