package pollerexpress.database.utilities;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.Color;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Point;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import pollerexpress.database.Database;
import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.TrainCardDao;

import static com.shared.models.Color.TRAIN.*;

public class DeckBuilder {
    Database _db;

    public DeckBuilder(Database db) {
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
        //make cities
        City atlanta = new City("Atlanta", new Point(0.0,0.0));
        City boston = new City("Boston", new Point(0.0,0.0));
        City calgary = new City("Calgary", new Point(0.0,0.0));
        City chicago = new City("Chicago", new Point(0.0,0.0));
        City dallas = new City("Dallas", new Point(0.0,0.0));
        City denver = new City("Denver", new Point(0.0,0.0));
        City duluth = new City("Duluth", new Point(0.0,0.0));
        City elpaso = new City("El Paso", new Point(0.0,0.0));
        City helena = new City("Helena", new Point(0.0,0.0));
        City houston = new City("Houston", new Point(0.0,0.0));
        City kansas = new City("Kansas City", new Point(0.0,0.0));
        City littlerock = new City("Little Rock", new Point(0.0,0.0));
        City losangeles = new City("Los Angeles", new Point(0.0,0.0));
        City miami = new City("Miami", new Point(0.0,0.0));
        City montreal = new City("Montreal", new Point(0.0,0.0));
        City nashville = new City("Nashville", new Point(0.0,0.0));
        City neworleans = new City("New Orleans", new Point(0.0,0.0));
        City newyork = new City("New York City", new Point(0.0,0.0));
        City oklahoma = new City("Oklahoma City", new Point(0.0,0.0));
        City phoenix = new City("Phoenix", new Point(0.0,0.0));
        City pittsburgh = new City("Pittsburgh", new Point(0.0,0.0));
        City portland = new City("Portland", new Point(0.0,0.0));
        City saltlake = new City("Salt Lake City", new Point(0.0,0.0));
        City sanfrancisco = new City("San Francisco", new Point(0.0,0.0));
        City santafe = new City("Santa Fe", new Point(0.0,0.0));
        City saultstemarie = new City("Sault Ste. Marie", new Point(0.0,0.0));
        City seattle = new City("Seattle", new Point(0.0,0.0));
        City toronto = new City("Toronto", new Point(0.0,0.0));
        City vancouver = new City("Vancouver", new Point(0.0,0.0));
        City winnipeg = new City("Winnipeg", new Point(0.0,0.0));

        DestinationCard[] cards = {
                new DestinationCard(losangeles, newyork, 21),
                new DestinationCard(duluth, houston, 8),
                new DestinationCard(saultstemarie, nashville, 8),
                new DestinationCard(newyork, atlanta, 6),
                new DestinationCard(portland, nashville, 17),
                new DestinationCard(vancouver, montreal, 20),
                new DestinationCard(duluth, elpaso, 10),
                new DestinationCard(toronto, miami, 10),
                new DestinationCard(portland, phoenix, 11),
                new DestinationCard(dallas, newyork, 11),
                new DestinationCard(calgary, saltlake, 7),
                new DestinationCard(calgary, phoenix, 13),
                new DestinationCard(losangeles, miami, 20),
                new DestinationCard(winnipeg, littlerock, 11),
                new DestinationCard(sanfrancisco, atlanta, 17),
                new DestinationCard(kansas, houston, 5),
                new DestinationCard(losangeles, chicago, 16),
                new DestinationCard(denver, pittsburgh, 11),
                new DestinationCard(chicago, santafe, 9),
                new DestinationCard(vancouver, santafe, 13),
                new DestinationCard(boston, miami, 12),
                new DestinationCard(chicago, neworleans, 7),
                new DestinationCard(montreal, atlanta, 9),
                new DestinationCard(seattle, newyork, 22),
                new DestinationCard(denver, elpaso, 4),
                new DestinationCard(helena, losangeles, 8),
                new DestinationCard(winnipeg, houston, 12),
                new DestinationCard(montreal, neworleans, 13),
                new DestinationCard(saultstemarie, oklahoma, 9),
                new DestinationCard(seattle, losangeles, 9),
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

    public void destroyBank(GameInfo gi) throws DatabaseException {
        //TODO: implement when we make it so people can actually finish the game
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
    }
}
