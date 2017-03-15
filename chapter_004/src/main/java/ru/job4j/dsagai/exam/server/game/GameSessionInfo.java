package ru.job4j.dsagai.exam.server.game;

import java.io.Serializable;

/**
 * class for retrieving base information about GameSession object
 * and for sending it to the client.
 *
 */
public final class GameSessionInfo implements Serializable {
    //sessions unique identifier.
    private final String uid;
    //count of slots available to connect session as player.
    private final int playerSlotsAvailable;
    //count of slots available to connect session as spectator.
    private final int spectatorSlotsAvailable;


    /**
     * default constructor
     * @param session GameSession.
     */
    public GameSessionInfo(GameSession session) {
        this.uid = session.getUid();
        this.playerSlotsAvailable = session.getAvailablePlayerConnectionsCount();
        this.spectatorSlotsAvailable = session.getAvailableSpectatorConnectionsCount();
    }

    /**
     * getter
     * @return int count of slots available to connect session as player.
     */
    public int getPlayerSlotsAvailable() {
        return playerSlotsAvailable;
    }

    /**
     * getter
     * @return int count of slots available to connect session as spectator.
     */
    public int getSpectatorSlotsAvailable() {
        return spectatorSlotsAvailable;
    }

    /**
     * getter
     * @return String sessions unique identifier.
     */
    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "GameSessionInfo{" +
                "uid='" + uid + '\'' +
                ", playerSlotsAvailable=" + playerSlotsAvailable +
                ", spectatorSlotsAvailable=" + spectatorSlotsAvailable +
                '}';
    }
}
