package ru.job4j.dsagai.exam.server.game.conditions;

import java.util.*;

/**
 * Game finishes when defined number of rounds was played.
 * But after last round is draw score, then game continues till first win.
 * If one of the players is ahead with advantage more than remaining rounds, then game also finishes.
 *
 * @author dsagai
 * @version 1.00
 * @since 17.02.2017
 */

public class GamesCountCondition implements WinCondition {
    private final int gamesCount;
    private int currentRound;
    private final Map<Integer,Integer> winners;
    private int winnerId;
    private boolean victory;


    /**
     * default constructor
     * @param gamesCount int.
     */
    public GamesCountCondition(int gamesCount) {
        this.gamesCount = gamesCount;
        this.winners = new TreeMap<>();
        this.winnerId = 0;
        this.victory = false;
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
        this.currentRound++;
        checkCondition(winnerId);
    }

    /**
     * sets victory property to true if win condition was achieved
     * @param winnerId int.
     */
    private void checkCondition(int winnerId) {
        int[] bestPlayer = {0, 0};
        int[] secondPlayer = {0, 0};
        for (Map.Entry<Integer, Integer> entry : this.winners.entrySet()){
            if (entry.getKey() != 0 && entry.getValue() >= bestPlayer[1]) {
                secondPlayer = Arrays.copyOf(bestPlayer, bestPlayer.length);
                bestPlayer[0] = entry.getKey();
                bestPlayer[1] = entry.getValue();
            }
        }
        if (this.currentRound >= this.gamesCount){
            if (bestPlayer[1] > secondPlayer[1]){
                this.winnerId = bestPlayer[0];
                this.victory = true;
            }
        } else if (bestPlayer[1] - secondPlayer[1] > this.gamesCount - this.currentRound) {
            this.winnerId = bestPlayer[0];
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

    @Override
    /**
     *
     * @return winnerId or nil if draw happened
     */
    public int getWinnerId() {
        return this.winnerId;
    }
}
