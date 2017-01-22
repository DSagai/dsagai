package ru.job4j.dsagai.lesson4.view.menu.actions;

import ru.job4j.dsagai.lesson4.controller.Controller;

/**
 * Exit action
 *
 * @author dsagai
 * @version 1.00
 * @since 18.01.2017
 */

public class ExitAction implements MenuAction {
    private final Controller controller;

    /**
     * default constructor
     * @param controller
     */
    public ExitAction(Controller controller) {
        this.controller = controller;
    }

    @Override
    /**
     * executes exit action.
     */
    public void execute() {
        this.controller.terminate();
    }
}
