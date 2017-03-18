package ru.job4j.dsagai.exam.client.view.components.actions;

/**
 * Interface for use in ActionReadConsole.
 * Text info is red into TextConsumer to feed method.
 *
 * @author dsagai
 * @version 1.00
 * @since 15.03.2017
 */
public interface TextConsumer {
    /**
     * Consumes text information.
     * @param text String.
     */
    void feed(String text);
}
