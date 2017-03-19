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
        assertThat(this.gameRound.turn(new GameCell(- 1, 0, this.player)), is(false));
        assertThat(this.gameRound.turn(new GameCell(0, -1, this.player)), is(false));
        assertThat(this.gameRound.turn(new GameCell(3, 0, this.player)), is(false));
        assertThat(this.gameRound.turn(new GameCell(0, 3, this.player)), is(false));
    }

    @Test
    public void whenCellIsFreeThenTrueAndCellEqualsPlayer() throws Exception {
        int x = 1;
        int y = 2;
        assertThat(this.gameRound.turn(new GameCell(x, y, this.player)), is(true));
        assertThat(this.gameRound.getField().getValue(x, y), is(this.player));
    }

    @Test
    public void whenCellIsBusyThenFalse() throws Exception {
        GameCell cell = new GameCell(0, 0, 1);
        this.gameRound.turn(cell);
        assertThat(this.gameRound.turn(cell), is(false));
    }



    @Test
    public void whenVerticalLineFilledThenGameOver() throws Exception {
        int x = 1;
        this.gameRound.turn(new GameCell(x, 0, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(new GameCell(x, 2, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(new GameCell(x, 1, this.player));
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(this.player));
    }

    @Test
    public void whenHorizontalLineFilledThenGameOver() throws Exception {
        int y = 1;
        this.gameRound.turn(new GameCell(1, y, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(new GameCell(0, y, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));
        this.gameRound.turn(new GameCell(2, y, this.player));
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(this.player));
    }

    @Test
    public void whenFirstDiagonalFilledThenGameOver() throws Exception {
        int x = 0;
        int y = 0;
        this.gameRound.turn(new GameCell(x++, y++, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(new GameCell(x++, y++, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(new GameCell(x, y, this.player));
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
            GameCell cell = new GameCell(x, y, player);
            this.gameRound.turn(cell);
        }
        assertThat(this.gameRound.isGameOver(), is(true));
        assertThat(this.gameRound.getWinnerId(),is(0));
    }

    @Test
    public void whenSecondDiagonalFilledThenGameOver() throws Exception {
        int x = 0;
        int y = 2;
        this.gameRound.turn(new GameCell(x++, y--, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(new GameCell(x++, y--, this.player));
        assertThat(this.gameRound.isGameOver(), is(false));

        this.gameRound.turn(new GameCell(x, y, this.player));
        assertThat(this.gameRound.isGameOver(), is(true));
    }
}