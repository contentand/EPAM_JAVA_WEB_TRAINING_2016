package com.daniilyurov.training.project.web.model.business.impl;

import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.model.business.impl.service.ServicesFactory;
import com.daniilyurov.training.project.web.model.business.impl.tool.OutputToolFactory;
import com.daniilyurov.training.project.web.model.business.impl.validator.ValidatorFactory;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Properties;

import static org.junit.Assert.*;


public class ConcreteCommandFactoryTest extends Mockito {

    private Localizer localizer;
    private Properties commandMapping;
    private RepositoryManagerFactory repositoryManagerFactory;
    private CommandFactory commandFactory;

    @Before
    public void setup() {
        localizer = mock(Localizer.class);
        repositoryManagerFactory = mock(RepositoryManagerFactory.class);
        commandMapping = new Properties(){{
            setProperty(Key.EMPTY, ValidEmptyCommand.class.getName());
            setProperty("newValidCommand", NewValidCommand.class.getName());
            setProperty("dependencyCommand", DependencyNeededCommand.class.getName());
        }};
        commandFactory = new ConcreteCommandFactory(commandMapping,
                localizer, repositoryManagerFactory);
    }

    @Test(expected = NullPointerException.class)
    public void nullCommandClassMappingPassedToConstructor_throwsNullPointerException() throws Exception {
        // execute
        new ConcreteCommandFactory(null, localizer, repositoryManagerFactory);
    }

    @Test(expected = NullPointerException.class)
    public void nullLocalizerPassedToConstructor_throwsIllegalStateException() throws Exception {
        // execute
        new ConcreteCommandFactory(commandMapping, null, repositoryManagerFactory);
    }

    @Test(expected = NullPointerException.class)
    public void nullRepositoryManagerFactoryPassedToConstructor_throwsNullPointerException() throws Exception {
        // execute
        new ConcreteCommandFactory(commandMapping, localizer, null);
    }

    @Test(expected = IllegalStateException.class)
    public void commandMappingPassedToConstructorHasNoEmptyCommand_throwsIllegalStateException() throws Exception {
        // execute
        new ConcreteCommandFactory(new Properties(),
                localizer, repositoryManagerFactory);
    }

    @Test(expected = IllegalStateException.class)
    public void invalidCommandMapped_throwsIllegalStateException() throws Exception {
        // setup
        Properties mappingWithInvalidCommand = new Properties() {{
            setProperty(Key.EMPTY, InvalidCommand.class.getName());
        }};

        // execute
        new ConcreteCommandFactory(mappingWithInvalidCommand,
                localizer, repositoryManagerFactory);
    }

    @Test
    public void nullRequestKey_returnsEmptyCommand() throws Exception {
        // execute
        Command command = commandFactory.defineCommand(null);

        // verify
        assertTrue(command instanceof ValidEmptyCommand);
    }

    @Test
    public void unmappedRequestKey_returnsEmptyCommand() throws Exception {
        // setup
        String unmappedKey = "blah-blah-blah";

        // execute
        Command command = commandFactory.defineCommand(unmappedKey);

        // verify
        assertTrue(command instanceof ValidEmptyCommand);
    }

    @Test
    public void brandNewValidRequestKey_loadsMappedCommandViaReflection() throws Exception {
        // execute
        Command command = commandFactory.defineCommand("newValidCommand");

        // verify
        assertTrue(command instanceof NewValidCommand);
    }

    @Test // dependencies are injected by identifying @Provided annotated method and its params
    public void mappedClassRequiresDependencies_injectsDependencies() throws Exception {
        // execute
        Command command = commandFactory.defineCommand("dependencyCommand");

        // verify
        assertTrue(command instanceof DependencyNeededCommand);
        DependencyNeededCommand com = (DependencyNeededCommand) command;
        assertTrue(com.areAllDependenciesSet());
    }

    // dummy class to be instantiated via reflection
    static class ValidEmptyCommand implements Command {

        public ValidEmptyCommand() {}

        @Override
        public String execute(Request request) {
            return "empty";
        }
    }
    // dummy class to be instantiated via reflection
    static class NewValidCommand implements Command {

        public NewValidCommand() {}

        @Override
        public String execute(Request request) {
            return "newValidCommand";
        }
    }

    // dummy class to be instantiated via reflection
    static class DependencyNeededCommand implements Command {

        private String notDependency;
        private ValidatorFactory validatorFactory;
        private ServicesFactory servicesFactory;
        private OutputToolFactory outputToolFactory;

        @Provided
        public void setValidatorFactory(ValidatorFactory validatorFactory, String notDependency) {
            this.validatorFactory = validatorFactory;
            this.notDependency = notDependency;
        }

        @Provided
        public void setDependencies(ServicesFactory servicesFactory, OutputToolFactory outputToolFactory) {
            this.servicesFactory = servicesFactory;
            this.outputToolFactory = outputToolFactory;
        }

        public DependencyNeededCommand() {}

        @Override
        public String execute(Request request) {
            return null;
        }

        // method for the test to determine whether all the dependencies are set
        public boolean areAllDependenciesSet() {
            return validatorFactory != null
                    && servicesFactory != null
                    && outputToolFactory != null
                    && notDependency == null; // This should be null as it is not a supported dependency
        }
    }


    // dummy class to be instantiated via reflection
    static class InvalidCommand {
        public InvalidCommand() {}
    }
}