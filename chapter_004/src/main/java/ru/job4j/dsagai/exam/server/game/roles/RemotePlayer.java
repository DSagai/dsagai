package ru.job4j.dsagai.exam.server.game.roles;

import ru.job4j.dsagai.exam.exceptions.UnexpectedMessageType;
import ru.job4j.dsagai.exam.protocol.Connection;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameRound;
import ru.job4j.dsagai.exam.util.MessagePropertyReader;

import java.io.IOException;

/**
 * Class maintains communication with remote user
 * through socket connection
 *
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public class RemotePlayer implements Player {


    private final Connection connection;
    private int id;

    /**
     * default constructor
     * @param connection Connection.
     */
    public RemotePlayer(Connection connection) {
        this.connection = connection;
    }

    /**
     * setter for id field.
     * @param id int
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    /**
     * make next turn.
     * @param game GameRound current game round.
     */
    public void makeTurn(GameRound game) throws UnexpectedMessageType, IOException,
            ClassNotFoundException, InterruptedException {
        boolean result = false;
        while (!result) {
            this.connection.send(new Message(MessageType.GAME_TURN_REQUEST));
            Message response = this.connection.getResponses();
            if (response.getType() != MessageType.GAME_TURN_RESPONSE) {
                throw new UnexpectedMessageType(String.format(MessagePropertyReader.getInstance().getString("server.unexpected.message"),
                        MessageType.VERSION_RESPONSE,
                        response.getType().name()));
            }
            GameCell cell = (GameCell) response.getData();
            result = game.turn(cell);
        }
    }

    @Override
    /**
     * sends actual game info to client
     * @param game GameRound current
     */
    public void updateField(GameCell cell) throws IOException {
        this.connection.send(new Message(MessageType.FIELD_UPDATE_REQUEST, cell));
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
     * informs client that he'll be disconnected.
     * @throws IOException
     */
    public void disconnectMessage() throws IOException {
        this.connection.send(new Message(MessageType.DISCONNECT_GAME));
    }

    @Override
    /**
     * sends command to all clients to init game field.
     * @param fieldSize int size of the field.
     * @throws IOException
     */
    public void initField(int fieldSize) throws IOException {
        this.connection.send(new Message(MessageType.FIELD_INIT, fieldSize));
    }
}
