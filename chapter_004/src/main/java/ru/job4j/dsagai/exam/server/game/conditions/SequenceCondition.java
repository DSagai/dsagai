package ru.job4j.dsagai.exam.server.game.conditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Win condition, which requires several victories in a row
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public class SequenceCondition implements WinCondition {
    private final List<Integer> winners;
    private final int sequenceLength;
    private boolean victory = false;

    /**
     * default constructor
     * @param sequenceLength int.
     */
    public SequenceCondition(int sequenceLength) {
        this.winners = new ArrayList<>();
        this.sequenceLength = sequenceLength;
    }

    @Override
    /**
     * adds new winner id to the session stats
     * @param winnerId int.
     */
    public void addRoundResult(int winnerId) {
        this.winners.add(winnerId);
        checkCondition(winnerId);
    }

    /**
     * sets victory property to true if win condition was achieved
     * @param winnerId int.
     */
    private void checkCondition(int winnerId) {
        boolean victory = false;
        if (winnerId != 0 && this.winners.size() >= this.sequenceLength) {
            victory = true;
            for (int i = 1; i <= this.sequenceLength; i++) {
                if (this.winners.get(this.winners.size() - i) != winnerId) {
                    victory = false;
                    break;
                }
            }
        }
        this.victory = victory;
    }

    @Override
    /**
     * returns stats in text format;
     * @return String.
     */
    public String getSessionStats() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.winners.size(); i++){
            if (this.winners.get(i) == 0) {
                builder.append(String.format("Round %d: draw%n", i));
            } else {
                builder.append(String.format("Round %d: winner Player %d%n", i, this.winners.get(i)));
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
