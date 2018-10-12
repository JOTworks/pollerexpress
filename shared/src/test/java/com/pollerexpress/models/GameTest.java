package com.pollerexpress.models;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameTest
{

    Game game;
    Player me;
    Player notMe;
    Player otherMe;

    @Before
    public void setUp() throws Exception
    {
        GameInfo info = new GameInfo("myGame",4);
        game = new Game(info);
        me = new Player("Torsten");
        notMe = new Player("someOneElse");
        otherMe = new Player("Torsten");
        Player[] p = {me, notMe};
        List<Player> players = new ArrayList<>(Arrays.asList(p));
        game.setPlayers(players);
    }

    @Test
    public void hasPlayer()
    {

        assertTrue( game.hasPlayer(me) );
        assertTrue( game.hasPlayer(notMe) );
        assertTrue( game.hasPlayer(otherMe) );
        //assertTrue(false);
    }

    @Test
    public void removePlayer()
    {
        assertTrue(true);
    }

    @Test
    public void getPlayerDex()
    {
        assertTrue(true);
    }


}