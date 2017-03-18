package ru.job4j.dsagai.exam.protocol;

import ru.job4j.dsagai.exam.protocol.messages.Message;
import ru.job4j.dsagai.exam.protocol.messages.MessageType;
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

    private final BlockingQueue<Message> requests;
    private final BlockingQueue<Message> responses;

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
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.in = new ObjectInputStream(this.socket.getInputStream());
        this.requests = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_CAPACITY);
        this.responses = new ArrayBlockingQueue<Message>(MESSAGE_QUEUE_CAPACITY);
        this.uid = (new UID()).toString();
        this.started = new Date();
    }



    @Override
    /**
     * closes connection
     */
    public void close() throws IOException {
        this.connected.set(false);
        this.in.close();
        this.out.close();
        this.socket.close();
        this.ended = new Date();
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
        }

        close();
        return String.format(MessagePropertyReader.getInstance().getString("log.connection"),
                this.uid, this.started, this.ended);
    }

    /**
     * Method sends message to client.
     * @param outgoing Message.
     * @return true if message was sent, otherwise returns false.
     */
    public boolean send(Message outgoing) throws IOException {
        boolean result = false;
        try {
            if (outgoing != null) {
                if (outgoing.getType() == MessageType.FIELD_REFRESH_REQUEST) {
                    System.out.println(outgoing.getData());
                }
                this.out.writeObject(outgoing);
                result = true;
            }
        } catch (Exception e) {
            close();
        }

        return result;
    }

    /**
     * receives next message from the socket
     * and puts it into the appropriate queue.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void receive() throws IOException, ClassNotFoundException {
        Message message = (Message)this.in.readObject();

        if (message.getType().isRequest()) {
            this.requests.offer(message);
        } else {
            this.responses.offer(message);
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
     * returns game response
     * @return next message from the queue
     */
    public Message getRequests() throws InterruptedException {
        return this.requests.take();
    }

    /**
     * returns service message
     * @return next message from the queue
     */
    public Message getResponses() throws InterruptedException {
        return this.responses.take();
    }

    /**
     *  returns address of remote client.
     * @return SocketAddress.
     */
    public SocketAddress getRemoteSocketAddress() {
        return this.socket.getRemoteSocketAddress();
    }

}
