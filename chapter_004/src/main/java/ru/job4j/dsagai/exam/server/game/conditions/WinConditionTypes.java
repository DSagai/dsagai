package ru.job4j.dsagai.exam.server.game.conditions;

import ru.job4j.dsagai.exam.client.view.components.MenuModeSelection;

import java.lang.reflect.InvocationTargetException;

/**
 * Enum defines available WinConditions and allows to get appropriate instance of WinCondition.
 *
 * @author dsagai
 * @version 1.00
 * @since 28.02.2017
 */

public enum WinConditionTypes {
    SEQUENCE_CONDITION(SequenceCondition.class,
            "Condition - sequence of wins in a row.",
            "select number of wins in a row:"),
    GAMES_COUNT_CONDITION(GamesCountCondition.class,
            "Condition - play defined number of rounds.",
            "select number of rounds to be played."),
    WINS_COUNT_CONDITION(WinsCountCondition.class,
            "Condition - win defined number of rounds.",
            "select number of rounds to be won.");

    private final Class<? extends  WinCondition> clazz;
    private final String description;
    private final String question;


    WinConditionTypes(Class<? extends WinCondition> clazz, String description, String question) {
        this.clazz = clazz;
        this.description = description;
        this.question = question;
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

    public String getDescription() {
        return description;
    }

    public String getQuestion() {
        return question;
    }

    /**
     *
     * @return String representation of all menu options.
     */
    public static String allItemsToString() {
        StringBuilder builder = new StringBuilder();
        for (WinConditionTypes selection : values()){
            builder.append(String.format("%d. %s%n", selection.ordinal() + 1, selection.getDescription()));
        }
        return builder.toString();
    }
}
