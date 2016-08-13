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

/**
 * <p>CommandFactory implementation.
 * To instantiate the class you need to provide it with application scoped dependencies:
 * commandClassMapping, localizer and repositoryManagerFactory.</p>
 *
 * <p>Using the provided dependencies, the factory generates other application scoped
 * dependencies that will be used by commands:
 * OutputToolFactory, ServicesFactory, ValidatorFactory</p>
 *
 * <p>The class stores commands in a map. At the beginning the map is empty.
 * Upon construction the class tries to locate and instantiate EMPTY command.
 * If it fails to do so, an IllegalStateException is thrown.</p>
 *
 * <p>Once the class is instantiated, it is ready to accept requests via
 * defineCommand(String requestKey) method.</p>
 *
 * <p>It takes the requestKey and then follows the steps:
 * 1. Checks if there is a command corresponding to such key in the map.
 * 2. If not, it tries to find if there is a class name mapped to such key.
 * 3. If there is, it tries to instantiate it using reflection.
 * 4. If it succeeds, it checks to see if there are @Provided-annotated methods and
 *    injects required dependencies into the command instance.
 * 5. If it succeeds, it puts the command into the commandCache map.
 * 6. Returns the initiated command.
 *
 * <p>If it fails to find corresponding command, an empty command is returned.
 * If it fails to instantiate a command via reflection, an IllegalStateException is thrown.</p>
 *
 * @author Daniil Yurov
 */
public class ConcreteCommandFactory implements CommandFactory {

    static Logger logger = Logger.getLogger(ConcreteCommandFactory.class);

    // Possible dependencies to provide to commands
    private Localizer localizer;
    private RepositoryManagerFactory repositoryManagerFactory;
    private OutputToolFactory outputToolFactory;
    private ServicesFactory servicesFactory;
    private ValidatorFactory validatorFactory;

    // Map to store initiated Commands
    private final Map<String, Command> commandCache;

    // Property to locate class name using the request key
    private final Properties commandClassMapping;

    public ConcreteCommandFactory(Properties commandClassMapping, Localizer localizer,
                                  RepositoryManagerFactory repositoryManagerFactory) {

        if (commandClassMapping == null
                || localizer == null
                || repositoryManagerFactory == null) {
            throw new NullPointerException();
        }

        this.localizer = localizer;
        this.repositoryManagerFactory = repositoryManagerFactory;
        setupAdditionalDependenciesForInjection();
        this.commandCache = new HashMap<>();
        this.commandClassMapping = commandClassMapping;

        logger.debug("  Initiating CommandFactory and creating empty command.");

        // create empty command
        String emptyCommandClassName = commandClassMapping.getProperty(Key.EMPTY);
        if (emptyCommandClassName == null) throw new IllegalStateException("Unable to locate empty command!");

        Command emptyCommand = instantiateCommandClass(emptyCommandClassName);
        this.commandCache.put(Key.EMPTY, emptyCommand);
    }

    // constructs additional application-scoped dependencies that might be needed by commands
    private void setupAdditionalDependenciesForInjection() {
        RepositoryTool repositoryTool = new RepositoryTool(repositoryManagerFactory);
        this.outputToolFactory = new OutputToolFactory(localizer);
        this.servicesFactory = new ServicesFactory(repositoryTool);
        this.validatorFactory = new ValidatorFactory(servicesFactory, outputToolFactory);
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

            logger.debug("Command not found in cache. So, we are trying " +
                    "to initialize it via reflection.");

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

    // locates class, instantiates it and provides with requested dependencies
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
            logger.error("Failed to instantiate command class", e);
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
                        logger.error("Unsupported dependency is requested : " + type.getName() +
                                "\nClass name: " + aClass.getName() +
                                ", method name: " + method.getName());
                        arguments.add(null); // we do not throw Exception but just set null
                    }
                }
                method.invoke(instance, arguments.toArray());
            }
        }
    }

}
