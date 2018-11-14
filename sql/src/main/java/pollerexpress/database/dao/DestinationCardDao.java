package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Point;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Model for DestinationCard
 * made by Morgan Hunt
 * For the purpose of accessing and updating Destination Deck and Destination Hand information stored
 * in the database while containing the access to the deck tables to only one class
 */
public class DestinationCardDao {
    private IDatabase _db;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS <TABLE_NAME>\n" +
                                                "(`CARD_ID` TEXT NOT NULL PRIMARY KEY, `POSITION` INT, `PLAYER` TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS <TABLE_NAME>";
    public static final String INSERT_CARD = "INSERT INTO <TABLE_NAME>(CARD_ID, POSITION, PLAYER)\n VALUES(?,?,?)";
    public static final String INSERT_DEFAULT_CARD = "INSERT INTO DEFAULT_DESTINATION_DECK(CARD_ID, CITY_1, CITY_2, POINTS)\n" +
                                                "VALUES(?,?,?,?)";
    public static final String SELECT_TOP_CARD = "SELECT DEF.CARD_ID, DEF.CITY_1, DEF.CITY_2, DEF.POINTS\n" +
                                                "FROM DEFAULT_DESTINATION_DECK AS DEF\n" +
                                                "LEFT JOIN <TABLE_NAME> AS DECK\n ON DECK.CARD_ID = DEF.CARD_ID\n" +
                                                "WHERE POSITION != 0\n ORDER BY POSITION ASC\n LIMIT 1";
    public static final String SELECT_HAND = "SELECT DEF.CARD_ID, DEF.CITY_1, DEF.CITY_2, DEF.POINTS\n" +
                                                "FROM <TABLE_NAME> AS DECK\n" +
                                                "LEFT JOIN DEFAULT_DESTINATION_DECK AS DEF\n ON DECK.CARD_ID = DEF.CARD_ID\n" +
                                                "WHERE PLAYER = ?";
    public static final String SELECT_DISCARD = "SELECT CARD_ID\n FROM <TABLE_NAME>\n WHERE POSITION = 0 AND PLAYER IS NULL";
    public static final String UPDATE_CARD = "UPDATE <TABLE_NAME>\n SET POSITION = ?, PLAYER = ?\n WHERE CARD_ID = ?";
    public static final String SELECT_CARD = "SELECT CARD_ID, CITY_1, CITY_2, POINTS\n" +
                                                "FROM DEFAULT_DESTINATION_DECK WHERE CARD_ID = ?";
    public static final String SELECT_ALL_DEFAULT = "SELECT *\n FROM DEFAULT_DESTINATION_DECK";
    public static final String COUNT_GAME_DECK = "SELECT COUNT(*)\n FROM <TABLE_NAME> WHERE POSITION != 0";

    /**
     *
     * @param db
     */
    public DestinationCardDao(IDatabase db)
    {
        this._db = db;
    }

    /**
     * @pre database exists
     * @pre given game's destination deck table does not already exist
     * @post given game now has a destination deck table
     * @param gi
     * @throws DatabaseException
     */
    public void createDeckTable(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        String CREATE_DECK = CREATE_TABLE.replace("<TABLE_NAME>",TABLE_NAME);

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(CREATE_DECK);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
    }

