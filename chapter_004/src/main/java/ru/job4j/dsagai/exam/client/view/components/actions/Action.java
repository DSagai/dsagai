package ru.job4j.dsagai.exam.client.view.components.actions;

/**
 * Interface for objects in actions queue
 *
 * @author dsagai
 * @version 1.00
 * @since 15.03.2017
 */
public interface Action {
    /**
     *
     * @throws InterruptedException
     */
    void doAction() throws InterruptedException;
}
