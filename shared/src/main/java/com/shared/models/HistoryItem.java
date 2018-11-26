package com.shared.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Nate
 * This class represents everything needed
 * to display a single action in the history, including
 * the player who acted and what time the action took place.
 */
public class HistoryItem implements Serializable {

    private String action;
    private Timestamp timestamp;
    private Player actionSender;

    public HistoryItem(String action, Timestamp timestamp, Player actionSender) {
        this.action = action;
        this.timestamp = timestamp;
        this.actionSender = actionSender;
    }

    public String getaction()
    {
        return action;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Player getactionSender() {
        return actionSender;
    }

    /**
     * @return A string of the form "player's name: action"
     */
    public String toString() {

        StringBuilder chatDisplay = new StringBuilder();

        chatDisplay.append(actionSender.getName())
                .append(": ")
                .append(action);

        return chatDisplay.toString();
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryItem chat = (HistoryItem) o;

        if(!(Objects.equals(timestamp, chat.timestamp))){
            return false;
        }
        if(!(Objects.equals(action, chat.action))){
            return false;
        }
        if(!(Objects.equals(actionSender, chat.actionSender))){
            return false;
        }
        return true;
    }

    public ArrayList<HistoryItem> sort(ArrayList<HistoryItem> chats) {
        return null;
    }
}
