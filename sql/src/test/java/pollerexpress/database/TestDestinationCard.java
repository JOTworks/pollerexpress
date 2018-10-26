package pollerexpress.database;

import com.shared.models.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.utilities.DeckBuilder;

import static org.junit.Assert.*;

public class TestDestinationCard {
    Database db;
    DeckBuilder builder;
    DestinationCardDao dcDao;
    GameInfo gi;
    Player p;

    @Before
    public void up() {
        db = new Database();
        builder = db.deckBuilder;
        dcDao = db.dcDao;
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
            dcDao.deleteDeck(gi);
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
            ArrayList<DestinationCard> deck = dcDao.getDefaultDeck();
            assertEquals(30, deck.size());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testBuildGameDeck() {
        try {
            builder.makeDestinationDeck(gi);
            assertEquals(30, dcDao.getDeckSize(gi));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDrawCard() {
        DestinationCard card = null;

        try {
            //make deck and get starting deck size for comparison
            builder.makeDestinationDeck(gi);
            int deckSize = dcDao.getDeckSize(gi);

            //run drawCard()
            card = dcDao.drawCard(p);

            //check card is not null
            assertNotNull(card);

            //check deck size is 1 less than before.
            assertEquals(deckSize - 1, dcDao.getDeckSize(gi));

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetPlayerHand() {
        try{
            //make game deck
            builder.makeDestinationDeck(gi);

            //draw three cards
            DestinationCard card1 = dcDao.drawCard(p);
            DestinationCard card2 = dcDao.drawCard(p);
            DestinationCard card3 = dcDao.drawCard(p);

            //get player's hand
            ArrayList<DestinationCard> hand = dcDao.getHand(p);

            //check the player's hand contains 3 cards
            assertEquals(3, hand.size());

            //check the player's hand contains the right 3 cards.
            assertTrue(hand.contains(card1));
            assertTrue(hand.contains(card2));
            assertTrue(hand.contains(card3));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDiscard() {
        try {
            //make game deck
            builder.makeDestinationDeck(gi);

            //draw a card
            DestinationCard card = dcDao.drawCard(p);

            //get player's hand size and get deck size
            int handSize = dcDao.getHand(p).size();
            int deckSize = dcDao.getDeckSize(gi);

            //discard a card
            dcDao.discardCard(p, card);

            //get player's hand size and get deck size again
            //player's hand should be 1 less, deck size should be the same.
            assertEquals(handSize - 1, dcDao.getHand(p).size());
            assertEquals(deckSize, dcDao.getDeckSize(gi));

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testShuffle() {
        try {
            //make game deck
            builder.makeDestinationDeck(gi);

            //first, test the discard pile.
            int discardCount = dcDao.getDiscardPile(gi).size();
            assertEquals(0, discardCount);

            DestinationCard card = dcDao.drawCard(p);
            dcDao.discardCard(p, card);
            discardCount = dcDao.getDiscardPile(gi).size();
            assertEquals(1, discardCount);

            for(int i = 0; i < 5; i++) {
                card = dcDao.drawCard(p);
                dcDao.discardCard(p, card);
            }
            //test shuffle
            builder.shuffleDestinationDeck(gi);

            //you can't draw on an empty deck, and doing so should return null.

            //after shuffling, the discard pile should be empty and the deck should have increased by the former size of the discard pile.

            //you can draw a card again after the deck is shuffled.

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
