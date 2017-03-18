package ru.job4j.dsagai.exam.client.view.components;

import ru.job4j.dsagai.exam.client.Controller;
import ru.job4j.dsagai.exam.client.view.ConsoleView;
import ru.job4j.dsagai.exam.server.game.GameSessionInfo;

import java.util.List;

/**
 * Join session screen for console view.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public class JoinSession extends Screen {
    //if true, then trying to connect as Player, otherwise as Spectator.
    private final boolean player;



    @Override
    public void run() {
        List<GameSessionInfo> sessions = getController().getActiveSessions();
        for (int i = 0; i < sessions.size(); i++) {
            System.out.printf("%d %s%n", i + 1, sessions.get(i));
        }
        System.out.println("Choose the session. Enter session number:");
        try {
            int response = Integer.parseInt(getView().takeConsoleResponse()) - 1;
            boolean result = getController().connectGameSession(sessions.get(response).getUid(), this.player);
            if (result) {
                Screen gameMode = new GameMode(getController(), getView());
                getView().setScreen(gameMode);
            } else {
                System.out.println("Connection was unsuccessful. Try again.");
                refresh();
            }
        } catch (InterruptedException e) {
            System.out.println("Closing JoinSession menu.");
        } catch (Exception e) {
            System.out.println("Incorrect input. Try again.");
            refresh();
        }
    }

    public JoinSession(Controller controller, ConsoleView view, boolean player) {
        super(controller, view);
        this.player = player;
    }

    @Override
    /**
     * Current thread interrupts,
     * then starting up new JoinSession thread,
     * which shows fresh info about active sessions.
     */
    public void refresh() {
        Screen joinSession = new JoinSession(getController(), getView(), this.player);
        getView().setScreen(joinSession);
    }
}
