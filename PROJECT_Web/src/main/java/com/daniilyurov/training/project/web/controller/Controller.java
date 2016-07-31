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
public class Controller extends HttpServlet {

    static Logger logger = Logger.getLogger(Controller.class);

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

        String requestedView = new IntentParser().getIntent(request);
        logger.debug("Parsed client intent : " + requestedView);

        ServletContext application = request.getServletContext();
        CommandFactory factory = (CommandFactory) application.getAttribute(ACTION_COMMAND_FACTORY);

        Command command = factory.defineCommand(requestedView);

        Request wrappedRequest = new HttpServletRequestWrapper(request);
        String responseView = command.execute(wrappedRequest);
        logger.debug("Executed command.");
        logger.debug("Command returned intent : " + requestedView);

        new Dispatcher(request,response,requestedView,responseView).dispatch();
    }


    /**
     * A utility abstract class that contains fields and methods
     * shared by IntentParser and Dispatcher.
     * The sole purpose is to avoid code repetition.
     *
     * Declared abstract to prevent inadvertent instantiation.
     *
     * @author Daniil Yurov
     */
    private abstract class RequestHolder {
        protected HttpServletRequest request;

        protected Properties getUrlMapping() {
            return (Properties) request.getServletContext().getAttribute(URL_MAPPING);
        }

        protected Properties getJspMapping() {
            return (Properties) request.getServletContext().getAttribute(JSP_MAPPING);
        }
    }

    /**
     * The class allows to get user's Intent from request.
     * Intent is a string that contains the name of concrete command the user wants to call.
     *
     * @author Daniil Yurov
     */
    private final class IntentParser extends RequestHolder {

        private String method;
        private String path;

        /**
         * Analyzes request method and url, and then constructs urlPattern.
         * It tries to map the urlPattern to Intent string using UrlMapping stored in Context Container.
         * If mapping failed, a null is returned.
         *
         * @param request user's request instance to analyze
         * @return a string representing user's Intent or null if the Intent is not recognized
         */
        String getIntent(HttpServletRequest request) {
            this.request = request;
            this.method = request.getMethod();
            this.path = request.getPathInfo();

            String urlPattern = formUrlPattern();
            return getIntent(urlPattern);
        }

        // private helper methods are listed below

        // locates what constant corresponds to the url pattern using url-mapping file
        private String getIntent(String urlPattern) {
            Properties urlMapping = getUrlMapping();
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
     * to redirect to. And it performs the forward/redirect.
     *
     * @author Daniil Yurov
     */
    private final class Dispatcher extends RequestHolder{

        private HttpServletResponse response;
        private String requestedView;
        private String responseView;
        private String destination;

        Dispatcher(HttpServletRequest request, HttpServletResponse response,
                   String requestedView, String responseView) {
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
                logger.debug("Intents match! : " + requestedView + " == " + responseView);
                forwardToAppropriateJsp();
            } else {
                logger.debug("Intents do not match! : " + requestedView + " != " + responseView);
                redirectToAppropriatePath();
            }
        }

        // private helper methods are listed below

        private void forwardToAppropriateJsp() throws IOException, ServletException {
            Properties jspMapping = getJspMapping();
            String viewPath = jspMapping.getProperty(responseView);

            logger.debug("So forwarding to : " + viewPath);

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
            logger.debug("Redirecting to url user indicated in parameter");
            String destinationPath = request.getParameter(PARAMETER_AFTER_PROCESS_DESTINATION_PATH);
            Optional<String> parameter = Optional.ofNullable(destinationPath);
            destination = parameter.orElseThrow(IllegalStateException::new);
        }

        // Moves user parameters into session container so that they are available for jsp after redirect.
        // These attributes are then removed from session container by AttributeKillerFilter once jsp is rendered.
        private void persistUserParametersInSession() {
            request.getParameterMap().forEach((key, value) -> {
                if (SessionAttributes.CORE_SESSION_ATTRIBUTES.contains(key)) {
                    throw new IllegalStateException();
                }
                String input = value.length > 0 ? value[0] : "";
                request.getSession().setAttribute(key, input);
            });
        }

        // finds urlPattern matching response view in the UrlMapping
        // and transforms it into normal url
        private void mapResponseToPathUsingUrlMapping() {
            Properties urlMapping = getUrlMapping();
            String urlPattern = (String) urlMapping.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(responseView))
                    .findFirst()
                    .orElseThrow(IllegalStateException::new)
                    .getKey();
            destination = removeMethodFromPattern(urlPattern);
            logger.debug("Redirecting to mapped destination -> " + destination);
        }

        // For example: [GET]/login -> /login
        private String removeMethodFromPattern(String urlPatten) {
            String result = request.getContextPath();
            result += urlPatten.replaceAll("\\[(.*?)\\]", "");
            return result;
        }
    }
}
