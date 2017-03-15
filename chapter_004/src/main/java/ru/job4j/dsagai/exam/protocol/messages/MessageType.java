package ru.job4j.dsagai.exam.protocol.messages;

import java.io.Serializable;

/**
 * defines available message types.
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public enum MessageType {
    //requests
    VERSION_CHECK_REQUEST(true),
    ACTIVE_SESSIONS_REQUEST(true),
    CONNECT_AS_SPECTATOR(true),
    CONNECT_AS_PLAYER(true),
    CREATE_GAME(true),
    DISCONNECT_GAME(true),
    DISCONNECT_SERVER(true),
    GAME_TURN_REQUEST(true),
    FIELD_REFRESH_REQUEST(true),
    TEXT_MESSAGE(true),
    //responses
    ACTIVE_SESSIONS_RESPONSE(false),
    CONNECTION_ACCEPTED(false),
    CONNECTION_REFUSED(false),
    VERSION_RESPONSE(false),
    GAME_TURN_RESPONSE(false);

    //defines does message belong to request type
    private final boolean request;

    /**
     * getter for request field.
     * @return true if message is request. Returns false if message is response.
     */
    public boolean isRequest() {
        return request;
    }

    MessageType(boolean request) {
        this.request = request;
    }
}
