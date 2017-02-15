package ru.job4j.dsagai.exam.server.game.roles;

import ru.job4j.dsagai.exam.server.game.round.GameRound;

/**
 * Player could participate game session
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public interface Player extends Spectator {
    /**
     * make next turn.
     * @param game GameRound current game round.
     */
    void makeTurn(GameRound game);

}
