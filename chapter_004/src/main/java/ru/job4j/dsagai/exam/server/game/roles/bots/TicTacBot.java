package ru.job4j.dsagai.exam.server.game.roles.bots;

import ru.job4j.dsagai.exam.server.game.roles.Player;
import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameField;
import ru.job4j.dsagai.exam.server.game.round.GameRound;

import java.io.IOException;

/**
 * Bot for TicTacToe game
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */

public class TicTacBot implements Player {
    GameField gameField;
    private int id;

    /**
     * default constructor.
     */
    TicTacBot() {}

    @Override
    /**
     * method appoints player's id for this session.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void makeTurn(GameRound game) {
        //TODO: implement algorithm
    }

    @Override
    /**
     * copies game field into own field.
     */
    public void updateField(GameCell cell) {
        this.gameField.updateCell(cell);
    }

    @Override
    /**
     * mock method. Bot does not require of text messages.
     */
    public void showMessage(String message) {}

    @Override
    /**
     * mock method. Bot does not require of text messages.
     */
    public void disconnectMessage() throws IOException {

    }

    @Override
    /**
     * inits gameField.
     */
    public void initField(int fieldSize) throws IOException {
        this.gameField = new GameField(fieldSize);
    }


}
