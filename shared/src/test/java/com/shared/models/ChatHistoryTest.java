package com.shared.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class ChatHistoryTest {

    private ChatHistory chatHistory;

    @Before
    public void setUp() throws Exception {

        chatHistory = new ChatHistory();
    }

    @After
    public void tearDown() throws Exception {

        chatHistory = null;
    }

    @Test
    public void addChat() {

        Player player = new Player("name", "gameid");

        // Timestamps separated by a millisecond
        Timestamp timestamp1 = new Timestamp(1);
        Timestamp timestamp2 = new Timestamp(2);
        Timestamp timestamp3 = new Timestamp(3);
        Chat chat1 = new Chat("first chat",timestamp1, player);
        Chat chat2 = new Chat("second chat",timestamp2, player);
        Chat chat3 = new Chat("third chat",timestamp3, player);

        chatHistory.addChat(chat3);
        chatHistory.addChat(chat1);
        chatHistory.addChat(chat2);

        /*
        * These asserts check two things: 1) that the chats have been added and
        * 2) that the chats have been properly sorted by their timestamps. */
        assertEquals(timestamp1, chatHistory.getChats().get(0).getTimestamp());
        assertEquals(timestamp2, chatHistory.getChats().get(1).getTimestamp());
        assertEquals(timestamp3, chatHistory.getChats().get(2).getTimestamp());
    }

//    @Test
//    public void getChatsAsString() {
//    }
}