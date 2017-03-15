package ru.job4j.dsagai.exam.server.game;

import org.apache.log4j.Level;
import ru.job4j.dsagai.exam.server.GameServer;
import ru.job4j.dsagai.exam.server.game.conditions.WinCondition;
import ru.job4j.dsagai.exam.server.game.roles.Player;
import ru.job4j.dsagai.exam.server.game.roles.Spectator;
import ru.job4j.dsagai.exam.server.game.round.GameRound;
import ru.job4j.dsagai.exam.server.game.round.GameTypes;
import ru.job4j.dsagai.exam.util.MessagePropertyReader;


import java.io.IOException;
import java.net.SocketAddress;
import java.rmi.server.UID;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;



/**
 * GameSession class implements administration of game session (sequence of game rounds)
 * @author dsagai
 * @version 1.00
 * @since 16.02.2017
 */

public class GameSession implements Callable<String> {


    private final WinCondition winCondition;
    private final GameTypes type;
    private final ConcurrentHashMap<SocketAddress,Spectator> spectators;
    private final ConcurrentHashMap<SocketAddress,Player> players;
    private final String uid;
    private final MessagePropertyReader messagePropertyReader;

    private GameRound gameRound;
    private volatile Status status;

    /**
     * default constructor
     * @param winCondition WinCondition.
     * @param type GameType.
     */
    public GameSession(WinCondition winCondition, GameTypes type) {
        this.winCondition = winCondition;
        this.type = type;
        this.spectators = new ConcurrentHashMap<SocketAddress,Spectator>();
        this.players = new ConcurrentHashMap<SocketAddress,Player>();
        this.status = Status.New;
        this.uid = (new UID()).toString();
        this.messagePropertyReader = MessagePropertyReader.getInstance();
    }

    /**
     * additional constructor.
     * @param initInfo GameSessionInitInfo.
     * @throws Exception
     */
    public GameSession(GameSessionInitInfo initInfo) throws Exception {
        this(initInfo.getWinConditionType().getWinConditionObject(initInfo.getCount()),
                initInfo.getGameType());
    }

    /**
     * getter UID field
     * @return String uid.
     */
    public String getUid() {
        return uid;
    }

    /**
     *
     * @return number of free connection slots for players.
     */
    public int getAvailablePlayerConnectionsCount() {
        return this.type.getPlayersCount() - this.players.size();
    }

    /**
     *
     * @return number of free connection slots for spectators.
     */
    public int getAvailableSpectatorConnectionsCount() {
        return this.type.getSpectatorsCount() - this.spectators.size();
    }


    /**
     * method adds new spectator to the session.
     * @param address SocketAddress.
     * @param spectator Spectator.
     * @return true if spectator was added, or false otherwise.
     */
    public synchronized boolean addSpectator(SocketAddress address, Spectator spectator) {
        boolean result = false;
        if (this.status != Status.Closed && getAvailableSpectatorConnectionsCount() > 0) {
            this.spectators.put(address, spectator);
            result = true;
        }
        return result;
    }

    /**
     * method adds new player to the session.
     * @param address SocketAddress.
     * @param player Player.
     * @return true if player was added, or false otherwise.
     */
    public synchronized boolean addPlayer(SocketAddress address, Player player) {
        boolean result = false;
        if (this.status == Status.New && getAvailablePlayerConnectionsCount() > 0) {
            if (addSpectator(address, player)) {
                this.players.put(address, player);
                result = true;
            }
        }
        return result;
    }

    /**
     * removes client from the game session.
     * @param address
     */
    public void removeClient(SocketAddress address) {
        removeSpectator(address);
        removePlayer(address);
    }


    /**
     * Removes player from game session.
     * if session has already begun, then session closes.
     * @param address
     */
    private void removePlayer(SocketAddress address) {

        this.players.remove(address);
        if (this.status == Status.Started){
            this.status = Status.Closed;
            close(this.messagePropertyReader.getString("game.end.lost.player"));
        }
    }

    /**
     * removes spectator from game session.
     * @param address SocketAddress.
     */
    private void removeSpectator(SocketAddress address) {
        this.spectators.remove(address);
    }


    /**
     * Creates and returns new GameRound
     * @return GameRound.
     * @throws Exception
     */
    private GameRound getNewRound() throws Exception {
        return (GameRound)this.type.getClazz().getConstructor(int.class).newInstance(this.type.getEdgeLength());
    }


