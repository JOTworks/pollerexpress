package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Color;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.TrainCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TrainCardDao {
    private IDatabase _db;
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS <TABLE_NAME>\n(`CARD_ID` TEXT NOT NULL PRIMARY KEY, `POSITION` INT, `PLAYER` TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS <TABLE_NAME>";
    public static final String INSERT_CARD = "INSERT INTO <TABLE_NAME>(CARD_ID, POSITION, PLAYER)\n VALUES(?,?,?)";
    public static final String INSERT_DEFAULT_CARD = "INSERT INTO DEFAULT_TRAIN_DECK(CARD_ID, COLOR)\n VALUES(?,?)";
    public static final String SELECT_ALL_DEFAULT = "SELECT *\n FROM DEFAULT_TRAIN_DECK";
    public static final String COUNT_GAME_DECK = "SELECT COUNT(*)\n FROM <TABLE_NAME> WHERE POSITION != 0";

    public TrainCardDao(IDatabase db) {
        this._db = db;
    }

    public void createDeckTable(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + gi.getId() + "\"";
        String CREATE_DECK = CREATE_TABLE.replace("<TABLE_NAME>",TABLE_NAME);

        _db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(CREATE_DECK);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
    }

    public void deleteDeck(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + gi.getId() + "\"";
        String DELETE_DECK = DROP_TABLE.replace("<TABLE_NAME>",TABLE_NAME);

        _db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DELETE_DECK);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
    }

    public void insertCard(GameInfo gi, String cardId, int position, String player) throws DatabaseException {
        //
    }

    public void updateCard(GameInfo gi, String card, int position, String player) {
        //implement later
    }

    public TrainCard getCard(String id) {
        //
        return null;
    }

    public TrainCard drawCard(Player player) throws DatabaseException {
        //
        return null;
    }

    public ArrayList<TrainCard> getHand(Player player) throws DatabaseException {
        //
        return null;
    }

    public void discardCard(Player player, TrainCard card) throws DatabaseException {
        //
    }

    public int getDeckSize(GameInfo gi) throws DatabaseException {
        //
        return 0;
    }

    public ArrayList<String> getDiscardPile(GameInfo gi) throws DatabaseException {
        //
        return null;
    }

    /*
    * -----------------------------------------------------
    * DEFAULT DECK FUNCTIONS
    * -----------------------------------------------------
    */

    public void insertIntoDefault(TrainCard card) throws DatabaseException {
        _db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_DEFAULT_CARD);
            stmnt.setString(1, card.getId());
            stmnt.setString(2, card.getColor().name());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
    }

    public ArrayList<TrainCard> getDefaultDeck() throws DatabaseException {
        ArrayList<TrainCard> deck = new ArrayList<>();
        _db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ALL_DEFAULT);
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                deck.add(new TrainCard(rs.getString("CARD_ID"), Color.TRAIN.valueOf(rs.getString("COLOR"))));
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
        return deck;
    }
}
