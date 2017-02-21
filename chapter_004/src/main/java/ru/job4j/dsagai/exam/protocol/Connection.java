package ru.job4j.dsagai.exam.protocol;

import ru.job4j.dsagai.exam.protocol.messages.Message;

import java.io.*;
import java.net.Socket;

/**
 * Wrapper for Socket for convenient writing and reading Message objects
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public class Connection implements Closeable {
    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;


    /**
     * default constructor
     * @param socket Socket.
     * @throws IOException
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * method sends Message object into socket
     * @param message Message
     * @throws IOException
     */
    public void send(Message message) throws IOException{
        out.writeObject(message);
    }

    /**
     * method reads Message object from socket
     * @return Message.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Message receive() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }

    @Override
    /**
     * closes connection
     */
    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
    }
}
