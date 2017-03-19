package ru.job4j.dsagai.exam.server.game.roles;

import ru.job4j.dsagai.exam.exceptions.UnexpectedMessageType;
import ru.job4j.dsagai.exam.server.game.round.GameRound;

import java.io.IOException;

/**
 * Player could participate game session
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public interface Player extends Spectator {

    /**
     * method appoints player's id for this session.
     * @param id
     */
    void setId(int id);

    /**
     * getter for id field.
     * @return int id.
     */
    int getId();
    /**
     * make next turn.
     * @param game GameRound current game round.
     */
    void makeTurn(GameRound game) throws IOException, UnexpectedMessageType, ClassNotFoundException, InterruptedException;

}
