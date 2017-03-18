package ru.job4j.dsagai.exam.server.game.round;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Test for TicTacToe class
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public class TicTacToeTest {
    private GameRound gameRound;
    private int player = 1;

    @Before
    public void init() {
        this.gameRound = new TicTacToe(3);
    }


    @Test
    public void gameField()throws Exception {
        int [][] field = this.gameRound.getField().getArrayRepresentation();
        assertThat(3, is(field.length));
        assertThat(3, is(field[0].length));
    }

    @Test
    public void whenIllegalCoordinateThenFalse() throws Exception {
        assertThat(this.gameRound.turn(this.player, - 1, 0), is(false));
        assertThat(this.gameRound.turn(this.player, 0, -1), is(false));
        assertThat(this.gameRound.turn(this.player, 3, 0), is(false));
        assertThat(this.gameRound.turn(this.player, 0, 3), is(false));
    }

    @Test
    public void whenCellIsFreeThenTrueAndCellEqualsPlayer() throws Exception {
        int x = 1;
        int y = 2;
        assertThat(this.gameRound.turn(this.player, x, y), is(true));
        assertThat(this.gameRound.getField().getValue(x, y), is(this.player));
    }

    @Test
    public void whenCellIsBusyThenFalse() throws Exception {
        this.gameRound.turn(1, 0, 0);
        assertThat(this.gameRound.turn(this.player, 0, 0), is(false));
    }



    @Test
    public void whenVerticalLineFilledThenGameOver() throws Exception {
        int x = 1;
        this.gameRound.turn(this.player, x, 0);
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(this.player, x, 2);
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(this.player, x, 1);
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(this.player));
    }

    @Test
    public void whenHorizontalLineFilledThenGameOver() throws Exception {
        int y = 1;
        this.gameRound.turn(this.player, 1, y);
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(this.player, 0, y);
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(this.player, 2, y);
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(this.player));
    }

    @Test
    public void whenFirstDiagonalFilledThenGameOver() throws Exception {
        int x = 0;
        int y = 0;
        this.gameRound.turn(this.player, x++, y++);
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(this.player, x++, y++);
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(this.player, x, y);
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(this.player));
    }

    @Test
    public void whenAllCellsClosedThenDraw() throws Exception {

        int x;
        int y;
        int player;
        for (int i = 0; i < 9; i++){
            y = i / 3;
            x = i - y * 3;
            player = 1 + (i / 2) % 2;
            this.gameRound.turn(player, x, y);
        }
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(0));
    }

    @Test
    public void whenSecondDiagonalFilledThenGameOver() throws Exception {
        int x = 0;
        int y = 2;
        this.gameRound.turn(this.player, x++, y--);
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(this.player, x++, y--);
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(this.player, x, y);
        assertThat(this.gameRound.isGameOver(), is(true));
    }
}