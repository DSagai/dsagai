package ru.job4j.dsagai.exam.protocol;

import org.junit.Test;
import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * TODO: add comments
 *
 * @author dsagai
 * @version TODO: set version
 * @since 01.03.2017
 */

public class ConnectionTest {

    @Test
    public void whenCloseThenStop() throws Exception {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        Message sent = new Message(MessageType.TEXT_MESSAGE, "QQQ");
        objectOutputStream.writeObject(sent);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        Connection connection = new Connection(socket);
        FutureTask<String> connectionFuture = new FutureTask<String>(connection);
        new Thread(connectionFuture).start();

        connection.close();
        System.out.println(connectionFuture.get());
        assertThat(connectionFuture.isDone(), is(true));
    }
}