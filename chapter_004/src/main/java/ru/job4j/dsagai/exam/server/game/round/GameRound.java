package ru.job4j.dsagai.exam.server.game.round;

/**
 * GameRound interface
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public interface GameRound {
    /**
     * returns actual game field.
     * @return int[][]
     */
    int[][] getField();

    /**
     *
     * @return boolean is game over?
     */
    boolean isGameOver();

    /**
     * game turn method
     * @param player int players personal identity
     * @param x int.
     * @param y int.
     * @return boolean Returns true is game turn was accepted, and false if not.
     */
    boolean turn(int player, int x, int y);

    /**
     *
     * @return int winner id or nil if draw happens.
     */
    int getWinnerId();
}
