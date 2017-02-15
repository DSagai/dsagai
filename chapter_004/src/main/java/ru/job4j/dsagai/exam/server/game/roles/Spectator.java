package ru.job4j.dsagai.exam.server.game.roles;

import ru.job4j.dsagai.exam.server.game.round.GameRound;

/**
 * Representation of a client at the server side.
 * Spectator could observe game process.
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public interface Spectator {

    /**
     * sends actual game info to client
     * @param game GameRound current
     */
    void refreshField(GameRound game);

    /**
     * sends text message to client
     * @param message
     */
    void showMessage(String message);
}
