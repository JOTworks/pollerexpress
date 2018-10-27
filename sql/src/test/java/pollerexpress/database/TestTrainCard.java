package pollerexpress.database;

import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.TrainCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import pollerexpress.database.dao.TrainCardDao;
import pollerexpress.database.utilities.DeckBuilder;

import static org.junit.Assert.*;

public class TestTrainCard {
    Database db;
    DeckBuilder builder;
    TrainCardDao tcDao;
    GameInfo gi;
    Player p;

    @Before
    public void up() {
        db = new Database();
        builder = db.deckBuilder;
        tcDao = db.tcDao;
        gi = new GameInfo("Game",3);
        p = new Player("username", gi.getId());
        try {
            db.open();

            db.deleteTables();
            db.createTables();

            db.close(true);

            builder.makeDefaultDecks();

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void down() {
        try {
            //tcDao.deleteDeck(gi);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testBuildDefaultDeck() {
        //test the the default Destination Deck was filled properly in the database, or that it even exists.
        try {
            ArrayList<TrainCard> deck = tcDao.getDefaultDeck();
            assertEquals(110, deck.size());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testBuildGameDeck() {
        try {
            builder.makeTrainDeck(gi);
            assertEquals(110, tcDao.getDeckSize(gi));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDrawCard() {
        //
    }

    @Test
    public void testGetPlayerHand() {
        //
    }

    @Test
    public void testDiscard() {
        //
    }

    @Test
    public void testShuffle() {
        //
    }
}
