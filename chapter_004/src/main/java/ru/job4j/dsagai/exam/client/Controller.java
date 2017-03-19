package ru.job4j.dsagai.exam.client;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.job4j.dsagai.exam.client.view.View;
import ru.job4j.dsagai.exam.exceptions.UnexpectedMessageType;
import ru.job4j.dsagai.exam.protocol.Connection;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
import ru.job4j.dsagai.exam.server.game.GameSessionInfo;
import ru.job4j.dsagai.exam.server.game.InitGameSessionInfo;
import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameField;


import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;

/**
 * Controller class provides
 * net connection service for game client.
 *
 * @author dsagai
 * @version 1.00
 * @since 18.02.2017
 */
public class Controller extends Thread {
    private static final Logger log = Logger.getLogger(Controller.class);
    private static long VERSION_UID = -5205962155846180894L;


    private final View view;
    private Connection connection;
    private boolean connected;

    public Controller(View view) {
        super();
        this.view = view;
        this.connected = false;
    }

    @Override
    /**
     * main loop of controller.
     */
    public void run() {
        while (!isInterrupted()){
            if (this.connected) {
                try {
                    Message incoming = this.connection.getRequests();
                    switch (incoming.getType()){
                        case FIELD_INIT: this.view.initField((int)incoming.getData());
                        break;
                        case GAME_TURN_REQUEST: this.view.turnRequest();
                        break;
                        case FIELD_UPDATE_REQUEST: this.view.updateField((GameCell) incoming.getData());
                        break;
                        case DISCONNECT_GAME: this.view.disconnectSession();
                        break;
                        case TEXT_MESSAGE: this.view.showText((String)incoming.getData());
                        break;
                    }
                } catch (InterruptedException e) {
                    disconnectServer();
                }
            }
        }
    }

    /**
     * method try to connect to remote server.
     * @param address Sting remote server address.
     * @param port int server port.
     */
    public boolean connectServer(String address, int port) {
        boolean result = false;
        Socket socket = null;
        try {
            socket = new Socket(address, port);
            this.connection = new Connection(socket);
            new Thread(new FutureTask<String>(this.connection)).start();
            clientHandHake();
            result = true;
        } catch (IOException e) {
            log.log(Level.ERROR, String.format("Could't connect to server %s:%d", address, port));
        }
        return result;
    }

    /**
     * handshake procedure client side.
     */
    private void clientHandHake(){
        try {
            Message serverRequest = this.connection.getRequests();
            if (serverRequest.getType() != MessageType.VERSION_CHECK_REQUEST) {
                throw new UnexpectedMessageType(String.format("HandShake expected %s but received %s",
                        MessageType.VERSION_CHECK_REQUEST,
                        serverRequest.getType()));
            }
            this.connection.send(new Message(MessageType.VERSION_RESPONSE, VERSION_UID));
            Message response = this.connection.getResponses();
            if (response.getType() == MessageType.CONNECTION_ACCEPTED) {
                this.connected = true;
            } else {
                disconnectServer();
            }

        } catch (Exception e) {
            log.log(Level.ERROR, e.getMessage());
            disconnectServer();
        }

    }

    /**
     * method sends player's turn response.
     * @param cell GameCell
     */
    public void sendGameTurn(GameCell cell){
        if (this.connected) {
            try {
                this.connection.send(new Message(MessageType.GAME_TURN_RESPONSE, cell));
            } catch (IOException e) {
                disconnectServer();
            }
        }
    }

    /**
     * method creates new game session.
     * @param initInfo InitGameSessionInfo.
     * @return true if game session was successfully created and player was connected.
     */
    public int createSession(InitGameSessionInfo initInfo) {
        int result = -1;
        if (this.connected) {
            try {
                this.connection.send(new Message(MessageType.CREATE_GAME, initInfo));
                Message response = this.connection.getResponses();
                switch (response.getType()) {
                    case SESSION_CONNECTION_RESPONSE:
                        result = (int)response.getData();
                        break;
                    default:
                        throw new UnexpectedMessageType(String.format("Expected %s, but received %s",
                                MessageType.SESSION_CONNECTION_RESPONSE,
                                response.getType()));
                }
            } catch (Exception e) {
                log.log(Level.ERROR, e.getMessage());
                disconnectServer();
            }
        }
        return result;
    }



    /**
     * try to connect to existing game as player or as spectator.
     * @param uid String game session identifier.
     * @param isPlayer boolean. True if you want to connect as player, false if you want to connect as spectator.
     * @return true if connected, otherwise false.
     */
    public int connectGameSession(String uid, boolean isPlayer) {
        int result = -1;
        if (this.connected) {
            try {
                MessageType messageType = isPlayer ? MessageType.CONNECT_AS_PLAYER : MessageType.CONNECT_AS_SPECTATOR;
                this.connection.send(new Message(messageType, uid));
                Message response = this.connection.getResponses();
                switch (response.getType()) {
                    case SESSION_CONNECTION_RESPONSE:
                        result = (int)response.getData();
                        break;
                    default:
                        throw new UnexpectedMessageType(String.format("Expected %s, but received %s",
                                MessageType.SESSION_CONNECTION_RESPONSE,
                                response.getType()));
                }
            } catch (Exception e) {
                log.log(Level.ERROR, e.getMessage());
                disconnectServer();
            }
        }
        return result;
    }

    /**
     * method asks server for active sessions
     * @return List<GameSessionInfo>.
     */
    public List<GameSessionInfo> getActiveSessions() {
        List<GameSessionInfo> list = new ArrayList<>();
        if (this.connected) {
            try {
                this.connection.send(new Message(MessageType.ACTIVE_SESSIONS_REQUEST));
                Message response = this.connection.getResponses();
                list = (List<GameSessionInfo>) response.getData();
            } catch (Exception e) {
                log.log(Level.INFO, e.getMessage());
                disconnectServer();
            }
        }
        return list;
    }

    /**
     * disconnects game session.
     */
    public void disconnectSession() {
        if (this.connected) {
            try {
                this.connection.send(new Message(MessageType.DISCONNECT_GAME));
            } catch (IOException e) {
                disconnectServer();
            }
        }
    }

    /**
     * disconnects from server
     */
    public void disconnectServer() {
        if (this.connected) {
            try {
                this.connection.send(new Message(MessageType.DISCONNECT_SERVER));
                this.connection.close();
            } catch (IOException e) {
                log.log(Level.INFO, e.getMessage());
            } finally {
                this.connected = false;
            }
        }
    }

    /**
     * Closing connection with server
     * and finishing thread.
     */
    public void close() {
        interrupt();
        disconnectServer();
    }


}
