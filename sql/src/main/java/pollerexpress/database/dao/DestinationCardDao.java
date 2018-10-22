package pollerexpress.database.dao;

import com.shared.exceptions.database.DataNotFoundException;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.Destination;

public class DestinationCardDao {
    IDatabase _db;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS <TABLE_NAME>\n (`CARD_ID` TEXT NOT NULL, `POSITION` INT, `PLAYER` TEXT, PRIMARY_KEY(`CARD_ID`) )";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS <TABLE_NAME>";
    //public static final String SELECT_TOP_CARD = "SELECT CARD_ID\n FROM <TABLE_NAME>\n WHERE POSITION != 0\n ORDER BY POSITION ASC\n LIMIT 1";
    public static final String SELECT_TOP_CARD = "SELECT DEFAULT.CARD_ID, DEFAULT.CITY_1, DEFAULT.CITY_2, DEFAULT.POINTS\n FROM DEFAULT_DESTINATION_DECK DEFAULT\n LEFT JOIN <TABLE_NAME> DECK\n ON DECK.CARD_ID = DEFAULT.CARD_ID\n WHERE POSITION != 0\n ORDER BY POSITION ASC\n LIMIT 1";
    public static final String UPDATE_CARD = "UPDATE <TABLE_NAME>\n SET POSITION = ?, PLAYER = ?\n WHERE CARD_ID = ?";
    public static final String SELECT_CARD = "SELECT CARD_ID, CITY_1, CITY_2, POINTS\n FROM DEFAULT_DESTINATION_DECK WHERE CARD_ID = ?";

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

    public void insertIntoDefault(DestinationCard card) {
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
                //TODO: actually build the cities rather than making empty ones.
                City city1 = new City();
                City city2 = new City();
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

}
