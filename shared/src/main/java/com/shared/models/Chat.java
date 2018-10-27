package com.shared.models;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Abby
 * This class represents everything needed
 * to display a single chat message, including
 * the player who sent it and what time it was sent.
 */
public class Chat {

    private String message;
    private Timestamp timestamp;
    private Player messageSender;

    public Chat(String message, Timestamp timestamp, Player messageSender) {
        this.message = message;
        this.timestamp = timestamp;
        this.messageSender = messageSender;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Player getMessageSender() {
        return messageSender;
    }

    /**
     * @return A string of the form "sender name: message"
     */
    public String toString() {

        StringBuilder chatDisplay = new StringBuilder();

        chatDisplay.append(messageSender.getName())
                .append(": ")
                .append(message);

        return chatDisplay.toString();
    }

}
