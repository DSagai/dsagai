package ru.job4j.dsagai.exam.client.view.components.actions;

import ru.job4j.dsagai.exam.protocol.Connection;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.server.game.round.GameField;

import java.net.Socket;
import java.util.concurrent.FutureTask;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 17.03.2017
 */
public class TestClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",6666);
        Connection connection = new Connection(socket);
        new Thread(new FutureTask<String>(connection)).start();
        Message message = connection.getRequests();
        GameField gameField = (GameField) message.getData();
        System.out.println(message.getType());

    }
}
