package ru.job4j.dsagai.exam.exceptions;

/**
 * Exception which rises when response message type differs expected one
 *
 * @author dsagai
 * @version 1.00
 * @since 20.02.2017
 */

public class UnexpectedMessageType extends Exception {

    public UnexpectedMessageType(String message) {
        super(message);
    }
}
