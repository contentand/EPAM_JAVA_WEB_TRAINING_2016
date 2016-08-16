package com.daniilyurov.training.project.web.setup;

import com.daniilyurov.training.project.web.i18n.ConcreteLocalizer;
import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import com.daniilyurov.training.project.web.model.business.impl.ConcreteCommandFactory;
import com.daniilyurov.training.project.web.model.dao.api.RepositoryManagerFactory;
import com.daniilyurov.training.project.web.model.dao.implementation.DaoImplementationFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import static com.daniilyurov.training.project.web.utility.ContextAttributes.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * ContextConfigurator designed to configure servlet context
 * upon initialization. It also provides a method to reinitialize
 * Command-Url-Jsp mapping.
 */
public class ContextConfigurator {

    static Logger logger = Logger.getLogger(ContextConfigurator.class);

    @SuppressWarnings("FieldCanBeLocal")
    private static String ACCESS_TYPE_INIT_PARAM_NAME_IN_WEB_XML = "access.type";
    @SuppressWarnings("FieldCanBeLocal")
    private static String BASE_NAME_INIT_PARAM_NAME_IN_WEB_XML = "base.name";
    private Localizer localizer;
    private ServletContext context;
    private RepositoryManagerFactory repositoryManagerFactory;

    /**
     * Initializes all dependencies and inserts them into
     * the Context Container.
     *
     * @param context context to configure.
     */
    public void configure(ServletContext context) {
        this.context = context;
        this.localizer = getLocalizer();
        this.repositoryManagerFactory = getRepositoryManagerFactory();

        // configuring logger
        new DOMConfigurator().doConfigure(context.getRealPath("/WEB-INF/log4j.xml"),
                LogManager.getLoggerRepository());

        logger.info("Initializing application!");
        logger.info("Setting up context-wide dependencies.");

        context.setAttribute(ACTION_COMMAND_FACTORY, getFreshCommandFactory());
        context.setAttribute(JSP_MAPPING, getJspMapping());
        context.setAttribute(URL_MAPPING, getUrlMapping());
        logger.info(". Mapping for Commands-Jsp-Urls has been setup.");

        context.setAttribute(LOCALIZER, localizer);

        // Setting up Context ReLoader to allow reconfiguration via JMX
        new ContextReloader(this);
        logger.info(". ContextReloader has been setup.");
        logger.info("Setup succeeded!");
    }

    /**
     * Method designed for ContextReloader to call to re-initiate Command-Url-Jsp mapping.
     */
    void reConfigureCommandUrlJspMapping() {
        logger.info("Reconfiguring context Command-Url-Jsp mapping.");
        context.setAttribute(ACTION_COMMAND_FACTORY, getFreshCommandFactory());
        context.setAttribute(JSP_MAPPING, getJspMapping());
        context.setAttribute(URL_MAPPING, getUrlMapping());
    }

    // Private helper methods are below ------------------------------------------------------------------------

    private Localizer getLocalizer() {
        logger.info(". Setting up Localizer.");
        String baseName = getBaseNameFromInitParams();
        return new ConcreteLocalizer(baseName);
    }

    private CommandFactory getFreshCommandFactory() {
        return new ConcreteCommandFactory(getCommandClassMapping(), localizer, repositoryManagerFactory);
    }

    private RepositoryManagerFactory getRepositoryManagerFactory() {
        logger.info(". Setting up RepositoryManagerFactory.");
        DataSource dataSource = getDataSource();
        String requestedAccessType = getRequestedAccessTypeStringFromInitParams();
        DaoImplementationFactory.Type type = parseAccessType(requestedAccessType);
        return DaoImplementationFactory.getRepositoryManagerFactory(type, dataSource);
    }

    private synchronized DataSource getDataSource() {
        Context environment;
        try {
            environment = (Context) (new InitialContext().lookup("java:comp/env"));
            return (DataSource) environment.lookup("jdbc/university");
        } catch (NamingException e) {
            logger.error("No data source found in JNDI", e);
            throw new IllegalStateException("No Data Source found in JNDI.");
        }
    }

    private DaoImplementationFactory.Type parseAccessType(String accessType) {
        DaoImplementationFactory.Type type;
        try {
            type = DaoImplementationFactory.Type.valueOf(accessType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong DAO access type has been indicated in web.xml. " +
                    accessType + " is not supported.");
        }
        return type;
    }

    private String getRequestedAccessTypeStringFromInitParams() {
        String accessType = context.getInitParameter(ACCESS_TYPE_INIT_PARAM_NAME_IN_WEB_XML);

        if (accessType == null) {
            throw new IllegalStateException("No DAO access type has been indicated in web.xml!");
        }
        return accessType;
    }

    private String getBaseNameFromInitParams() {
        String baseName = context.getInitParameter(BASE_NAME_INIT_PARAM_NAME_IN_WEB_XML);
        if (baseName == null) {
            throw new IllegalStateException("No internationalization basename has been indicated in web.xml!");
        }
        return baseName;
    }

    // load url mapping from file
    private Properties getUrlMapping() {
        String URL_MAPPING_LOCATION = "/WEB-INF/mapping/url-mapping.properties";
        return getProperties(URL_MAPPING_LOCATION);
    }

    private Properties getCommandClassMapping() {
        String COMMAND_CLASS_MAPPING_LOCATION = "/WEB-INF/mapping/class-mapping.properties";
        return getProperties(COMMAND_CLASS_MAPPING_LOCATION);
    }

    private Properties getJspMapping() {
        String JSP_MAPPING_LOCATION = "/WEB-INF/mapping/jsp-mapping.properties";
        return getProperties(JSP_MAPPING_LOCATION);
    }

    private Properties getProperties(String location) {
        try {
            Properties properties = new Properties();
            properties.load(context.getResourceAsStream(location));
            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to load " + location);
        }
    }

}
