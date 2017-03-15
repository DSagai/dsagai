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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Message message = (Message) o;

        if (type != message.type)
            return false;
        return data != null ? data.equals(message.data) : message.data == null;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
