package com.shared.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;

/**
 * Abby
 * This class represents the chat history for a single game.
 * This class should be observed by the chat presenter
 * (and that's necessary for phase2).
 */
public class ChatHistory extends Observable implements Serializable
{

    ArrayList<Chat> chats;

    public ChatHistory(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public ChatHistory() {
        chats = new ArrayList<>();
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    /**
     * Adds a chat to the list of chats,
     * sorts the chats, and
     * alerts observers of the change.
     * @post a chat was added to the list
     * @post the observers of ChatHistory were notified of the change
     * @param chat The chat to be added to the history
     */
    public void addChat(Chat chat) {

        chats.add(chat);
        sortChats(chats);
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(chat);
        }
    }

    /**
     * This method sorts the chats by their timestamps,
     * putting the earlier messages at the front of the list.
     * @pre chats is nonempty
     * @return a list of Chat objects, sorted by their timestamps
     */
    private ArrayList<Chat> sortChats(ArrayList<Chat> chats) {

        Comparator<Chat> comparator = new Comparator<Chat>() {

            /* Returns a value less than 0 if this chat's timestamp
            is before the timestamp of t1. Returns a value greater
            than zero if chat's timestamp is after the timestamp of t1.
            @pre the two chat objects do not have identical timestamps.*/
            @Override
            public int compare(Chat chat, Chat t1) {
                Timestamp thisTimestamp = chat.getTimestamp();
                Timestamp givenTimestamp = t1.getTimestamp();
                if(thisTimestamp.compareTo(givenTimestamp) < 0) {
                    return -1;
                }
                else return 1;
            }
        };

        Collections.sort(chats, comparator);

        return chats;
    }

    /**
     * (DONE)
     * returns chats as strings of the form "name: message"
     * @return An arraylist of sorted, formatted strings.
     */
    public ArrayList<String> getChatsAsString() {

        ArrayList<String> chatsAsStrings = new ArrayList<>();

        chats = sortChats(chats);

        for(int i = 0; i < chats.size(); i++) {

            Chat chat = chats.get(i);

            chatsAsStrings.add(chat.toString());
        }

        return chatsAsStrings;
    }
}
