package com.pollerexpress.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class PlayerTest
{
    Player me;
    Player alsoMe;
    Player notMe;
    @Before
    public void generate()
    {
        me = new Player("Torsten");
        alsoMe = new Player("Torsten");
        alsoMe.gameId = "fakeGame";
        notMe = new Player("Jack");
    }

    @org.junit.Test
    public void equals()
    {
        assertTrue( me.equals( alsoMe) );
        assertTrue( alsoMe.equals(me) );//transitive
        assertFalse( me.equals(null)  );
        assertFalse( me.equals(notMe) );
        assertFalse( notMe.equals(me) );
    }
}