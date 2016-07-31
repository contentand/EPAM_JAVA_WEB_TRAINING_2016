package com.daniilyurov.training.project.web.setup;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class ContextReloader implements ContextReloaderMBean {

    ContextConfigurator setup;

    public ContextReloader(ContextConfigurator setup) {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();

        try {
            server.registerMBean(this, new ObjectName("configuration", "context", "reloader"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setup = setup;
    }

    @Override
    public void reload() {
        System.out.println("STARTING RELOADING...");
        setup.reConfigureCommandUrlJspMapping();
        System.out.println("...RELOADING FINISHED.");
    }
}
