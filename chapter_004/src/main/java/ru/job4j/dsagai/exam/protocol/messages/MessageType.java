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
    //server logic
    VERSION_CHECK_REQUEST,
    VERSION_RESPONSE,
    CONNECTION_ACCEPTED,
    CONNECTION_REFUSED,
    MENU_REQUEST,
    MENU,
    ACTIVE_SESSIONS_REQUEST,
    ACTIVE_SESSIONS_RESPONSE,
    CONNECT_AS_SPECTATOR,
    CONNECT_AS_PLAYER,
    CREATE_GAME,
    DISCONNECT_GAME,
    DISCONNECT_SERVER,
    GAME_CONNECTION_SUCCESSFUL,
    GAME_CONNECTION_UNSUCCESSFUL,
    //game logic
    GAME_TURN_REQUEST,
    GAME_TURN_RESPONSE,
    FIELD_REFRESH_REQUEST,
    TEXT_MESSAGE;

}
