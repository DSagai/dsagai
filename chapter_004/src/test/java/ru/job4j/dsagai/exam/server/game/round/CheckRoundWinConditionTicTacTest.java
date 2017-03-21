package ru.job4j.dsagai.exam.server.game.round;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * CheckRoundWinConditionTicTac
 *
 * @author dsagai
 * @version 1.00
 * @since 21.03.2017
 */

public class CheckRoundWinConditionTicTacTest {
    private CheckRoundWinConditionTicTac ticTac= new CheckRoundWinConditionTicTac();

    @Test
    public void testThreeOnThree() throws Exception {
        this.ticTac.initMask(3);
        int[][] field = {
                {0, 0, 1},
                {2, 0, 1},
                {2, 0, 1}
        };

        assertThat(ticTac.checkWin(field, 1), is(true));
        assertThat(ticTac.checkWin(field, 2), is(false));

        int[][] field2 = {
                {0, 0, 2},
                {1, 2, 0},
                {2, 0, 1}
        };

        assertThat(ticTac.checkWin(field2, 2), is(true));
        assertThat(ticTac.checkWin(field2, 1), is(false));

        int[][] field3 = {
                {0, 0, 2},
                {3, 3, 3},
                {2, 0, 1}
        };

        assertThat(ticTac.checkWin(field3, 3), is(true));
        assertThat(ticTac.checkWin(field3, 1), is(false));
    }

}