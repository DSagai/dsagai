package ru.job4j.dsagai.exam.server.game.round;

/**
 * Available game configurations
 *
 * @author dsagai
 * @version 1.00
 * @since 16.02.2017
 */

public enum GameTypes {
    //TODO: Configure
    //TODO: init from XML?
    TicTacThreeOnThreeCellsField(3, 2, 10, TicTacToe.class),
    TicTacFiveOnFiveCellsField(5, 3, 10, TicTacToe.class);

    private final int edgeLength;
    private final int playersCount;
    private final int spectatorsCount;
    private final Class<? extends GameRound> clazz;

    GameTypes(int edgeLength, int playersCount, int spectatorsCount, Class<? extends GameRound> clazz) {
        this.edgeLength = edgeLength;
        this.playersCount = playersCount;
        this.spectatorsCount = spectatorsCount;
        this.clazz = clazz;
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
}
