package ru.job4j.dsagai.lesson4.view.menu.actions;

import ru.job4j.dsagai.lesson4.controller.Controller;

/**
 * Enum contains available menu actions
 *
 * @author dsagai
 * @version 1.00
 * @since 19.01.2017
 */

public enum Actions {
    Empty(new EmptyAction()),
    FirstAction(new FirstAction(Controller.getInstance())),
    SecondAction(new SecondAction(Controller.getInstance())),
    ExitAction(new ExitAction(Controller.getInstance()));

    private final MenuAction action;

    Actions(MenuAction action) {
        this.action = action;
    }

    public MenuAction getAction() {
        return action;
    }
}
