package ru.job4j.dsagai.exam.server.game;

import ru.job4j.dsagai.exam.server.game.conditions.WinConditionTypes;
import ru.job4j.dsagai.exam.server.game.round.GameTypes;

import java.io.Serializable;


/**
 * Entity class for transfer information required for GameSession initialization
 *
 * @author dsagai
 * @version 1.00
 * @since 28.02.2017
 */

public final class InitGameSessionInfo implements Serializable {
    //defines win condition.
    private final WinConditionTypes winConditionType;
    //param for WinCondition initialization.
    private final int count;
    //defines game params, such as game field size, count of players, etc.
    private final GameTypes gameType;

    /**
     * 0 - don't add bot player
     * 1 - add bot as the first player
     * 2 - add bot as the second player
     */
    private final int addBot;

    /**
     * default constructor
     * @param winConditionType WinConditionTypes defines win condition.
     * @param count int param for WinCondition initialization.
     * @param gameType GameTypes defines game params, such as game field size, count of players, etc.
     * @param addBot int defines bot player presence:
     *               0 - don't add bot player
     *               1 - add bot as the first player
     *               2 - add bot as the second player
     */
    public InitGameSessionInfo(WinConditionTypes winConditionType, int count,
                               GameTypes gameType, int addBot) {
        this.winConditionType = winConditionType;
        this.count = count;
        this.gameType = gameType;
        this.addBot = addBot;
    }

    /**
     * getter for winConditionType field.
     * @return WicConditionTypes.
     */
    public WinConditionTypes getWinConditionType() {
        return winConditionType;
    }

    /**
     * getter for count field.
     * @return int.
     */
    public int getCount() {
        return count;
    }

    /**
     * getter for gameType field.
     * @return GameTypes.
     */
    public GameTypes getGameType() {
        return gameType;
    }


    /**
     * getter for addBot field.
     * @return int.
     */
    public int getAddBot() {
        return addBot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        InitGameSessionInfo that = (InitGameSessionInfo) o;

        if (count != that.count)
            return false;
        if (addBot != that.addBot)
            return false;
        if (winConditionType != that.winConditionType)
            return false;
        return gameType == that.gameType;
    }

    @Override
    public int hashCode() {
        int result = winConditionType.hashCode();
        result = 31 * result + count;
        result = 31 * result + gameType.hashCode();
        result = 31 * result + addBot;
        return result;
    }
}
