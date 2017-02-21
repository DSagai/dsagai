package ru.job4j.dsagai.exam.server.game.round;

import java.io.Serializable;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 21.02.2017
 */

public final class GameCell implements Serializable {
    private final int x;
    private final int y;

    public GameCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
