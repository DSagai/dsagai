package ru.job4j.dsagai.exam.server.game.conditions;

import java.lang.reflect.InvocationTargetException;

/**
 * Enum defines available WinConditions and allows to get appropriate instance of WinCondition.
 *
 * @author dsagai
 * @version 1.00
 * @since 28.02.2017
 */

public enum WinConditionTypes {
    SEQUENCE_CONDITION(SequenceCondition.class),
    GAMES_COUNT_CONDITION(GamesCountCondition.class),
    WINS_COUNT_CONDITION(WinsCountCondition.class);

    private final Class<? extends  WinCondition> clazz;

    WinConditionTypes(Class<? extends WinCondition> clazz) {
        this.clazz = clazz;
    }


    /**
     * factory method for WinCondition object.
     * @param count int param required for initialisation of win condition object.
     *              Could represent count of wins in a row or count of games have to be played, etc.
     * @return WinCondition new instance of WinCondition depending of
     */
    public WinCondition getWinConditionObject(int count)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return this.clazz.getConstructor(int.class).newInstance(count);
    }
}
