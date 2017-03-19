package ru.job4j.dsagai.exam.server;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.job4j.dsagai.exam.exceptions.UnexpectedMessageType;
import ru.job4j.dsagai.exam.protocol.Connection;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
import ru.job4j.dsagai.exam.server.game.GameSession;
import ru.job4j.dsagai.exam.server.game.GameSessionInfo;
import ru.job4j.dsagai.exam.server.game.InitGameSessionInfo;

import ru.job4j.dsagai.exam.server.game.roles.Player;
import ru.job4j.dsagai.exam.server.game.roles.RemotePlayer;
import ru.job4j.dsagai.exam.server.game.roles.bots.BotFactory;
import ru.job4j.dsagai.exam.server.game.round.GameRound;
import ru.job4j.dsagai.exam.util.MessagePropertyReader;
import ru.job4j.dsagai.exam.util.ServerPropertyReader;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.*;

/**
 * class GameServer is starting point
 * of server part of the application.
 * It receives connection requests.
 * And after that new connection is transferred
 * to the own connection handler thread.
 *
 * @author dsagai
 * @version 1.00
 * @since 21.02.2017
 */

public class GameServer {
    private static final long VERSION_UID = -5205962155846180894L;
    public static final Logger log = Logger.getLogger(GameServer.class);


    private final Map<String, GameSession> gameSessions;
    private final ExecutorCompletionService<String> completionService;

    /**
     * default constructor
     */
    public GameServer() {
        this.gameSessions = new ConcurrentSkipListMap<>();
        int threadsCount = ServerPropertyReader.getInstance().getIntValue("thread.pool.capacity");
        int workQueueLength = ServerPropertyReader.getInstance().getIntValue("work.queue.length");
        this.completionService = new ExecutorCompletionService<String>(new ThreadPoolExecutor(threadsCount, threadsCount,
                0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(workQueueLength)));

    }

