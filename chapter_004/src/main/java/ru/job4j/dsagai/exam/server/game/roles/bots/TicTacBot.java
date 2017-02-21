package ru.job4j.dsagai.exam.server.game.roles.bots;

import ru.job4j.dsagai.exam.server.game.roles.Player;
import ru.job4j.dsagai.exam.server.game.round.GameRound;

/**
 * Bot for TicTacToe game
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */

public class TicTacBot implements Player {
    private int[][] gameField;

    /**
     * default constructor.
     */
    TicTacBot() {}

    @Override
    public void makeTurn(GameRound game) {
        //TODO: implement algorithm
    }

    @Override
    /**
     * copies game field into own field.
     */
    public void refreshField(GameRound game) {
        this.gameField = game.getField();
    }

    @Override
    /**
     * mock method. Bot does not require of text messages.
     */
    public void showMessage(String message) {}

    @Override
    /**
     * fake method. Bot does not use network connection.
     * That's why it is always connected
     */
    public boolean isConnected() {
        return true;
    }
}
