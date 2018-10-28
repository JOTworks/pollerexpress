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
     * @return A string containing the message and the sender name
     * on different lines.
     */
    public String toString() {

        StringBuilder chatDisplay = new StringBuilder();

        /*I need to find a better way of displaying the time.*/
        chatDisplay.append(message)
                .append('\n')
                .append(messageSender.getName());

        return chatDisplay.toString();
    }


    public boolean equals(Chat message2) {
        return false;
    }

    public ArrayList<Chat> sort(ArrayList<Chat> chats) {
        return null;
    }
}
