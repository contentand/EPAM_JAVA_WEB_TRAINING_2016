package com.daniilyurov.training.project.web.utility;

/**
 * Summarises enumeration of all keys used in Context Container
 * across the application.
 */
public interface ContextAttributes {
    String REPOSITORY_MANAGER_FACTORY = "repositoryManagerFactory";
    String SERVICES = "services";
    String ACTION_COMMAND_FACTORY = "actionCommandFactory";
    String JSP_MAPPING = "jspMapping";
    String URL_MAPPING = "urlMapping";
    String LOCALIZER = "localizer";
}
