package ru.job4j.dsagai.exam.server.game.round;


import java.util.Arrays;

/**
 * Tic Tac Toe game
 *
 * @author dsagai
 * @version 1.00
 * @since 13.02.2017
 */

public class TicTacToe implements GameRound {
    private final int[][] field;
    private boolean gameOver;
    private int cellsClosed;
    private int winnerId;

    /**
     * default constructor
     * @param fieldSize int defines edge of the field.
     */
    public TicTacToe(int fieldSize) {
        this.field = new int[fieldSize][fieldSize];
        this.gameOver = false;
        this.cellsClosed = 0;
        this.winnerId = 0;
    }

    /**
     * returns actual game field.
     * @return int[][]
     */
    @Override
    public int[][] getField() {
        return Arrays.copyOf(this.field, this.field.length);
    }


    /**
     *
     * @return boolean is game over?
     */
    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * game turn method
     * @param player int players personal identity
     * @param x int.
     * @param y int.
     * @return boolean Returns true is game turn was accepted, and false if not.
     */
    @Override
    public boolean turn(int player, int x, int y) {
        boolean result = false;
        if (x >= 0 && y >= 0
                && x < this.field.length && y < this.field.length
                && this.field[x][y] == 0) {
            result = true;
            this.field[x][y] = player;
            this.cellsClosed++;
            checkGameCondition(x, y);
        }

        return result;
    }

    @Override
    /**
     *
     * @return int winner id or nil if draw happens.
     */
    public int getWinnerId() {
        return this.winnerId;
    }

    /**
     * method checks game over condition.
     * if condition has been satisfied then gameOver field turns to true
     * @param x
     * @param y
     */
    private void checkGameCondition(int x, int y) {
        int player = this.field[x][y];
        if (this.cellsClosed == this.field.length * this.field.length) {
            this.gameOver = true;
        }else if (horizontalMatches(player, y) || verticalMatches(player, x)
                || diagonalMatches(player, true)
                || diagonalMatches(player, false)) {
            this.gameOver = true;
            this.winnerId = player;
        }
    }

    /**
     * checks is horizontal line fulfilled by current player.
     * @param player int
     * @param y int
     * @return true if line is fulfilled by current player, otherwise return false.
     */
    private boolean horizontalMatches(int player, int y) {
        boolean result = true;
        for (int x = 0; x < this.field.length; x++){
            if (this.field[x][y] != player) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * checks is vertical line fulfilled by current player.
     * @param player int
     * @param x int
     * @return true if line is fulfilled by current player, otherwise return false.
     */
    private boolean verticalMatches(int player, int x) {
        boolean result = true;
        for (int y = 0; y < this.field.length; y++){
            if (this.field[x][y] != player) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * checks is diagonal line fulfilled by current player.
     * @param player int
     * @param isFirst boolean
     * @return true if line is fulfilled by current player, otherwise return false.
     */
    private boolean diagonalMatches(int player, boolean isFirst){
        int x = 0;
        int y = isFirst ? 0 : this.field.length - 1;
        int increment = isFirst ? 1 : -1;

        boolean result = true;

        for (int i = 0; i < this.field.length; i++) {
            if (this.field[x][y] != player){
                result = false;
                break;
            }
            x++;
            y = y + increment;
        }
        return result;
    }




}
