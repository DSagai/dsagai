package ru.job4j.dsagai.exam.server.game.conditions;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * tests for WinsCountCondition class
 *
 * @author dsagai
 * @version 1.00
 * @since 15.02.2017
 */

public class WinsCountConditionTest {
    private WinCondition condition;
    private int playerOne = 1;
    private int playerTwo = 2;

    @Before
    public void init() throws Exception {
        this.condition = new WinsCountCondition(2);
    }

    @Test
    public void whenWinsTwoRoundsThenWinsSession() throws Exception {

        this.condition.addRoundResult(this.playerOne);
        assertThat(this.condition.isVictory(), is(false));

        this.condition.addRoundResult(0);
        assertThat(this.condition.isVictory(), is(false));

        this.condition.addRoundResult(this.playerTwo);
        assertThat(this.condition.isVictory(), is(false));

        this.condition.addRoundResult(this.playerOne);
        assertThat(this.condition.isVictory(), is(true));
    }

    @Test
    public void getStats() {
        this.condition.addRoundResult(this.playerOne);
        this.condition.addRoundResult(0);
        this.condition.addRoundResult(this.playerTwo);
        this.condition.addRoundResult(this.playerOne);

        String stats = this.condition.getSessionStats();

        assertThat(stats.contains("draws 1"), is(true));
        assertThat(stats.contains("Player 1: games won 2"), is(true));
        assertThat(stats.contains("Player 2: games won 1"), is(true));

    }



}