package ru.job4j.dsagai.exam.client.view.components.actions;

import ru.job4j.dsagai.exam.client.view.ConsoleView;

/**
 * Action, which reads text from the console
 * to the TextConsumer.
 *
 * @author dsagai
 * @version 1.00
 * @since 15.03.2017
 */
public class ActionReadConsole implements Action {
    private final TextConsumer consumer;
    private final ConsoleView view;

    public ActionReadConsole(TextConsumer consumer, ConsoleView view) {
        this.consumer = consumer;
        this.view = view;
    }

    @Override
    /**
     * Taking an action.
     */
    public void doAction() throws InterruptedException {
        String response = this.view.takeConsoleResponse();
        this.consumer.feed(response);
    }
}
