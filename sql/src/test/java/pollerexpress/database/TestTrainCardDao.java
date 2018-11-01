package pollerexpress.database;

import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.cardsHandsDecks.TrainCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import pollerexpress.database.dao.TrainCardDao;
import pollerexpress.database.utilities.DeckBuilder;

import static org.junit.Assert.*;

public class TestTrainCardDao {
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
        try {
            db.open();
            tcDao.deleteDeck(gi);
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
    public void testBuildDefaultDeck() {
        //test the the default Train Deck was filled properly in the database, or that it even exists.
        try {
            db.open();
            ArrayList<TrainCard> deck = tcDao.getDefaultDeck();
            assertEquals(110, deck.size());
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
            builder.makeTrainDeck(gi);
            assertEquals(105, tcDao.getDeckSize(gi));
            assertEquals(5, tcDao.getFaceUp(gi).length);
            assertFalse(Arrays.asList(tcDao.getFaceUp(gi)).contains(null));
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
       try {
           db.open();
            //make deck and get starting deck size for comparison
            builder.makeTrainDeck(gi);
            int deckSize = tcDao.getDeckSize(gi);

            //run drawCard()
            TrainCard card = tcDao.drawCard(p);

            //check card is not null
            assertNotNull(card);

            //check deck size is 1 less than before.
            assertEquals(deckSize - 1, tcDao.getDeckSize(gi));

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
            //make game deck
            db.open();
            builder.makeTrainDeck(gi);

            //draw three cards
            TrainCard card1 = tcDao.drawCard(p);
            TrainCard card2 = tcDao.drawCard(p);
            TrainCard card3 = tcDao.drawCard(p);

            //get player's hand
            ArrayList<TrainCard> hand = tcDao.getHand(p);

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
            //make game deck
            db.open();
            builder.makeTrainDeck(gi);

            //draw a card
            TrainCard card = tcDao.drawCard(p);

            //get player's hand size and get deck size
            int handSize = tcDao.getHand(p).size();
            int deckSize = tcDao.getDeckSize(gi);

            //discard a card
            tcDao.discardCard(p, card);

            //get player's hand size and get deck size again
            //player's hand should be 1 less, deck size should be the same.
            assertEquals(handSize - 1, tcDao.getHand(p).size());
            assertEquals(deckSize, tcDao.getDeckSize(gi));

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
    public void testFaceUp() {
        try {
            //make game deck
            db.open();
            builder.makeTrainDeck(gi);

            //there should be five faceup cards
            assertEquals(5, tcDao.getFaceUp(gi).length);
            assertFalse(Arrays.asList(tcDao.getFaceUp(gi)).contains(null));
            int deckSize = tcDao.getDeckSize(gi);

            //get a faceup card
            TrainCard card = tcDao.drawFaceUp(p,3);

            //should return null if you try for an index lower than 1 or higher than 5

            //there should STILL be five faceup cards, but the deck should be one less and the one you have shouldn't be in it, but should be in the player's hand.
            assertEquals(5, tcDao.getFaceUp(gi).length);
            assertFalse(Arrays.asList(tcDao.getFaceUp(gi)).contains(null));
            assertEquals(deckSize - 1, tcDao.getDeckSize(gi));
            assertFalse(Arrays.asList(tcDao.getFaceUp(gi)).contains(card));
            assertTrue(tcDao.getHand(p).contains(card));
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
    public void testShuffle() {
        try {
            db.open();
            //make game deck
            builder.makeTrainDeck(gi);

            //first, test the discard pile.
            int discardCount = tcDao.getDiscardPile(gi).size();
            assertEquals(0, discardCount);

            TrainCard card = tcDao.drawCard(p);
            tcDao.discardCard(p, card);
            discardCount = tcDao.getDiscardPile(gi).size();
            assertEquals(1, discardCount);

            for(int i = 0; i < 5; i++) {
                card = tcDao.drawCard(p);
                tcDao.discardCard(p, card);
            }
            //test shuffle
            //builder.shuffleTrainDeck(gi);

            //empty the deck
            int deckSize = tcDao.getDeckSize(gi);
            for(int i = 0; i < deckSize; i++) {
                card = tcDao.drawCard(p);
                tcDao.discardCard(p, card);
            }
            assertEquals(0, tcDao.getDeckSize(gi));

            //you can't draw on an empty deck, and doing so should return null.
            assertNull(tcDao.drawCard(p));

            //after shuffling, the discard pile should be empty and the deck should have increased by the former size of the discard pile.
            int formerDiscardCount = tcDao.getDiscardPile(gi).size();
            builder.shuffleTrainDeck(gi);
            assertEquals(0, tcDao.getDiscardPile(gi).size());
            assertEquals(formerDiscardCount, tcDao.getDeckSize(gi));

            //test previous, this time with there still being some cards in the deck.
            for(int i = 0; i < 15; i++) {
                card = tcDao.drawCard(p);
                tcDao.discardCard(p, card);
            }
            formerDiscardCount = tcDao.getDiscardPile(gi).size();
            int formerDeckCount = tcDao.getDeckSize(gi);
            builder.shuffleTrainDeck(gi);
            assertEquals(0, tcDao.getDiscardPile(gi).size());
            assertEquals(formerDeckCount + formerDiscardCount, tcDao.getDeckSize(gi));

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
