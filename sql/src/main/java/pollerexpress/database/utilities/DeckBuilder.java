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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.IDatabase;
import pollerexpress.database.dao.TrainCardDao;

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
        TrainCardDao tcDao = _db.getTrainCardDao();
        //12 of each color, 14 rainbow.
        Color.TRAIN[] colors = {PURPLE, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, RAINBOW};
        for(Color.TRAIN color : colors) {
            //add twelve cards of each color
            for(int i = 0; i < 12; i++) {
                tcDao.insertIntoDefault(new TrainCard(color));
            }
        }
        //add two extra of rainbow
        tcDao.insertIntoDefault(new TrainCard(RAINBOW));
        tcDao.insertIntoDefault(new TrainCard(RAINBOW));
    }

    public void makeBank(GameInfo gi) throws DatabaseException {
        this.makeDestinationDeck(gi);
        this.makeTrainDeck(gi);
    }

    public void makeDestinationDeck(GameInfo gi) throws DatabaseException {
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

    public void makeTrainDeck(GameInfo gi) throws DatabaseException {
        TrainCardDao tcDao = _db.getTrainCardDao();

        //drop if exists and then create table
        tcDao.deleteDeck(gi);
        tcDao.createDeckTable(gi);

        //fill table
        ArrayList<TrainCard> defaultDeck = tcDao.getDefaultDeck();
        for(TrainCard card : defaultDeck) {
            tcDao.insertCard(gi, card.getId(), 0, null, 0);
        }

        //shuffle
        this.shuffleTrainDeck(gi);

        //flip five face-up cards
        tcDao.flipFaceUp(gi, 1);
        tcDao.flipFaceUp(gi, 2);
        tcDao.flipFaceUp(gi, 3);
        tcDao.flipFaceUp(gi, 4);
        tcDao.flipFaceUp(gi, 5);
    }




    public void shuffleDestinationDeck(GameInfo gi) throws DatabaseException {
        String table = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        DestinationCardDao dcDao = _db.getDestinationCardDao();

        this.shuffle(table, dcDao.getDiscardPile(gi));
    }

    public void shuffleTrainDeck(GameInfo gi) throws DatabaseException {
        String table = "\"TRAIN_DECK_" + gi.getId() + "\"";
        TrainCardDao tcDao = _db.getTrainCardDao();

        this.shuffle(table, tcDao.getDiscardPile(gi));
    }

    private void shuffle(String tablename, ArrayList<String> discardPile) throws DatabaseException {
        String GET_TOP_POSITION = "SELECT POSITION\n FROM " + tablename + "\n ORDER BY POSITION DESC\n LIMIT 1";
        String UPDATE_CARD = "UPDATE " + tablename + "\n SET POSITION = ?, PLAYER = NULL\n WHERE CARD_ID = ?";
        _db.open();

        Collections.shuffle(discardPile);

        try {
            int top = 0;
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_TOP_POSITION);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                top = rs.getInt("POSITION");
            }
            rs.close();
            stmnt.close();

            //top is where the shuffle positions will START, so I have to add one.
            top += 1;

            for (int i = 0; i < discardPile.size(); i++) {
                stmnt = _db.getConnection().prepareStatement(UPDATE_CARD);
                stmnt.setInt(1, i + top);
                stmnt.setString(2, discardPile.get(i));
                stmnt.execute();
                stmnt.close();
            }
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
    }
}
