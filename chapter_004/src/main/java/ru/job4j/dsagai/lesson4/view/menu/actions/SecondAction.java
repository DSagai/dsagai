package ru.job4j.dsagai.lesson4.view.menu.actions;

import ru.job4j.dsagai.lesson4.controller.Controller;

/**
 * Fake second action
 *
 * @author dsagai
 * @version 1.00
 * @since 19.01.2017
 */

public class SecondAction implements MenuAction {
    private final Controller controller;

    /**
     * default constructor
     * @param controller
     */
    public SecondAction(Controller controller) {
        this.controller = controller;
    }

    @Override
    /**
     * execute second action.
     */
    public void execute() {
        this.controller.action2();
    }
}
