package com.daniilyurov.training.project.web.controller;

import com.daniilyurov.training.project.web.model.business.api.Command;
import com.daniilyurov.training.project.web.model.business.api.CommandFactory;
import com.daniilyurov.training.project.web.model.business.api.Request;
import com.daniilyurov.training.project.web.utility.SessionAttributes;
import org.apache.log4j.Logger;

import static com.daniilyurov.training.project.web.utility.ContextAttributes.*;
import static com.daniilyurov.training.project.web.utility.RequestParameters.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * Front Controller.
 * Accepts all requests to dynamic resources.
 * Addresses business logic for response resolution.
 * Dispatches the response appropriately (via redirect or forward to jsp page).
 *
 * @author Daniil Yurov
 */
public class FrontController extends HttpServlet {

    private static Logger logger = Logger.getLogger(FrontController.class);

    @Override
    public String getServletInfo() {
        return "Front Controller, version: 1.0, author: Daniil Yurov (c) 2016";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get request key from the Url
        String requestKey = new RequestKeyMapper().getRequestKey(request);

        // Get command corresponding to the request key
        ServletContext context = request.getServletContext();
        CommandFactory factory = (CommandFactory) context.getAttribute(ACTION_COMMAND_FACTORY);
        Command command = factory.defineCommand(requestKey);

        // Get response key from the command
        Request wrappedRequest = new HttpServletRequestWrapper(request);
        String responseKey = command.execute(wrappedRequest);

        // Dispatch to the final destination
        new Dispatcher(request,response,requestKey,responseKey).dispatch();
    }


    /**
     * A utility class that contains fields and methods
     * shared by RequestKeyMapper and Dispatcher.
     * The sole purpose is to avoid code repetition.
     *
     * @author Daniil Yurov
     */
    private static class MappingProvider {
        private ServletContext context;

        MappingProvider(HttpServletRequest request) {
            this.context = request.getServletContext();
        }

        public Properties getUrlMapping() {
            return (Properties) context.getAttribute(URL_MAPPING);
        }

        public Properties getJspMapping() {
            return (Properties) context.getAttribute(JSP_MAPPING);
        }
    }

    /**
     * The class allows to get user's request key.
     * Request key is a string that contains the name of concrete command the user wants to call.
     *
     * @author Daniil Yurov
     */
    private static final class RequestKeyMapper {

        private MappingProvider provider;
        private String method;
        private String path;

        /**
         * Analyzes request method and url, and then constructs urlPattern.
         * It tries to map the urlPattern to Request key string using UrlMapping stored in Context Container.
         * If mapping failed, a null is returned.
         *
         * @param request user's request instance to analyze
         * @return a request key or null if the key is not recognized
         */
        String getRequestKey(HttpServletRequest request) {
            this.provider = new MappingProvider(request);
            this.method = request.getMethod();
            this.path = request.getPathInfo();

            String urlPattern = formUrlPattern();
            return getRequestKey(urlPattern);
        }

        // Private helper methods are listed below

        // locates what constant corresponds to the url pattern using url-mapping file
        private String getRequestKey(String urlPattern) {
            Properties urlMapping = provider.getUrlMapping();
            return urlMapping.getProperty(urlPattern);
        }

        // For example: method:GET, path:/faculty/13 -> urlPattern:GET/faculty/{id}
        private String formUrlPattern() {
            path = replaceNumericValuesWithIdTag(path);
            path = removeLastForwardSlashIfPresentAndNotOnly(path);
            return "[" + method.toUpperCase() + "]" + path;
        }

        // For example: /faculty/213/selection/ -> /faculty/{id}/selection/
        private String replaceNumericValuesWithIdTag(String path) {
            String NUMERIC_VALUES = "\\d+", ID_TAG = "{id}";
            return path.replaceAll(NUMERIC_VALUES, ID_TAG);
        }

