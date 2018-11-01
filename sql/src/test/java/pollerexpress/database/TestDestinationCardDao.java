package pollerexpress.database;

import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.utilities.DeckBuilder;

import static org.junit.Assert.*;

public class TestDestinationCardDao {
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


            builder.makeDefaultDecks();
            db.close(true);


        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void down() {
        try
        {
            db.open();
            dcDao.deleteDeck(gi);
            db.close(true);
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
            db.open();
            ArrayList<DestinationCard> deck = dcDao.getDefaultDeck();
            assertEquals(30, deck.size());
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            db.close(false);
        }
    }

    @Test
    public void testBuildGameDeck() {
        try {
            db.open();
            builder.makeDestinationDeck(gi);
            assertEquals(30, dcDao.getDeckSize(gi));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            db.close(false);
        }
    }

    @Test
    public void testDrawCard() {
        DestinationCard card = null;

        try {
            //make deck and get starting deck size for comparison
            db.open();
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
        finally
        {
            db.close(false);
        }
    }

    @Test
    public void testGetPlayerHand() {
        try{
            db.open();
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
        finally
        {
            db.close(false);
        }
    }

    @Test
    public void testDiscard() {
        try {
            db.open();
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
        finally
        {
            db.close(true);
        }
    }

    @Test
    public void testShuffle() {
        try {
            db.open();
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
            //builder.shuffleDestinationDeck(gi);

            //empty the deck
            int deckSize = dcDao.getDeckSize(gi);
            for(int i = 0; i < deckSize; i++) {
                card = dcDao.drawCard(p);
                dcDao.discardCard(p, card);
            }
            assertEquals(0, dcDao.getDeckSize(gi));

            //you can't draw on an empty deck, and doing so should return null.
            assertNull(dcDao.drawCard(p));

            //after shuffling, the discard pile should be empty and the deck should have increased by the former size of the discard pile.
            int formerDiscardCount = dcDao.getDiscardPile(gi).size();
            builder.shuffleDestinationDeck(gi);
            assertEquals(0, dcDao.getDiscardPile(gi).size());
            assertEquals(formerDiscardCount, dcDao.getDeckSize(gi));

            //test previous, this time with there still being some cards in the deck.
            for(int i = 0; i < 15; i++) {
                card = dcDao.drawCard(p);
                dcDao.discardCard(p, card);
            }
            formerDiscardCount = dcDao.getDiscardPile(gi).size();
            int formerDeckCount = dcDao.getDeckSize(gi);
            builder.shuffleDestinationDeck(gi);
            assertEquals(0, dcDao.getDiscardPile(gi).size());
            assertEquals(formerDeckCount + formerDiscardCount, dcDao.getDeckSize(gi));

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            db.close(false);
        }
    }
}
