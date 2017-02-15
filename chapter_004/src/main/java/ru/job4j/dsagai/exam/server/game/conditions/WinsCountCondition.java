package ru.job4j.dsagai.exam.server.game.conditions;

import java.util.*;

/**
 * Win condition, which requires defined number of victories to win
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public class WinsCountCondition implements WinCondition {
    private final Map<Integer, Integer> winners;
    private final int winsCount;
    private boolean victory;

    /**
     * default constructor.
     * @param winsCount int number of victories required to win
     */
    public WinsCountCondition(int winsCount) {
        this.winners = new HashMap<>();
        this.winsCount = winsCount;
    }

    @Override
    /**
     * adds new winner id to the session stats
     * @param winnerId int.
     */
    public void addRoundResult(int winnerId) {
        if (this.winners.containsKey(winnerId)){
            this.winners.put(winnerId, this.winners.get(winnerId) + 1);
        } else {
            this.winners.put(winnerId, 1);
        }
        checkCondition(winnerId);
    }

    /**
     * sets victory property to true if win condition was achieved
     * @param winnerId int.
     */
    private void checkCondition(int winnerId) {
        if (this.winners.get(winnerId) >= this.winsCount && winnerId != 0) {
            this.victory = true;
        }
    }

    @Override
    /**
     * returns stats in text format;
     * @return String.
     */
    public String getSessionStats() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, Integer> pair : this.winners.entrySet()){
            if (pair.getKey() == 0) {
                builder.append(String.format("draws %d%n", pair.getValue()));
            } else {
                builder.append(String.format("Player %d: games won %d%n", pair.getKey(), pair.getValue()));
            }
        }
        return builder.toString();
    }

    @Override
    /**
     *
     * @return true when victory is achieved, otherwise returns false
     */
    public boolean isVictory() {
        return this.victory;
    }
}
