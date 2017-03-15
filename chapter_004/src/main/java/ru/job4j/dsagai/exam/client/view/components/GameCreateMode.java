package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 18.02.2017
 */
public class GameCreateMode extends Screen {
    private final boolean player;

    public GameCreateMode(Controller controller, ConsoleView view, boolean player) {
        super(controller, view);
        this.player = player;
    }

    @Override
    public void refresh() {

    }
}
