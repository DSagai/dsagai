package ru.job4j.dsagai.exam.client.view;

import ru.job4j.dsagai.exam.server.game.round.GameField;

/**
 * Interface of view part of game client.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public interface View {

    /**
     * informs client, that server waits turn from him.
     */
    void turnRequest();

    /**
     * sends to the client updated game field.
     * @param field int[][]
     */
    void updateField(GameField field);

    /**
     * informs client, what he was disconnected
     * from game session.
     */
    void disconnectSession();

    /**
     * sends text message to the client
     * @param text String.
     */
    void showText(String text);

}