    /**
     * getter for status field.
     * @return Status.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * waiting for players.
     * if there are no active connections then close the session.
     */
    private void waitForPlayersLoop() {
        while (getAvailablePlayerConnectionsCount() != 0){
            if (this.players.isEmpty() && this.spectators.isEmpty()){
                close(this.messagePropertyReader.getString("game.end.no.connections"));
            }
        }
    }

    /**
     * main game loop
     * iterating throw players until win condition has been achieved or
     * some of player lost connection.
     *
     * @throws Exception
     */
    private void gameLoop() throws Exception {
        int i = 0;
        this.gameRound = getNewRound();
        sendBroadcastMessage(this.messagePropertyReader.getString("game.round.started"));
        refreshField();
        Player[] playersArray = null;
        playersArray = this.players.values().toArray(playersArray);
        while (this.status != Status.Closed) {
            try {
                playersArray[i].makeTurn(this.gameRound);
                refreshField();
                if (this.gameRound.isGameOver()) {
                    this.winCondition.addRoundResult(this.gameRound.getWinnerId());
                    String roundRes = this.gameRound.getWinnerId() == 0 ?
                            this.messagePropertyReader.getString("game.draw") :
                            String.format(this.messagePropertyReader.getString("game.win"), this.gameRound.getWinnerId());

                    sendBroadcastMessage(String.format(this.messagePropertyReader.getString("game.round.over"), roundRes));
                    sendBroadcastMessage(this.winCondition.getSessionStats());


                    if (this.winCondition.isVictory()) {
                        close(String.format(this.messagePropertyReader.getString("game.end.win"), this.winCondition.getWinnerId()));
                    } else {
                        i = 0;
                        this.gameRound = getNewRound();
                        sendBroadcastMessage(this.messagePropertyReader.getString("game.round.started"));
                    }
                } else {
                    i++;
                    if (i == this.players.size()) {
                        i = 0;
                    }
                }
            } catch (Exception e) {
                close(this.messagePropertyReader.getString("game.end.unexpected.situation"));
                throw e;
            }
        }
    }

    /**
     * sends actual game field to all spectators.
     */
    private void refreshField() throws IOException {
        for (Map.Entry<SocketAddress,Spectator> spectator : this.spectators.entrySet()){
            spectator.getValue().refreshField(this.gameRound);
        }
    }


    @Override
    /**
     * game session entry point.
     */
    public String call() throws Exception {
        Date startTime = new Date();
        waitForPlayersLoop();
        gameLoop();
        Date endTime = new Date();
        return String.format(this.messagePropertyReader.getString("log.session"), this.uid, startTime, endTime);
    }




    /**
     * Closes current session.
     * @param result String result of the GameSession or the reason why Session was finished.
     */
    public void close(String result) {
        sendBroadcastMessage(String.format(this.messagePropertyReader.getString("session.end"), result));
        sendBroadcastDisconnectMessage();
        this.players.clear();
        this.spectators.clear();
        this.status = Status.Closed;
    }

    /**
     * sends text message to every spectator connected
     * @param message String.
     */
    private void sendBroadcastMessage(String message) {
        for (Map.Entry<SocketAddress,Spectator> spectator : this.spectators.entrySet()){
            try {
                spectator.getValue().showMessage(message);
            } catch (Exception e) {
                GameServer.log.log(Level.INFO, e.getCause().getMessage());
            }
        }
    }

    private void sendBroadcastDisconnectMessage() {
        for (Map.Entry<SocketAddress,Spectator> spectator : this.spectators.entrySet()){
            try {
                spectator.getValue().disconnectMessage();
            } catch (Exception e) {
                GameServer.log.log(Level.INFO, e.getCause().getMessage());
            }
        }
    }

    /**
     *
     * @return true if game session is still active (has status New or Started), otherwise returns false.
     */
    public boolean isActive() {
        return this.status != Status.Closed;
    }

    /**
     * returns information about this game session in format defined by GameSessionInfo class
     * @return GameSessionInfo.
     */
    public GameSessionInfo getGameSessionInfo() {
        return new GameSessionInfo(this);
    }



    /**
     * enum defines available states for the GameSession
     */
    private enum Status {
        New,
        Started,
        Closed
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameSession that = (GameSession) o;

        return uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
