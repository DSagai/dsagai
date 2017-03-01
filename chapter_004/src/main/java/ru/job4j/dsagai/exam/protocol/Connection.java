package ru.job4j.dsagai.exam.protocol;

import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.util.MessagePropertyReader;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;
import java.rmi.server.UID;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Wrapper for Socket for convenient writing and reading Message objects
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public class Connection implements Closeable, Callable<String> {
    private static final int MESSAGE_QUEUE_CAPACITY = 10;

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final BlockingQueue<Message> gameResponse;
    private final BlockingQueue<Message> serviceResponse;
    private final BlockingQueue<Message> outgoingMessages;

    private final String uid;
    private final Date started;
    private Date ended;
    private final AtomicBoolean connected = new AtomicBoolean(true);




    /**
     * default constructor
     * @param socket Socket.
     * @throws IOException
     */
    public Connection(Socket socket)  throws IOException {
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.gameResponse = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_CAPACITY);
        this.serviceResponse = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_CAPACITY);
        this.outgoingMessages = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_CAPACITY);
        this.uid = (new UID()).toString();
        this.started = new Date();
    }



    @Override
    /**
     * closes connection
     */
    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
        this.ended = new Date();
        this.connected.set(false);
    }

    @Override
    /**
     * main loop for the task
     * listens socket for incoming messages
     * and sends outgoing messages.
     *
     */
    public String call() throws Exception {

        while (!Thread.currentThread().isInterrupted() && this.connected.get()){
            receive();
            send();
        }

        close();
        return String.format(MessagePropertyReader.getInstance().getString("log.connection"),
                this.uid, this.started, this.ended);
    }

    /**
     * sends next message from the queue.
     * @throws IOException
     */
    private void send() throws IOException, InterruptedException {
        Message outgoing = this.outgoingMessages.poll();
        if (outgoing != null) {
            this.out.writeObject(outgoing);
        }
    }

    /**
     * receives next message from the socket
     * and puts it into the appropriate queue.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void receive() throws IOException, ClassNotFoundException {
        Message response = (Message)this.in.readObject();
        switch (response.getType()){
            case GAME_TURN_RESPONSE: this.gameResponse.offer(response);
            break;
            default: this.serviceResponse.offer(response);
            break;
        }
    }

    /**
     * getter for uid field.
     * @return
     */
    public String getUid() {
        return uid;
    }

    /**
     * puts new message into
     * outgoing queue
     * @param message
     */
    public void pushMessage(Message message) {
        this.outgoingMessages.offer(message);
    }

    /**
     * returns game response
     * @return next message from the queue
     */
    public Message getGameResponse() {
        return this.gameResponse.poll();
    }

    /**
     * returns service message
     * @return next message from the queue
     */
    public Message getServiceResponse() {
        return this.serviceResponse.poll();
    }

    /**
     *  returns address of remote client.
     * @return SocketAddress.
     */
    public SocketAddress getRemoteSocketAddress() {
        return this.socket.getRemoteSocketAddress();
    }

}
