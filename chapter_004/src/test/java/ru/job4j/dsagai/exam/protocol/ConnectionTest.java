package ru.job4j.dsagai.exam.protocol;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
import ru.job4j.dsagai.exam.server.game.InitGameSessionInfo;
import ru.job4j.dsagai.exam.server.game.conditions.WinConditionTypes;
import ru.job4j.dsagai.exam.server.game.round.GameCell;
import ru.job4j.dsagai.exam.server.game.round.GameTypes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.FutureTask;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for Connection class
 *
 * @author dsagai
 * @version 1.00
 * @since 01.03.2017
 */

public class ConnectionTest {

    private Connection connection;

    private ByteArrayInputStream in;
    private ByteArrayOutputStream out;
    private FutureTask<String> connectionFuture;
    private Message requestMessage;
    private Message responseMessage;

    @Before
    public void init() throws Exception {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream inputInit = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(inputInit);
        this.requestMessage = new Message(MessageType.CREATE_GAME, new InitGameSessionInfo(WinConditionTypes.GAMES_COUNT_CONDITION,
                3, GameTypes.TicTacThreeOnThreeCellsField, 0));
        this.responseMessage = new Message(MessageType.GAME_TURN_RESPONSE, new GameCell(1, 1));

        objectOutputStream.writeObject(this.requestMessage);

        objectOutputStream.writeObject(this.responseMessage);

        this.in = new ByteArrayInputStream(inputInit.toByteArray());
        this.out = new ByteArrayOutputStream();

        when(socket.getOutputStream()).thenReturn(this.out);
        when(socket.getInputStream()).thenReturn(this.in);

        this.connection = new Connection(socket);
        this.connectionFuture = new FutureTask<String>(this.connection);

    }

    @Test
    public void whenCloseThenStop() throws Exception {

        new Thread(this.connectionFuture).start();

        this.connection.close();
        this.connectionFuture.get();
        assertThat(this.connectionFuture.isDone(), is(true));
    }

    @Test
    public void whenSendServiceMessageThenReceiveServiceOtherwiseReceiveGame() throws Exception {
        new Thread(this.connectionFuture).start();


        Message response = null;
        Message request = null;
        do {
            response = this.connection.getResponses();
        } while (response == null);

        do {
            request = this.connection.getRequests();
        } while (request == null);
        this.connection.close();

        assertThat(request.equals(this.requestMessage), is(true));
        assertThat(response.equals(this.responseMessage), is(true));


    }


}