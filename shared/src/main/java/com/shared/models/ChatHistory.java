package com.shared.models;

import java.util.ArrayList;

/**
 * Abby
 * This class represents the chat history for a single game.
 */
public class ChatHistory {

    ArrayList<ChatMessage> chats;

    public ChatHistory(ArrayList<ChatMessage> chats) {
        this.chats = chats;
    }

    public ChatHistory() {
    }

    public ArrayList<ChatMessage> getChats() {
        return chats;
    }

    public void setChats(ArrayList<ChatMessage> chats) {
        this.chats = chats;
    }

    /**
     * This method sorts the chatsMessages by their timestamps,
     * putting the earlier messages at the front of the list.
     * If two ChatMessages have the same timestamp, then we
     * sort, alphabetically, by the name of the sender.
     * @return a list of ChatMessage objects, sorted by their timestamps
     *          or null if the arraylist of ChatMessages is empty.
     */
    public ArrayList<ChatMessage> sortChats() {

        if(chats.size() == 0) { return null; }

        /*get the timestamp of each and use a compare method*/

        return null;
    }
}
