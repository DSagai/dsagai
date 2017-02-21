package ru.job4j.dsagai.exam.server.game.conditions;

import java.util.List;

/**
 * interface for win condition classes
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public interface WinCondition {
    /**
     * adds new winner id to the session stats
     * @param winnerId int.
     */
    void addRoundResult(int winnerId);

    /**
     * returns stats in text format;
     * @return String.
     */
    String getSessionStats();

    /**
     *
     * @return true when victory is achieved, otherwise returns false
     */
    boolean isVictory();

    /**
     *
     * @return winnerId or nil if draw happened
     */
    int getWinnerId();

}
