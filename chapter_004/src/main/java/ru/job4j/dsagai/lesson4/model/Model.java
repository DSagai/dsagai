package ru.job4j.dsagai.lesson4.model;

/**
 * Mock Model interface.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.01.2017
 */

public interface Model {

    /**
     * Fake action.
     * @param param String.
     */
    void doAction(String param);

    /**
     * Method returns current state of the model.
     * @return String.
     */
    String getState();

}