    /**
     * @pre database exists
     * @post given game's destination deck table does not exist.
     * @param gi
     * @throws DatabaseException
     */
    public void deleteDeck(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        String DELETE_DECK = DROP_TABLE.replace("<TABLE_NAME>",TABLE_NAME);

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DELETE_DECK);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
    }

    /**
     * @pre game deck exists
     * @pre game deck does not already have card with given id
     * @post game deck has card
     * @param gi
     * @param cardId
     * @param position
     * @param player
     * @throws DatabaseException
     */
    public void insertCard(GameInfo gi, String cardId, int position, String player) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        String INSERT = INSERT_CARD.replace("<TABLE_NAME>",TABLE_NAME);

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT);
            stmnt.setString(1, cardId);
            stmnt.setInt(2, position);
            stmnt.setString(3, player);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
    }

    /**
     * @pre game deck exists
     * @pre game deck contains card with given id
     * @post game deck's card with given id correlates with information passed in
     * @param gi
     * @param card
     * @param position
     * @param player
     * @throws DatabaseException
     */
    public void updateCard(GameInfo gi, String card, int position, String player) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        String UPDATE = UPDATE_CARD.replace("<TABLE_NAME>",TABLE_NAME);
        //_db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE);
            stmnt.setInt(1, position);
            stmnt.setString(2, player);
            stmnt.setString(3, card);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
    }

    /**
     * @pre card with given id has been inserted into the default deck table
     * @post everything is still the same
     * @param id of DestinationCard
     * @return DestinationCard if exists in database, else null
     * @throws DatabaseException
     */
    public DestinationCard getCard(String id) throws DatabaseException {
        DestinationCard card = null;
        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_CARD);
            stmnt.setString(1, id);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                City city1 = new City(rs.getString("CITY_1"), new Point(0.0,0.0));
                City city2 = city1;
                card = new DestinationCard(rs.getString("CARD_ID"), city1, city2, rs.getInt("POINTS"));
            }
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
        return card;
    }

    /**
     * @pre player is in game
     * @pre player's game has a destination deck
     * @pre game deck size is not 0
     * @post game deck size is 1 less than it was in precondition
     * @post card at top position is now attached to given player
     * @param player
     * @return TrainCard, if deck is not empty; null if deck is empty.
     * @throws DatabaseException
     */
    public DestinationCard drawCard(Player player) throws DatabaseException {
        //get card
        DestinationCard card = null;
        String TABLE_NAME = "\"DESTINATION_DECK_" + player.getGameId() + "\"";
        String GET_TOP_CARD = SELECT_TOP_CARD.replace("<TABLE_NAME>",TABLE_NAME);

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_TOP_CARD);
            ResultSet rs = stmnt.executeQuery();
            if (rs.next()) {
                //TODO: actually fetch the cities rather than making dummy ones.
                City city1 = new City("North Pole", new Point(0.1, 0.1));
                City city2 = city1;
                card = new DestinationCard(rs.getString("CARD_ID"), city1, city2, rs.getInt("POINTS"));
            }
            rs.close();
            stmnt.close();

            if(card != null) {
                //update deck to show card has been drawn, and by who.
                String UPDATE_DECK = UPDATE_CARD.replace("<TABLE_NAME>", TABLE_NAME);

                stmnt = _db.getConnection().prepareStatement(UPDATE_DECK);
                stmnt.setInt(1, 0);
                stmnt.setString(2, player.getName());
                stmnt.setString(3, card.getId());
                stmnt.execute();
                stmnt.close();
            }
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);//why would we commit if there was/wasn't an exception...
        return card;
    }

    /**
     * @pre player is in game
     * @pre player's game has a destination deck
     * @post nothing has changed
     * @param player
     * @return ArrayList of DestinationCards in the player's hand; size may be 0 if no cards are yet drawn
     * @throws DatabaseException
     */
    public ArrayList<DestinationCard> getHand(Player player) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + player.getGameId() + "\"";
        String GET_HAND = SELECT_HAND.replace("<TABLE_NAME>",TABLE_NAME);
        ArrayList<DestinationCard> hand = new ArrayList<>();

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_HAND);
            stmnt.setString(1, player.getName());
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                //TODO: refactor getting cities in this function
                City city1 = new City(rs.getString("CITY_1"), new Point(0.0, 0.0));
                City city2 = new City(rs.getString("CITY_2"), new Point(0.0, 0.0));
                hand.add(new DestinationCard(rs.getString("CARD_ID"), city1, city2, rs.getInt("POINTS")));
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
        return hand;
    }

    /**
     * @pre player is in game
     * @pre player's game has a destination deck
     * @pre given player owns given card
     * @post player no longer owns card
     * @post card is marked as in the discard pile
     * @param player
     * @param card
     * @throws DatabaseException
     */
    public void discardCard(Player player, DestinationCard card) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + player.getGameId() + "\"";
        String UPDATE = UPDATE_CARD.replace("<TABLE_NAME>",TABLE_NAME);

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE);
            stmnt.setInt(1, 0);
            stmnt.setString(2, null);
            stmnt.setString(3, card.getId());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
    }

    /**
     * @pre game deck exists
     * @post nothing has changed
     * @param gi
     * @return
     * @throws DatabaseException
     */
    public int getDeckSize(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        String GET_COUNT = COUNT_GAME_DECK.replace("<TABLE_NAME>",TABLE_NAME);
        int count = 0;

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_COUNT);
            ResultSet rs = stmnt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("COUNT(*)");
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
        return count;
    }

    /**
     * @pre game deck exists
     * @post nothing has changed
     * @param gi
     * @return
     * @throws DatabaseException
     */
    public ArrayList<String> getDiscardPile(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"DESTINATION_DECK_" + gi.getId() + "\"";
        String GET_DISCARD = SELECT_DISCARD.replace("<TABLE_NAME>",TABLE_NAME);
        ArrayList<String> discardPile = new ArrayList<>();

        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_DISCARD);
            ResultSet rs = stmnt.executeQuery();
            while (rs.next()) {
                discardPile.add(rs.getString("CARD_ID"));
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
        return discardPile;
    }

    /*
     * --------------------------------------------------------
     * DEFAULT DECK FUNCTIONS
     * --------------------------------------------------------
     */

    /**
     * @pre database exists
     * @pre default deck does not already have card with given id
     * @post default deck has card
     * @param card
     * @throws DatabaseException
     */
    public void insertIntoDefault(DestinationCard card) throws DatabaseException {
        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_DEFAULT_CARD);
            stmnt.setString(1, card.getId());
            stmnt.setString(2, card.getCity1().getName());
            stmnt.setString(3, card.getCity2().getName());
            stmnt.setInt(4, card.getPoints());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
    }

    /**
     * @pre databse exists
     * @post nothing has changed
     * @return ArrayList of DestinationCards
     * @throws DatabaseException
     */
    public ArrayList<DestinationCard> getDefaultDeck() throws DatabaseException {
        ArrayList<DestinationCard> deck = new ArrayList<>();
        //_db.open();

        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ALL_DEFAULT);
            ResultSet rs = stmnt.executeQuery();

            while (rs.next()) {
                //TODO: refactor getting cities in this function, if we ever actually USE this function besides for testing...
                City city1 = new City(rs.getString("CITY_1"), new Point(0.0, 0.0));
                City city2 = new City(rs.getString("CITY_2"), new Point(0.0, 0.0));
                deck.add(new DestinationCard(rs.getString("CARD_ID"), city1, city2, rs.getInt("POINTS")));
            }

            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        //_db.close(true);
        return deck;
    }
}
