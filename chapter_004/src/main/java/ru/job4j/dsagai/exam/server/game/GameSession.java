package ru.job4j.dsagai.exam.server.game;

import ru.job4j.dsagai.exam.server.game.conditions.WinCondition;
import ru.job4j.dsagai.exam.server.game.roles.Player;
import ru.job4j.dsagai.exam.server.game.roles.Spectator;
import ru.job4j.dsagai.exam.server.game.roles.bots.TicTacBot;
import ru.job4j.dsagai.exam.server.game.round.GameRound;
import ru.job4j.dsagai.exam.server.game.round.GameTypes;
import ru.job4j.dsagai.exam.util.MessagePropertyReader;


import java.io.IOException;
import java.net.SocketAddress;
import java.rmi.server.UID;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * GameSession class implements administration of game session (sequence of game rounds)
 * TODO: modify class for modern concept accordance
 * @author dsagai
 * @version 1.00
 * @since 16.02.2017
 */

public class GameSession implements Callable<String> {


    private final WinCondition winCondition;
    private final GameTypes type;
    private final CopyOnWriteArrayList<Spectator> spectators;
    private final CopyOnWriteArrayList<Player> players;
    private final String uid;
    private final MessagePropertyReader messagePropertyReader;

    private GameRound gameRound;
    private Status status;

    /**
     * default constructor
     * @param winCondition WinCondition.
     * @param type GameType.
     */
    public GameSession(WinCondition winCondition, GameTypes type) {
        this.winCondition = winCondition;
        this.type = type;
        this.spectators = new CopyOnWriteArrayList<Spectator>();
        this.players = new CopyOnWriteArrayList<Player>();
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
     * Adds new Player
     * @param player Player.
     * @return true if player was successfully added, otherwise returns false.
     */
    public synchronized boolean addPlayer(Player player) {
        boolean result = false;
        if (this.status == Status.New && getAvailablePlayerConnectionsCount() > 0) {
            if (addSpectator(player)) {
                this.players.add(player);
                result = true;
            }
        }
        return result;
    }

    public synchronized boolean addSpectaror(SocketAddress address, Spectator spectator) {
        // TODO: implement method
        return false;
    }

    public synchronized boolean addPlayer(SocketAddress address, Player player) {
        //TODO: implement method
        return false;
    }

    public void removeClient(SocketAddress address) {
        //TODO: implement method
    }

    /**
     * Adds new Spectator
     * @param spectator Spectator.
     * @return true if spectator was successfully added, otherwise returns false.
     */
    public synchronized boolean addSpectator(Spectator spectator) {
        boolean result = false;
        if (this.status != Status.Closed && getAvailableSpectatorConnectionsCount() > 0) {
            this.spectators.add(spectator);
            result = true;
        }
        return result;
    }

    /**
     * TODO: add javadoc
     * @param player
     */
    public void removePlayer(Player player) {
        //TODO: add method implementation
    }

    /**
     * TODO: add javadoc
     * @param spectator
     */
    public void removeSpectator(Spectator spectator) {
        //TODO: add method implementation
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
            checkSpectatorConnections();
            checkPlayerConnections();
            if (this.players.isEmpty() && this.spectators.isEmpty()){
                finishGameSession(this.messagePropertyReader.getString("game.end.no.connections"));
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
        while (this.status != Status.Closed) {
            try {
                this.players.get(i).makeTurn(this.gameRound);
                refreshField();
                if (this.gameRound.isGameOver()) {
                    this.winCondition.addRoundResult(this.gameRound.getWinnerId());
                    String roundRes = this.gameRound.getWinnerId() == 0 ?
                            this.messagePropertyReader.getString("game.draw") :
                            String.format(this.messagePropertyReader.getString("game.win"), this.gameRound.getWinnerId());

                    sendBroadcastMessage(String.format(this.messagePropertyReader.getString("game.round.over"), roundRes));
                    sendBroadcastMessage(this.winCondition.getSessionStats());


                    if (this.winCondition.isVictory()) {
                        finishGameSession(String.format(this.messagePropertyReader.getString("game.end.win"), this.winCondition.getWinnerId()));
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
                finishGameSession(this.messagePropertyReader.getString("game.end.unexpected.situation"));
                throw e;
            }
        }
    }

    /**
     * sends actual game field to all spectators.
     */
    private void refreshField() throws IOException {
        for (Spectator spectator : this.spectators){
            spectator.refreshField(this.gameRound);
        }
    }


    @Override
    public String call() throws Exception {
        Date startTime = new Date();
        waitForPlayersLoop();
        gameLoop();
        Date endTime = new Date();
        return String.format(this.messagePropertyReader.getString("log.session"), this.uid, startTime, endTime);
    }

    /**
     * checks are players still connected?
     * if not then removes disconnected.
     * if some one was disconnected after session  was started, then session closes.
     */
    //TODO: rewrite method
    private void checkPlayerConnections() {
//        for (int i = this.players.size(); i >= 0; i--) {
//            if (!this.players.get(i).isConnected()){
//                this.players.remove(i);
//                if (this.status == Status.Started){
//                    finishGameSession(this.messagePropertyReader.getString("game.end.lost.player"));
//                    break;
//                }
//            }
//        }
    }


    /**
     * checks are spectators still connected?
     * if not then removes disconnected.
     */
    //TODO: rewrite method
    private void checkSpectatorConnections() {
//        for (int i = this.spectators.size() - 1; i >= 0; i--) {
//            if (!this.spectators.get(i).isConnected()) {
//                this.spectators.remove(i);
//            }
//        }
    }


    /**
     * Closes current session.
     * @param result String result of the GameSession or the reason why Session was finished.
     */
    private void finishGameSession(String result) {
        sendBroadcastMessage(String.format(this.messagePropertyReader.getString("session.end"), result));
        this.players.clear();
        this.spectators.clear();
        this.status = Status.Closed;
    }

    /**
     * sends text message to every spectator connected
     * @param message String.
     */
    private void sendBroadcastMessage(String message) {
        for (Spectator spectator : this.spectators){
            try {
                spectator.showMessage(message);
            } catch (Exception e) {
                //TODO: log error
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
