package ru.job4j.dsagai.exam.client.view.components.actions;

import ru.job4j.dsagai.exam.client.view.ConsoleView;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
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
    public void doAction() throws InterruptedException {
        String response = this.view.takeConsoleResponse();
        this.consumer.feed(response);
    }
}
