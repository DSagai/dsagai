package ru.job4j.dsagai.exam.client.view.components.actions;

import ru.job4j.dsagai.exam.protocol.Connection;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
import ru.job4j.dsagai.exam.server.game.round.GameField;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.FutureTask;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 17.03.2017
 */
public class TestServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(6666);
        Socket socket = serverSocket.accept();

        GameField field = new GameField(3);
        field.updateCell(1, 1,1);

        Connection connection = new Connection(socket);
        new Thread(new FutureTask<String>(connection)).start();
        connection.send(new Message(MessageType.FIELD_REFRESH_REQUEST, field));
    }
}
