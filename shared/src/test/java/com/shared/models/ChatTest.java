package com.shared.models;

import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class ChatTest {

    private Long time;
    private Timestamp timestamp;
    private Player message_sender;
    private Chat chat;

    @Before
    public void setUp() throws Exception {

        // 10/26/2018 1:00:46 GMT
        time = Long.valueOf(1540515647);
        timestamp = new Timestamp(time);
        message_sender = new Player("MyName", "MyGameId");
        chat = new Chat("Hi, it's me", timestamp, message_sender);
    }

    @Test
    public void toStringTest() {

        StringBuilder ss_expected = new StringBuilder();
        ss_expected.append(timestamp)
                .append('\n')
                .append("Hi, it's me")
                .append('\n')
                .append(message_sender.getName());

        String expected = ss_expected.toString();
        String actual = chat.toString();
        assertEquals(expected, actual);

    }
}