package com.daniilyurov.training.project.web.setup;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listens to the context initialization and calls context configurator
 * upon launch.
 */
@WebListener
public class ContextInitializationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        ContextConfigurator contextConfigurator = new ContextConfigurator();
        contextConfigurator.configure(context);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No action
    }
}
