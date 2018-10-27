package com.shared.models;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Abby
 * This class represents the chat history for a single game.
 * This class should be observed by the chat presenter
 * (and that's necessary for phase2).
 */
public class ChatHistory extends Observable {

    ArrayList<Chat> chats;

    public ChatHistory() {
        chats = new ArrayList<>();
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    /**
     * Adds a chat, sorts the chats, and notifies
     * observers that the chat history has changed.
     * @param chat
     */
    public void addChat(Chat chat) {

        synchronized (this)
        {
            this.getChats().add(chat);
            sortChats(chats);
            this.setChanged();
            notifyObservers(this.chats);
        }
    }

    /**
     * This method sorts the chatsMessages by their timestamps,
     * putting the earlier messages at the front of the list.
     * If two ChatMessages have the same timestamp, then we
     * sort, alphabetically, by the name of the sender.
     * @return a list of Chat objects, sorted by their timestamps
     *          or null if the arraylist of ChatMessages is empty.
     */
    private ArrayList<Chat> sortChats(ArrayList<Chat> chats) {

        if(chats.size() == 0) { return null; }


        for(int i = 0; i < chats.size(); i++) {


        }

        // for now, just return the unsorted class.
        return chats;
    }

    /**
     * (DONE)
     * Sorts the chats by timestamp, then returns
     * them as strings of the form "name: message"
     * @return An arraylist of sorted, formatted strings.
     */
    public ArrayList<String> getChatsAsString() {

        ArrayList<String> chatsAsStrings = new ArrayList<>();

        chats = sortChats(chats);

        for(int i = 0; i < chats.size(); i++) {

            Chat chat = chats.get(i);

            StringBuilder ss = new StringBuilder();
            ss.append(chat.getMessageSender().getName())
                    .append(": ")
                    .append(chat.getMessage());

            chatsAsStrings.add(ss.toString());
        }

        return chatsAsStrings;
    }
}
