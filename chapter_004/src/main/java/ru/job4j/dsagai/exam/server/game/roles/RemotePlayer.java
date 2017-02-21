package ru.job4j.dsagai.exam.server.game.roles;

import ru.job4j.dsagai.exam.exceptions.UnexpectedMessageType;
import ru.job4j.dsagai.exam.protocol.Connection;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameRound;

import java.io.IOException;

/**
 * Class maintains communication with remote user
 * through socket connection
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public class RemotePlayer implements Player {
    private static long WAIT_TIMEOUT = 100;

    private final Connection connection;
    private final int id;

    /**
     * default constructor
     * @param connection Connection.
     * @param id int.
     */
    public RemotePlayer(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }

    @Override
    /**
     * make next turn.
     * @param game GameRound current game round.
     */
    public void makeTurn(GameRound game) throws UnexpectedMessageType, IOException, ClassNotFoundException {
        boolean result = false;
        while (!result) {
            this.connection.send(new Message(MessageType.GAME_TURN_REQUEST));
            Message response = this.connection.receive();
            if (response.getType() != MessageType.GAME_TURN_RESPONSE) {
                throw new UnexpectedMessageType(String.format("Expected GAME_TURN_RESPONSE, but received %s", response.getType().name()));
            }
            GameCell cell = (GameCell)response.getData();
            result = game.turn(this.id, cell);
        }

    }

    @Override
    /**
     * sends actual game info to client
     * @param game GameRound current
     */
    public void refreshField(GameRound game) throws IOException {
        this.connection.send(new Message(MessageType.FIELD_REFRESH_REQUEST, game.getField()));
    }

    @Override
    /**
     * sends text message to client
     * @param message
     */
    public void showMessage(String message) throws IOException {
        this.connection.send(new Message(MessageType.TEXT_MESSAGE, message));
    }

    @Override
    /**
     *
     * @return true if connection is still active, otherwise returns false
     */
    public boolean isConnected() {
        boolean result = false;
        try{
            this.connection.send(new Message(MessageType.CONNECTION_CHECK_REQUEST));
            wait(WAIT_TIMEOUT);
            Message response = this.connection.receive();
            if (response.getType() == MessageType.CONNECTION_CHECK_RESPONSE) {
                result = true;
            }
        } catch (Exception e){
            //TODO: log error
        }
        return result;
    }
}
