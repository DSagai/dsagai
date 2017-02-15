package ru.job4j.dsagai.exam.server.game.conditions;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Tests for SequenceCondition
 *
 * @author dsagai
 * @version 1.00
 * @since 15.02.2017
 */

public class SequenceConditionTest {
    private WinCondition condition;
    private int playerOne = 1;
    private int playerTwo = 2;

    @Before
    public void init() throws Exception {
        this.condition = new SequenceCondition(2);
    }

    @Test
    public void whenTwoVictoriesInRowThenWin() throws Exception {

        this.condition.addRoundResult(this.playerOne);
        assertThat(this.condition.isVictory(), is(false));

        this.condition.addRoundResult(this.playerTwo);
        assertThat(this.condition.isVictory(), is(false));

        this.condition.addRoundResult(this.playerOne);
        assertThat(this.condition.isVictory(), is(false));

        this.condition.addRoundResult(this.playerOne);
        assertThat(this.condition.isVictory(), is(true));

    }

    @Test
    public void getSessionStats() throws Exception {


        this.condition.addRoundResult(this.playerOne);
        this.condition.addRoundResult(this.playerTwo);
        this.condition.addRoundResult(0);
        String result = "Round 0: winner Player 1\r\n" +
                "Round 1: winner Player 2\r\n" +
                "Round 2: draw\r\n";

        assertThat(this.condition.getSessionStats(), is(result));

    }


}