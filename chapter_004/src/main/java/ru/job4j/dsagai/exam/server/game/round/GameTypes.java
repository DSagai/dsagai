package ru.job4j.dsagai.exam.server.game.round;

/**
 * Available game configurations
 *
 * @author dsagai
 * @version 1.00
 * @since 16.02.2017
 */

public enum GameTypes {
    TicTacThreeOnThreeCellsField(3, 2, 10, TicTacToe.class,
            "Three on three cells game field for two players."),
    TicTacFiveOnFiveCellsField(5, 3, 10, TicTacToe.class,
            "Five on five cells game field for three players.");

    private final int edgeLength;
    private final int playersCount;
    private final int spectatorsCount;
    private final Class<? extends GameRound> clazz;
    private final String description;

    GameTypes(int edgeLength, int playersCount, int spectatorsCount, Class<? extends GameRound> clazz, String description) {
        this.edgeLength = edgeLength;
        this.playersCount = playersCount;
        this.spectatorsCount = spectatorsCount;
        this.clazz = clazz;
        this.description = description;
    }

    public int getEdgeLength() {
        return edgeLength;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public int getSpectatorsCount() {
        return spectatorsCount;
    }

    public Class<? extends GameRound> getClazz() {
        return clazz;
    }

    /**
     *
     * @return String representation of all menu options.
     */
    public static String allItemsToString() {
        StringBuilder builder = new StringBuilder();
        for (GameTypes selection : values()){
            builder.append(String.format("%d. %s%n", selection.ordinal() + 1, selection.getDescription()));
        }
        return builder.toString();
    }

    public String getDescription() {
        return description;
    }
}
