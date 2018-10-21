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

public class DestinationCardDao {
    IDatabase _db;
    public static final String SELECT_TOP_CARD_POS = "SELECT DESTINATION_DECK_POS\n FROM GAMES\n WHERE GAME_ID = ?";
    public static final String UPDATE_TOP_CARD_POS = "UPDATE GAMES\n SET DESTINATION_DECK_POS = ?\n WHERE GAME_ID = ?";
    public static final String SELECT_CARD = "SELECT CARD_ID, CITY_1, CITY_2, POINTS\n FROM DEFAULT_DESTINATION_DECK WHERE CARD_ID = ?";

    public DestinationCardDao(IDatabase db) {
        this._db = db;
    }

    public void createDeckTable(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "DESTINATION_DECK_" + gi.getId();
        String CREATE_DECK = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + "\n (`CARD_ID` TEXT NOT NULL, `POSITION` INT, `DISCARDED` TINYINT, PRIMARY_KEY(`CARD_ID`) )";
        //TODO: implement (maybe, this actually might be in a different package and class)
    }

    public void deleteDeck(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "DESTINATION_DECK_" + gi.getId();
        String DELETE_DECK = "DROP TABLE IF EXISTS " + TABLE_NAME;
        //implement later
    }

    public void insertCardAtPosition(String cardId, int position){
        //implement later
    }

    public DestinationCard drawCard(Game game) throws DatabaseException {
        try {
            //get position of the top card in the game's deck.
            int topPos = 0;
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_TOP_CARD_POS);
            stmnt.setString( 1, game.getId() );
            ResultSet rs = stmnt.executeQuery();
            if(rs.next())
            {
                topPos = rs.getInt("DESTINATION_DECK_POS");
            }
            rs.close();
            stmnt.close();

            //get card_id of the card at that position in the game's deck
            String cardId = "";
            String SELECT_CARD_ID = "SELECT CARD_ID\n FROM DESTINATION_DECK_" + game.getId() + "\n WHERE POSITION = ?";
            stmnt = _db.getConnection().prepareStatement(SELECT_CARD_ID);
            stmnt.setInt( 1, topPos );
            rs = stmnt.executeQuery();
            if(rs.next())
            {
                cardId = rs.getString("CARD_ID");
            }
            rs.close();
            stmnt.close();

            //increments the position of the top card in the game. (check for end of deck?)
            //TODO: check for end of deck in order to shuffle when the end is reached.
            int newPos = topPos + 1;
            stmnt = _db.getConnection().prepareStatement(UPDATE_TOP_CARD_POS);
            stmnt.setInt( 1, newPos );
            stmnt.setString( 2, game.getId() );
            stmnt.execute();
            stmnt.close();

            //retrieves DestinationCard by card_id from default_destination_deck and returns it.
            DestinationCard card = null;
            stmnt = _db.getConnection().prepareStatement(SELECT_CARD);
            stmnt.setString( 1, cardId );
            rs = stmnt.executeQuery();
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
                throw new DataNotFoundException(cardId,"DEFAULT_DESTINATION_DECK");
            } else {
                return card;
            }
        } catch(Exception e) {
            throw new DatabaseException();
        }
    }

}
