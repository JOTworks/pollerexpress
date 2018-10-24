package pollerexpress.database;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.DestinationCard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.IDatabase;
import pollerexpress.database.utilities.DeckBuilder;

import static org.junit.Assert.*;

public class TestDestinationCard {

    @Test
    public void test() {
        assertTrue(true);
        IDatabase db = new Database();
        try {
            ((Database) db).open();
        } catch (DatabaseException e) {
            System.out.println("Database won't open :)))");
        }
        DeckBuilder builder = new DeckBuilder(db);
        DestinationCardDao dcDao = new DestinationCardDao(db);

        //test the the default Destination Deck was filled properly in the database, or that it even exists.
        /* TEST 1 */
        try {
            ArrayList<DestinationCard> deck = dcDao.getDefaultDeck();
            System.out.println(deck.size());
            assertEquals(30, deck.size());
        } catch(Exception e) {
            System.out.println("TestDestinationCard Test1 threw an exception.");
            fail(e.getMessage());
        }
    }
}
