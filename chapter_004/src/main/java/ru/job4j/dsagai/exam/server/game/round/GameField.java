package ru.job4j.dsagai.exam.server.game.round;

import java.io.Serializable;
import java.util.Arrays;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 16.03.2017
 */
public class GameField implements Serializable, Cloneable {
    private final int[][] field;

    public GameField(int n) {
        this.field = new int[n][n];
        this.field[0][0] = 1;
    }

    public boolean updateCell(int value, int x, int y) {
        boolean result = false;
        if (x >= 0 && y >= 0
                && x < this.field.length && y < this.field.length
                && this.field[x][y] == 0) {
            result = true;
            this.field[x][y] = value;

        }

        return result;
    }

    public boolean updateCell(int value, GameCell cell) {
        return updateCell(value, cell.getX(), cell.getY());
    }

    public int[][] getArrayRepresentation() {
        return Arrays.copyOf(this.field, this.field.length);
    }

    public int length() {
        return this.field.length;
    }

    public int getValue(int x, int y) {
        return this.field[x][y];
    }

    @Override
    public String toString() {
        return "GameField{" +
                "field=" + Arrays.deepToString(field) +
                '}';
    }
}
