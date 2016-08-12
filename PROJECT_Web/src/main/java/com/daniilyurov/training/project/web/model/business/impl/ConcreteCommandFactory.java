package com.daniilyurov.training.project.web.model.business.impl;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import com.daniilyurov.training.project.web.model.business.impl.tool.RepositoryTool;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class ConcreteCommandFactory implements CommandFactory {

    static Logger logger = Logger.getLogger(ConcreteCommandFactory.class);

    // Dependencies to provide
    private Localizer localizer;
    private RepositoryManagerFactory repositoryManagerFactory;
    private OutputToolFactory outputToolFactory;
    private ServicesFactory servicesFactory;
    private ValidatorFactory validatorFactory;


    private final Map<String, Command> commandCache;
    private final Properties commandClassMapping;

    public void setupDependenciesForInjection() {
        RepositoryTool repositoryTool = new RepositoryTool(repositoryManagerFactory);
        this.outputToolFactory = new OutputToolFactory(localizer);
        this.servicesFactory = new ServicesFactory(repositoryTool);
        this.validatorFactory = new ValidatorFactory(servicesFactory, outputToolFactory);
    }

    public ConcreteCommandFactory(Properties commandClassMapping, Localizer localizer,
                                  RepositoryManagerFactory repositoryManagerFactory) {

        this.localizer = localizer;
        this.repositoryManagerFactory = repositoryManagerFactory;
        setupDependenciesForInjection();

        if (commandClassMapping == null) throw new NullPointerException();
        this.commandCache = new HashMap<>();
        this.commandClassMapping = commandClassMapping;

        logger.debug("  Initiating CommandFactory and creating empty command.");

        // create empty command
        String emptyCommandClassName = commandClassMapping.getProperty(Key.EMPTY);
        if (emptyCommandClassName == null) throw new IllegalStateException("Unable to locate empty command!");

        Command emptyCommand = instantiateCommandClass(emptyCommandClassName);
        this.commandCache.put(Key.EMPTY, emptyCommand);
    }

    @Override
    public Command defineCommand(String requestKey) {

        logger.debug("Trying to find command corresponding to request key.");

        // check if requestKey is null
        if (requestKey == null) return commandCache.get(Key.EMPTY);

        // check if the command responsible for processing requestKey is in cache
        Command command = commandCache.get(requestKey);

        // if command is not in cache
        if (command == null) {

            logger.debug("Command not found in cache. So trying to initialize it via reflection.");

            // check if such requestKey is mapped to command class
            String className = commandClassMapping.getProperty(requestKey);
            if (className == null) {
                logger.debug("No mapping for such requestKey is found.");
                return commandCache.get(Key.EMPTY);
            }

            synchronized (commandCache) {
                if (commandCache.get(requestKey) == null) {
                    // dynamically instantiate class mapped to the command
                    command = instantiateCommandClass(className);
                    commandCache.put(requestKey, command);
                }
            }

            command = commandCache.get(requestKey);
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
            Command command = (Command) constructor.newInstance();
            provideDependencies(aClass, command);
            return command;
        } catch (Exception e) {
            throw new IllegalStateException("Unable to instantiate " + className, e);
        }
    }

    // locates dependencies needed by Command and injects them into the instance.
    private void provideDependencies(Class aClass, Command instance) throws Exception {
        for (Method method : aClass.getMethods()) {
            if (method.isAnnotationPresent(Provided.class)) {
                List<Object> arguments = new ArrayList<>();
                for (Class type : method.getParameterTypes()) {
                    if (type.equals(OutputToolFactory.class)) {
                        arguments.add(outputToolFactory);
                    }
                    else if (type.equals(ServicesFactory.class)) {
                        arguments.add(servicesFactory);
                    }
                    else if (type.equals(ValidatorFactory.class)) {
                        arguments.add(validatorFactory);
                    }
                    else {
                        arguments.add(null);
                    }
                }
                method.invoke(instance, arguments.toArray());
            }
        }
    }

}
