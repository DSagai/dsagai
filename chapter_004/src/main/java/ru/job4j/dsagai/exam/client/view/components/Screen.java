package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;

/**
 * Console interface screen.
 * Provides specific interaction with user
 * through text console.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public abstract class Screen extends Thread {
    private final Controller controller;
    private final ConsoleView view;

    public Screen(Controller controller, ConsoleView view) {
        this.controller = controller;
        this.view = view;
    }

    protected Controller getController() {
        return controller;
    }

    protected ConsoleView getView() {
        return view;
    }

    public abstract void refresh();


    public void showMessage(String message) {
        System.out.println(message);
    }
}
