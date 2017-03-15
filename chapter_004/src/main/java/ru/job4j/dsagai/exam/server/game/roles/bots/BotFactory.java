package ru.job4j.dsagai.exam.server.game.roles.bots;

import ru.job4j.dsagai.exam.server.game.roles.Player;
import ru.job4j.dsagai.exam.server.game.round.GameRound;
import ru.job4j.dsagai.exam.server.game.round.TicTacToe;

import java.net.SocketAddress;

/**
 * Utility class. Returns appropriate Bot implementation
 * in dependency of game type
 *
 * @author dsagai
 * @version 1.00
 * @since 19.02.2017
 */

public class BotFactory {

    /**
     *
     * @return
     */
    public static SocketAddress getFakeAddress() {
        //TODO: implement method.
        return null;
    }

    /**
     * returns appropriate Bot implementation
     * in dependency of game type
     * @param gameClass
     * @return Player bot object.
     */
    public static Player getBot(Class<? extends GameRound> gameClass) {
        Player result;
        if (gameClass == TicTacToe.class) {
            result = new TicTacBot();
        } else {
            throw new UnsupportedClassVersionError();
        }
        return result;
    }
}
