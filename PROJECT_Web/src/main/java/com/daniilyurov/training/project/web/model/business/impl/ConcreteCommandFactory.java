package com.daniilyurov.training.project.web.model.business.impl;

import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConcreteCommandFactory implements CommandFactory {

    static Logger logger = Logger.getLogger(ConcreteCommandFactory.class);

    private final Map<String, Command> commandCache;
    private final Properties commandClassMapping;

    public ConcreteCommandFactory(Properties commandClassMapping) {
        if (commandClassMapping == null) throw new NullPointerException();
        this.commandCache = new HashMap<>();
        this.commandClassMapping = commandClassMapping;

        logger.debug("  Initiating CommandFactory and creating empty command.");

        // create empty command
        String emptyCommandClassName = commandClassMapping.getProperty(Intent.EMPTY);
        if (emptyCommandClassName == null) throw new IllegalStateException("Unable to locate empty command!");

        Command emptyCommand = instantiateCommandClass(emptyCommandClassName);
        this.commandCache.put(Intent.EMPTY, emptyCommand);
    }

    @Override
    public Command defineCommand(String intent) {

        logger.debug("Trying to find command corresponding to Intent.");

        // check if intent is null
        if (intent == null) return commandCache.get(Intent.EMPTY);

        // check if the command responsible for processing intent is in cache
        Command command = commandCache.get(intent);

        // if command is not in cache
        if (command == null) {

            logger.debug("Command not found in cache. So trying to initialize it via reflection.");

            // check if such intent is mapped to command class
            String className = commandClassMapping.getProperty(intent);
            if (className == null) {
                logger.debug("No mapping for such intent is found.");
                return commandCache.get(Intent.EMPTY);
            }

            synchronized (commandCache) {
                if (commandCache.get(intent) == null) {
                    // dynamically instantiate class mapped to the command
                    command = instantiateCommandClass(className);
                    commandCache.put(intent, command);
                }
            }

            command = commandCache.get(intent);
        }

        logger.debug("Returning command from cache.");
        return command;
    }

    private Command instantiateCommandClass(String className) {

        logger.debug("Found mapped class name and instantiating command via reflection.");

        ClassLoader classLoader = ConcreteCommandFactory.class.getClassLoader();
        try {
            Class aClass = classLoader.loadClass(className);
            //noinspection unchecked
            Constructor constructor = aClass.getConstructor();
            return (Command) constructor.newInstance();
        } catch (ClassCastException | ClassNotFoundException | NoSuchMethodException
                                    | IllegalAccessException | InstantiationException
                                    | InvocationTargetException e) {

            throw new IllegalStateException("Unable to instantiate " + className, e);
        }
    }
}
