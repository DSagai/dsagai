package ru.job4j.dsagai.lesson4.view.menu.actions;

import ru.job4j.dsagai.lesson4.controller.Controller;

/**
 * Fake first action
 *
 * @author dsagai
 * @version 1.00
 * @since 19.01.2017
 */

public class FirstAction implements MenuAction {
    private final Controller controller;

    /**
     * default constructor
     * @param controller
     */
    public FirstAction(Controller controller) {
        this.controller = controller;
    }

    @Override
    /**
     * execute first action.
     */
    public void execute() {
        this.controller.action1();
    }
}
