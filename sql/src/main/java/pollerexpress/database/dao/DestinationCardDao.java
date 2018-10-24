package pollerexpress.database.dao;

import com.shared.exceptions.database.DataNotFoundException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Point;
import com.shared.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Destination;
import javax.xml.crypto.Data;

public class DestinationCardDao {
    private IDatabase _db;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS <TABLE_NAME>\n (`CARD_ID` TEXT NOT NULL, `POSITION` INT, `PLAYER` TEXT, PRIMARY_KEY(`CARD_ID`) )";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS <TABLE_NAME>";
    public static final String INSERT_CARD = "INSERT INTO <TABLE_NAME>(CARD_ID, POSITION, PLAYER)\n VALUES(?,?,?)";
    public static final String INSERT_DEFAULT_CARD = "INSERT INTO DEFAULT_DESTINATION_DECK(CARD_ID, CITY_1, CITY_2, POINTS)\n VALUES(?,?,?,?)";
    //public static final String SELECT_TOP_CARD = "SELECT CARD_ID\n FROM <TABLE_NAME>\n WHERE POSITION != 0\n ORDER BY POSITION ASC\n LIMIT 1";
    public static final String SELECT_TOP_CARD = "SELECT DEFAULT.CARD_ID, DEFAULT.CITY_1, DEFAULT.CITY_2, DEFAULT.POINTS\n FROM DEFAULT_DESTINATION_DECK DEFAULT\n LEFT JOIN <TABLE_NAME> DECK\n ON DECK.CARD_ID = DEFAULT.CARD_ID\n WHERE POSITION != 0\n ORDER BY POSITION ASC\n LIMIT 1";
    public static final String UPDATE_CARD = "UPDATE <TABLE_NAME>\n SET POSITION = ?, PLAYER = ?\n WHERE CARD_ID = ?";
    public static final String SELECT_CARD = "SELECT CARD_ID, CITY_1, CITY_2, POINTS\n FROM DEFAULT_DESTINATION_DECK WHERE CARD_ID = ?";
    public static final String SELECT_ALL_DEFAULT = "SELECT * FROM DEFAULT_DESTINATION_DECK";

    public DestinationCardDao(IDatabase db) {
        this._db = db;
    }

    public void createDeckTable(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "DESTINATION_DECK_" + gi.getId();
        String CREATE_DECK = CREATE_TABLE.replace("<TABLE_NAME>",TABLE_NAME);
        //TODO: implement (maybe, this actually might be in a different package and class)
    }

    public void deleteDeck(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "DESTINATION_DECK_" + gi.getId();
        String DELETE_DECK = DROP_TABLE.replace("<TABLE_NAME>",TABLE_NAME);
        //implement later
    }



    public void insertCard(String card, int position, String player) {
        //implement later
    }

    public void updateCard(String card, int position, String player) {
        //implement later
    }

    public DestinationCard getCard(String id) {
        //implement later
        return null;
    }

    public DestinationCard drawCard(GameInfo gi, Player player) throws DatabaseException {
        try {
            //TODO: check for shuffle time, wherever that happens.
            //get card
            DestinationCard card = null;
            String TABLE_NAME = "DESTINATION_DECK_" + gi.getId();
            String GET_TOP_CARD = SELECT_TOP_CARD.replace("<TABLE_NAME>",TABLE_NAME);
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_TOP_CARD);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next())
            {
                //TODO: actually fetch the cities rather than making dummy ones.
                City city1 = new City("North Pole", new Point(0.1,0.1));
                City city2 = city1;
                card = new DestinationCard(rs.getString("CARD_ID"), city1, city2, rs.getInt("POINTS"));
            }
            rs.close();
            stmnt.close();
            if(card == null) {
                throw new DataNotFoundException("TOP CARD","DESTINATION_DECK");
            }

            //update deck to show card has been drawn, and by who.
            String UPDATE_DECK = UPDATE_CARD.replace("<TABLE_NAME>",TABLE_NAME);
            stmnt = _db.getConnection().prepareStatement(UPDATE_DECK);
            stmnt.setInt( 1, 0 );
            stmnt.setString( 2, player.getName());
            stmnt.setString( 3, card.getId());
            stmnt.execute();
            stmnt.close();

            return card;
        } catch(Exception e) {
            throw new DatabaseException();
        }
    }

    public void discardCard(Game game, DestinationCard card) {
        //implement later
    }

    /*
     * --------------------------------------------------------
     * DEFAULT DECK FUNCTIONS
     * --------------------------------------------------------
     */

    public void insertIntoDefault(DestinationCard card) throws DatabaseException {
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_DEFAULT_CARD);
            stmnt.setString(1, card.getId());
            stmnt.setString(2, card.getCity1().getName());
            stmnt.setString(3, card.getCity2().getName());
            stmnt.setInt(4, card.getPoints());
            stmnt.execute();
            stmnt.close();
        } catch(Exception e) {
            throw new DatabaseException();
        }
    }

    public ArrayList<DestinationCard> getDefaultDeck() throws DatabaseException {
        try {
            System.out.println("hi");
            ArrayList<DestinationCard> deck = new ArrayList<>();
            System.out.println("haven't died yet 1");
            Connection conn = _db.getConnection();
            if(conn == null) {
                System.out.println("awwwwwkwwwward");
            }
            System.out.println("haven't died yet 2");
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ALL_DEFAULT);
            System.out.println("haven't died yet 3");
            ResultSet rs = stmnt.executeQuery();
            System.out.println("haven't died yet 4");
            int i = 0;
            while(rs.next()) {
                System.out.println("HEY! "+i);
                i++;
                //TODO: refactor getting cities in this function, if we ever actually USE this function besides for testing...
                City city1 = new City(rs.getString("CITY_1"), new Point(0.0,0.0));
                City city2 = new City(rs.getString("CITY_2"), new Point(0.0,0.0));
                deck.add(new DestinationCard(rs.getString("CARD_ID"), city1, city2, rs.getInt("POINTS")));
            }
            System.out.println("got out of here");
            rs.close();
            stmnt.close();
            return deck;
        } catch(Exception e) {
            System.out.println(e.toString());
            throw new DatabaseException();
        }
    }
}
