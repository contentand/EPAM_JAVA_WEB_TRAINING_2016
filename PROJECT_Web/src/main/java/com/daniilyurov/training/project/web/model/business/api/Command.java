package com.daniilyurov.training.project.web.model.business.api;

/**
 * Command provides method "execute" that represents encapsulation of a particular business logic.
 * Once called, "execute" analyzes the request, performs business logic and responds with
 * a String representing the key to the view.
 *
 * Note! Instances of the class are NOT to be stored for later execution!
 */
public interface Command {

    /**
     * Performs business logic, updates state of objects contained in the request passed,
     * responds with a String representing the key of the view to display after execution.
     *
     * @param request encapsulation of request, session attributes and application scoped dependencies.
     * @return String representing the key to the view according to the View Mapping.
     */
    String execute(Request request);
}
