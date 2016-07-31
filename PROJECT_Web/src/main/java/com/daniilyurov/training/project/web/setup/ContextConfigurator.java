package com.daniilyurov.training.project.web.setup;

import com.daniilyurov.training.project.web.i18n.ConcreteLocalizer;
import com.daniilyurov.training.project.web.i18n.Localizer;
import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import com.daniilyurov.training.project.web.model.business.api.Provider;
import com.daniilyurov.training.project.web.model.business.api.Service;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContextConfigurator {

    static Logger logger = Logger.getLogger(ContextConfigurator.class);

    @SuppressWarnings("FieldCanBeLocal")
    private String ACCESS_TYPE_INIT_PARAM_NAME_IN_WEB_XML = "access.type";
    @SuppressWarnings("FieldCanBeLocal")
    private String BASE_NAME_INIT_PARAM_NAME_IN_WEB_XML = "base.name";

    private ServletContext context;

    /**
     * Initializes all dependencies and inserts them into
     * the Context Container.
     *
     * @param context context to configure.
     */
    public void configure(ServletContext context) {
        this.context = context;
        new DOMConfigurator().doConfigure(context.getRealPath("/WEB-INF/log4j.xml"), LogManager.getLoggerRepository());

        logger.info("Initializing application!");
        logger.info("Setting up context-wide dependencies.");
        context.setAttribute(ACTION_COMMAND_FACTORY, getFreshCommandFactory());
        context.setAttribute(JSP_MAPPING, getJspMapping());
        context.setAttribute(URL_MAPPING, getUrlMapping());
        logger.info("1. Mapping for Commands-Jsp-Urls has been setup.");
        context.setAttribute(REPOSITORY_MANAGER_FACTORY, getRepositoryManagerFactory());
        logger.info("2. RepositoryManagerFactory has been setup.");
        context.setAttribute(SERVICES, getTypeSafeEmptyServicesMap());
        context.setAttribute(LOCALIZER, getLocalizer());
        logger.info("3. Localizer has been setup.");

        // Setting up Context ReLoader to allow reconfiguration via JMX
        ContextReloader reloader = new ContextReloader(this);
        logger.info("4. ContextReloader has been setup.");
        logger.info("Setup succeeded!");
    }

    // !This method is PUBLIC! ContextReloader can call it to re-initiate mapping.
    public void reConfigureCommandUrlJspMapping() {
        logger.info("Reconfiguring context Command-Url-Jsp mapping.");
        context.setAttribute(ACTION_COMMAND_FACTORY, getFreshCommandFactory());
        context.setAttribute(JSP_MAPPING, getJspMapping());
        context.setAttribute(URL_MAPPING, getUrlMapping());
    }

    /**
     * Since generics is lost once collection is placed into the Context Container,
     * we need to make sure the map does not accept invalid values.
     * Creates a map that throws exception if wrong type.
     *
     * @return
     */
    private Map<Provider.Services, Service> getTypeSafeEmptyServicesMap() {
        return Collections.checkedMap(new HashMap<>(), Provider.Services.class, Service.class);
    }

    private Localizer getLocalizer() {
        String baseName = getBaseNameFromInitParams();
        return new ConcreteLocalizer(baseName);
    }

    private CommandFactory getFreshCommandFactory() {
        return new ConcreteCommandFactory(getCommandClassMapping());
    }

    private RepositoryManagerFactory getRepositoryManagerFactory() {
        DataSource dataSource = getDataSource();
        String requestedAccessType = getRequestedAccessTypeStringFromInitParams();
        DaoImplementationFactory.Type type = parseAccessType(requestedAccessType);
        return DaoImplementationFactory.getRepositoryManagerFactory(type, dataSource);
    }

    private synchronized DataSource getDataSource() {
        Context environment = null;
        try {
            environment = (Context) (new InitialContext().lookup("java:comp/env"));
            return (DataSource) environment.lookup("jdbc/university");
        } catch (NamingException e) {
            // TODO log!
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
