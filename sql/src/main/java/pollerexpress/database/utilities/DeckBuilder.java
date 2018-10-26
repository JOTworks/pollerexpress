package pollerexpress.database.utilities;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.Color;
import com.shared.models.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Point;
import com.shared.models.TrainCard;
import com.shared.models.interfaces.IDatabaseFacade;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.IDatabase;

import static com.shared.models.Color.TRAIN.*;

public class DeckBuilder {
    IDatabase _db;

    public DeckBuilder(IDatabase db) {
        this._db = db;
    }

    /**
     * Creates the default decks.
     * This function should only be called when the database is being built.
     * Otherwise the decks should already exist.
     */
    public void makeDefaultDecks() throws DatabaseException, SQLException {
        DestinationCardDao dcDao = _db.getDestinationCardDao();
        City city = new City("North Pole", new Point(0.0,0.0));
        DestinationCard[] cards = {
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
                new DestinationCard(city, city, 1),
        };
        for(DestinationCard card : cards) {
            dcDao.insertIntoDefault(card);
        }

        //do same thing for train cards...
        //12 of each color, 14 rainbow.
        Color.TRAIN[] colors = {PURPLE, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, RAINBOW};
        for(Color.TRAIN color : colors) {
            //add twelve cards of each color
            //tcDao.insertIntoDefault(new TrainCard(color));
        }
        //add two extra of rainbow
    }

    public void makeDestinationDeck(GameInfo gi) throws DatabaseException, SQLException {
        DestinationCardDao dcDao = _db.getDestinationCardDao();

        //drop if exists and then create table
        dcDao.deleteDeck(gi);
        dcDao.createDeckTable(gi);

        //fill table
        ArrayList<DestinationCard> defaultDeck = dcDao.getDefaultDeck();
        for(DestinationCard card : defaultDeck) {
            dcDao.insertCard(gi, card.getId(), 0, null);
        }


        //shuffle
        this.shuffleDestinationDeck(gi);
    }

    public void makeTrainDeck(GameInfo gi) {
        //implement later
    }




    public void shuffleDestinationDeck(GameInfo gi) throws DatabaseException, SQLException {
        //implement later; calls this.shuffle()
        String table = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        DestinationCardDao dcDao = _db.getDestinationCardDao();

        //check the deck size; only shuffle if it's empty.
        int decksize = dcDao.getDeckSize(gi);
        if(decksize > 0) {
            System.out.println("You can't shuffle yet, the deck isn't empty!");
            return;
        }

        this.shuffle(table, dcDao.getDiscardPile(gi));
    }

    public void shuffleTrainDeck(GameInfo gi) {
        //implement later; calls this.shuffle()
    }

    private void shuffle(String tablename, ArrayList<String> discardPile) throws DatabaseException, SQLException {
        System.out.println("every day i'm shuffling");
        String UPDATE_CARD = "UPDATE " + tablename + "\n SET POSITION = ?, PLAYER = NULL\n WHERE CARD_ID = ?";
        _db.open();
        System.out.println(discardPile.size());
        System.out.println(discardPile.get(0));
        Collections.shuffle(discardPile);
        System.out.println(discardPile.size());
        System.out.println(discardPile.get(0));

        PreparedStatement stmnt;
        for(int i = 0; i < discardPile.size(); i++) {
            stmnt = _db.getConnection().prepareStatement(UPDATE_CARD);
            stmnt.setInt( 1, i + 1 );
            stmnt.setString( 2, discardPile.get(i));
            stmnt.execute();
            stmnt.close();
        }

        _db.close(true);
    }
}
