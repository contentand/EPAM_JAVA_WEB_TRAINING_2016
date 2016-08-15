package com.daniilyurov.training.project.web.setup;

/**
 * JMX interface for reloading context
 */
public interface ContextReloaderMBean {

    /**
     * Reloads context.
     */
    @SuppressWarnings("unused")
    void reload();
}
