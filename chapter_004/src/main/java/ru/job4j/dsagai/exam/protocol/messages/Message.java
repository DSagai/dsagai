package ru.job4j.dsagai.exam.protocol.messages;



import java.io.Serializable;

/**
 * Class describes and implements socket message
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public class Message implements Serializable {
    private final MessageType type;
    private final Serializable data;

    public Message(MessageType type, Serializable data) {
        this.type = type;
        this.data = data;
    }

    public Message(MessageType type) {
        this.type = type;
        this.data = null;
    }

    /**
     * getter for type property
     * @return
     */
    public MessageType getType() {
        return type;
    }

    /**
     * getter fo date property
     * @return
     */
    public Serializable getData() {
        return data;
    }
}