    /**
     * The main loop of GameServer class.
     * the main task is to accept new socket connections
     * and gives it to a ConnectionHandler
     */
    public void start() {
        initCompletionService();
        int serverPort = ServerPropertyReader.getInstance().getIntValue("server.port");
        try (ServerSocket serverSocket = new ServerSocket(serverPort))
        {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket);
                Future<String> connectionFuture = this.completionService.submit(connection);
                this.completionService.submit(new ConnectionHandler(connection, connectionFuture));
            }

        } catch (IOException e) {
            log.log(Level.FATAL,String.format(MessagePropertyReader.getInstance().getString("server.cannot.open.server.socket"),
                    serverPort));
        }
    }


    /**
     * method initializes and launches
     * utility thread, which listens ThreadPool
     * and writes results into logger.
     */
    private void initCompletionService() {
        Thread completionServiceReader = new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    try {
                        log.log(Level.INFO, GameServer.this.completionService.take().get());
                    } catch (InterruptedException e) {
                        log.log(Level.INFO, e.getMessage());
                    } catch (ExecutionException e) {
                        log.log(Level.DEBUG, e.getCause().getStackTrace());
                    }
                }
            }
        };
        completionServiceReader.setDaemon(true);
        completionServiceReader.start();
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
    }

    /**
     * class provides management of client connection.
     * Accepts client requests and sends responses.
     * If connection brakes by any reason, ConnectionHandler calls for disconnect to
     * active GameSession.
     */
    private class ConnectionHandler implements Callable<String> {

        private final Connection connection;
        private final Future<String> connectionFuture;

        private boolean isConnected = false;
        //game session, which is connected to current remote user.
        private GameSession currentSession;
        //tells: is current ConnectionHolder owner of the currentSession.
        private boolean sessionOwner;

        public ConnectionHandler(Connection connection, Future<String> connectionFuture) {
            this.connection = connection;
            this.connectionFuture = connectionFuture;
            this.currentSession = null;
            this.sessionOwner = false;
        }

        @Override
        //the main loop of connection handler
        public String call() throws Exception {
            String result = "";
            handShake();
            while (this.isConnected && !Thread.currentThread().isInterrupted()) {
                if (!this.connectionFuture.isDone()) {
                    Message response = this.connection.getRequests();
                    switch (response.getType()) {
                        case ACTIVE_SESSIONS_REQUEST:
                            doActiveSessionRequest();
                            break;
                        case CONNECT_AS_PLAYER:
                            doConnectAsPlayer((String) response.getData());
                            break;
                        case CONNECT_AS_SPECTATOR:
                            doConnectAsSpectator((String) response.getData());
                            break;
                        case CREATE_GAME:
                            doCreateGame((InitGameSessionInfo) response.getData());
                            break;
                        case DISCONNECT_GAME:
                            doDisconnectGame();
                            break;
                        case DISCONNECT_SERVER:
                            doDisconnectServer();
                            break;
                    }
                    if (this.currentSession != null && !this.currentSession.isActive()) {
                        if (this.sessionOwner) {
                            GameServer.this.gameSessions.remove(this.currentSession.getUid());
                            this.sessionOwner = false;
                        }
                        this.currentSession = null;
                    }
                } else {
                    doDisconnectServer();
                }
            }
            return result;
        }

        /**
         * method closes clients connection.
         * @throws IOException
         */
        private void doDisconnectServer() throws IOException {
            doDisconnectGame();
            this.isConnected = false;
            this.connection.close();
        }

        /**
         * method disconnect client from GameSession, but
         * server connection remains active.
         */
        private void doDisconnectGame() {
            if (this.currentSession != null) {
                this.currentSession.removeClient(this.connection.getRemoteSocketAddress());
            }
        }

        /**
         * method creates new GameSession and joins own client connection to it.
         * @throws Exception
         */
        private void doCreateGame(InitGameSessionInfo initInfo) throws Exception {
            GameSession gameSession = new GameSession(initInfo);
            Class<? extends GameRound> gameRoundClass = initInfo.getGameType().getClazz();
            Player player = new RemotePlayer(this.connection);
            int playerId = -1;
            switch (initInfo.getAddBot()) {
            case 0:
                playerId = gameSession.addPlayer(this.connection.getRemoteSocketAddress(), player);
                break;
            case 1:
                playerId = gameSession.addPlayer(this.connection.getRemoteSocketAddress(), player);
                gameSession.addPlayer(BotFactory.getFakeAddress(), BotFactory.getBot(gameRoundClass));
                break;
            case 2:
                gameSession.addPlayer(BotFactory.getFakeAddress(), BotFactory.getBot(gameRoundClass));
                playerId = gameSession.addPlayer(this.connection.getRemoteSocketAddress(), player);
            }
            this.currentSession = gameSession;
            this.sessionOwner = true;
            GameServer.this.completionService.submit(gameSession);
            this.connection.send(new Message(MessageType.SESSION_CONNECTION_RESPONSE, playerId));
            GameServer.this.gameSessions.put(gameSession.getUid(), gameSession);

        }

        /**
         * method joins client connection to the existing GameSession as Spectator.
         * if connection was accepted, then then sends to the client message GAME_CREATION_SUCCESSFUL,
         * otherwise sends GAME_CONNECTION_UNSUCCESSFUL
         * @param uid String.
         */
        private void doConnectAsSpectator(String uid) throws IOException {
            GameSession session = GameServer.this.gameSessions.get(uid);
            boolean result = false;
            if (session != null) {
                result = session.addSpectator(this.connection.getRemoteSocketAddress(), new RemotePlayer(this.connection));
            }
            if (result) {
                this.currentSession = session;
                this.connection.send(new Message(MessageType.SESSION_CONNECTION_RESPONSE, 0));
            } else {
                this.connection.send(new Message(MessageType.SESSION_CONNECTION_RESPONSE, -1));
            }
        }

        /**
         * method joins client connection to the existing GameSession as Player.
         * if connection was accepted, then then sends to the client message GAME_CREATION_SUCCESSFUL,
         * otherwise sends GAME_CONNECTION_UNSUCCESSFUL
         * @param uid String.
         */
        private void doConnectAsPlayer(String uid) throws IOException {
            GameSession session = GameServer.this.gameSessions.get(uid);
            int result = -1;
            if (session != null) {
                result = session.addPlayer(this.connection.getRemoteSocketAddress(), new RemotePlayer(this.connection));
            }
            if (result > -1) {
                this.currentSession = session;
            }
            this.connection.send(new Message(MessageType.SESSION_CONNECTION_RESPONSE, result));
        }

        /**
         * method sends to the client List of active game sessions.
         */
        private void doActiveSessionRequest() throws IOException {
            ArrayList<GameSessionInfo> sessions = new ArrayList<>();
            for (Map.Entry<String, GameSession> sessionEntry : GameServer.this.gameSessions.entrySet()) {
                if (sessionEntry.getValue().isActive()) {
                    sessions.add(sessionEntry.getValue().getGameSessionInfo());
                }
            }
            this.connection.send(new Message(MessageType.ACTIVE_SESSIONS_RESPONSE, sessions));
        }



        /**
         * method checks client version.
         * if it equals to the server VERSION_UID,
         * then connection accepted,
         * else connection refused.
         */
        private void handShake() throws UnexpectedMessageType, InterruptedException, IOException {
            this.connection.send(new Message(MessageType.VERSION_CHECK_REQUEST));
            Message response = this.connection.getResponses();
            if (response.getType() != MessageType.VERSION_RESPONSE){
                throw new UnexpectedMessageType(String.format(MessagePropertyReader.getInstance().getString("server.unexpected.message"),
                        MessageType.VERSION_RESPONSE,
                        response.getType().name()));
            } else {
                long clientVersion = (long)response.getData();
                if (clientVersion == GameServer.VERSION_UID) {
                    this.isConnected = true;
                    this.connection.send(new Message(MessageType.CONNECTION_ACCEPTED,
                            String.format(MessagePropertyReader.getInstance().getString("server.connection.accepted"),
                                    ServerPropertyReader.getInstance().getProperty("server.name"))));
                } else {
                    this.connection.send(new Message(MessageType.CONNECTION_REFUSED,
                            MessagePropertyReader.getInstance().getString("server.connection.refused")));
                }
            }
        }
    }
}
