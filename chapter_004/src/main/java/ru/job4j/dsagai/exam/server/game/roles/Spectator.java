package ru.job4j.dsagai.exam.server.game.roles;

import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameRound;

import java.io.IOException;

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
     * @param cell GameCell last turn.
     */
    void updateField(GameCell cell)  throws IOException;

    /**
     * sends text message to client
     * @param message
     */
    void showMessage(String message)  throws IOException;

    /**
     * informs client that he'll be disconnected.
     * @throws IOException
     */
    void disconnectMessage() throws IOException;

    /**
     * sends command to all clients to init game field.
     * @param fieldSize int size of the field.
     * @throws IOException
     */
    void initField(int fieldSize) throws IOException;

}
