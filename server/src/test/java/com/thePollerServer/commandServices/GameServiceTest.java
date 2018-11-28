package com.thePollerServer.commandServices;

import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.Player;
import com.thePollerServer.services.GameService;
import com.thePollerServer.utilities.Factory;
import com.thePollerServer.utilities.RealDatabaseFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class GameServiceTest
{

    Player me;
    Player otherMe;
    List<DestinationCard> tooMany;
    List<DestinationCard> shouldWork;
    List<DestinationCard> None;
    @Before
    public void setUp() throws Exception
    {
        Factory.setFactory(new GSDFactory());

        me = new Player("Torsten");
        otherMe = new Player("Jack");
        DestinationCard [] cards = {new DestinationCard(null,null,20), new DestinationCard(null, null,0), new DestinationCard(null, null, 30)};
        tooMany = Arrays.asList(cards);
        shouldWork = new ArrayList<>();
        int count = 0;
        for(DestinationCard d : tooMany)
        {
            count +=1;
            shouldWork.add(d);
            if(count == 2)
            {
                break;
            }
        }
        None = new ArrayList<>();
    }

    @After
    public void tearDown() throws Exception
    {
        Factory.setFactory(new RealDatabaseFactory());
    }

    @Test
    public void discardDestinationCards() throws Exception
    {
        GameService gs = new GameService();
        assertFalse(gs.discardDestinationCards(me, tooMany) );
        assertTrue(gs.discardDestinationCards(me, shouldWork));
        assertTrue(gs.discardDestinationCards(me, None));

        assertFalse(gs.discardDestinationCards(otherMe, shouldWork) );
    }
}