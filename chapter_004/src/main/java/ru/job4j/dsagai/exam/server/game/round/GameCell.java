package ru.job4j.dsagai.exam.server.game.round;

import java.io.Serializable;

/**
 * Class holds game turn information - selected field cell
 *
 * @author dsagai
 * @version 1.00
 * @since 21.02.2017
 */

public final class GameCell implements Serializable {
    private final int x;
    private final int y;

    /**
     * default constructor.
     * @param x int.
     * @param y int.
     */
    public GameCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * getter for x field.
     * @return int.
     */
    public int getX() {
        return x;
    }

    /**
     * getter for y field.
     * @return int.
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        GameCell gameCell = (GameCell) o;

        if (x != gameCell.x)
            return false;
        return y == gameCell.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
