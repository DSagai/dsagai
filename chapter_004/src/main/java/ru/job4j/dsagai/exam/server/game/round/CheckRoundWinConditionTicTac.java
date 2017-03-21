package ru.job4j.dsagai.exam.server.game.round;

/**
 * Round win utility for TicTacToe
 *
 * @author dsagai
 * @version 1.00
 * @since 21.03.2017
 */

public class CheckRoundWinConditionTicTac {
    //contains all pure win combinations.
    private int[] winMask;

    /**
     * fills all pure win combinations.
     * @param size int.
     */
    public void initMask(int size) {
        this.winMask = new int[size * 2 + 2];
        //fill rows
        int k = (1 << size) - 1;
        for (int i = 0; i < size; i++) {
            this.winMask[i] = k << i * size;
        }
        //fill columns
        k = 0;
        for (int i = 0; i < size; i++) {
            k += 1 << i * size;
        }
        for (int i = 0; i < size; i++) {
            this.winMask[i + size] = k << i;
        }
        //fill first diagonal
        for (int i = 0; i < size; i++) {
            this.winMask[size * 2] += 1 << i * (size + 1);
        }
        //fill second diagonal
        for (int i = 1; i <= size; i++) {
            this.winMask[size * 2 + 1] += 1 << i * (size - 1);
        }
    }

    /**
     * converts field into integer representation.
     * for example field 0 1 0
     *                   1 0 2
     *                   0 1 2
     * for player 1 returns 010100010 (binary representation)
     * for player 2 returns 000001001 (binary representation)
     * @param field
     * @param playerId
     * @return
     */
    private int convertArrayToInt(int[][] field, int playerId) {
        int result = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == playerId) {
                    result += 1 << (i * field.length + j);
                }
            }
        }
        return result;
    }

    /**
     * checks is the field victorious for exact player
     * @param field int[][] game field.
     * @param playerId int player's id.
     * @return true if player is a winner for this field, otherwise returns false.
     */
    public boolean checkWin(int[][] field, int playerId) {
        boolean result = false;
        int fieldToInt = convertArrayToInt(field, playerId);
        for (int mask : this.winMask) {
            if (mask == (mask & fieldToInt)){
                result = true;
                break;
            }
        }
        return result;
    }

}
