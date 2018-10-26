package com.shared.models;

import java.sql.Timestamp;

/**
 * Abby
 * This class represents everything needed
 * to display a single chat message, including
 * the player who sent it and what time it was sent.
 */
public class ChatMessage {

    private String message;
    private Timestamp timestamp;
    private Player messageSender;

    public ChatMessage(String message, Timestamp timestamp, Player messageSender) {
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
     * @return A string containing timestamp, message, and sender name,
     * separated by new lines.
     */
    public String toString() {

        StringBuilder chatDisplay = new StringBuilder();

        /*I need to find a better way of displaying the time.*/
        chatDisplay.append(timestamp)
                .append('\n')
                .append(message)
                .append('\n')
                .append(messageSender.getName());

        return chatDisplay.toString();
    }

}