        // For example: /faculty/{id}/selection/ -> /faculty/{id}/selection
        // IT SHOULD NOT TOUCH A SINGLE / though. Example: / -> /
        private String removeLastForwardSlashIfPresentAndNotOnly(String path) {
            String LAST_FORWARD_SLASH = "/$", NOTHING = "";
            return path.length() > 1 ? path.replaceAll(LAST_FORWARD_SLASH, NOTHING) : path;
        }
    }

    /**
     * The purpose of the class is to encapsulate method
     * that determines whether there should be forward or redirect.
     * It identifies which jsp to forward to or which url
     * to redirect to. It eventually performs the forward/redirect.
     *
     * @author Daniil Yurov
     */
    private static final class Dispatcher {

        private MappingProvider provider;
        private HttpServletRequest request;
        private HttpServletResponse response;
        private String requestedView;
        private String responseView;
        private String destination;

        Dispatcher(HttpServletRequest request, HttpServletResponse response,
                   String requestedView, String responseView) {
            this.provider = new MappingProvider(request);
            this.request = request;
            this.response = response;
            this.requestedView = requestedView;
            this.responseView = responseView;
        }

        /**
         * Perform forward/redirect.
         *
         * Note: All requests sent via POST/PUT/DELETE methods should
         * obligatorily supply "afterProcessDestinationPath" parameter.
         * If such is not passed, an illegal state exception is thrown.
         *
         * @throws IOException if it fails to forward, redirect; or read url/jsp-mapping file
         * @throws ServletException if it fails to forward;
         * @throws IllegalStateException if afterProcessDestinationPath parameter is not sent via
         *                                  POST/PUT/DELETE request;
         *                               if it failed to map responseView to corresponding url using
         *                                  url-mapping file
         */
        void dispatch() throws IOException, ServletException {
            if (responseView != null && responseView.equals(requestedView)) {
                forwardToAppropriateJsp();
            } else {
                redirectToAppropriatePath();
            }
        }

        // private helper methods are listed below

        private void forwardToAppropriateJsp() throws IOException, ServletException {
            Properties jspMapping = provider.getJspMapping();
            String viewPath = jspMapping.getProperty(responseView);

            request.getRequestDispatcher(viewPath).forward(request, response);
        }

        private void redirectToAppropriatePath() throws IOException {
            persistUserParametersInSession(); // useful for input tags so that the user does not retype text.
            if (responseView == null) {
                takePathFromRequestParameter();
            } else {
                mapResponseToPathUsingUrlMapping();
            }
            response.sendRedirect(destination);
        }

        private void takePathFromRequestParameter() {
            String destinationPath = request.getParameter(PARAMETER_AFTER_PROCESS_DESTINATION_PATH);
            Optional<String> parameter = Optional.ofNullable(destinationPath);
            destination = parameter.orElseThrow(IllegalStateException::new);
        }

        // Moves user parameters into session container so that they are available for jsp after redirect.
        // These attributes are then removed from session container by AttributeKillerFilter once jsp is rendered.
        private void persistUserParametersInSession() {
            request.getParameterMap().forEach((key, value) -> {
                if (SessionAttributes.CORE_SESSION_ATTRIBUTES.contains(key)) {
                    logger.error("Core attribute " + key + "has been used as parameter");
                    return;
                }
                String input = value.length > 0 ? value[0] : "";
                request.getSession().setAttribute(key, input);
            });
        }

        // finds urlPattern matching response view in the UrlMapping
        // and transforms it into normal url
        private void mapResponseToPathUsingUrlMapping() {
            Properties urlMapping = provider.getUrlMapping();
            String urlPattern = (String) urlMapping.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(responseView))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new)
                    .getKey();
            destination = removeMethodFromPattern(urlPattern);
        }

        // For example: [GET]/login -> /login
        private String removeMethodFromPattern(String urlPatten) {
            String result = request.getContextPath();
            result += urlPatten.replaceAll("\\[(.*?)\\]", "");
            return result;
        }
    }
}
