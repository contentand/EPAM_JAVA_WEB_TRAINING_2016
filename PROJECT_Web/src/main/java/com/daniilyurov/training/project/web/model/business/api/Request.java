package com.daniilyurov.training.project.web.model.business.api;

/**
 * This interface provides basic request wrapping.
 * It has basic methods needed by business logic.
 * This abstraction makes business logic independent from request type.
 */
public interface Request {

    /**
     * This methods returns all values of particular parameter.
     * @param key parameter name
     * @return an array of parameters or null if absent.
     */
    String[] getParameterValues(String key);

    /**
     * This method sets particular object into session container.
     * @param key attribute name
     * @param value attribute value
     */
    void setSessionAttribute(String key, Object value);

    /**
     * This method return attribute value contained within session
     * container bearing the name key.
     * @param key attribute name
     * @return attribute value under attribute name or null if no found
     */
    Object getSessionAttribute(String key);

    /**
     * Get url path.
     * @return url path to requested resource.
     */
    String getUrlPath();


}
