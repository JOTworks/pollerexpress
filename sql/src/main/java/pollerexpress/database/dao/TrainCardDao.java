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
    public static final String UPDATE_CARD = "UPDATE <TABLE_NAME>\n SET POSITION = ?, PLAYER = ?\n WHERE CARD_ID = ?";
    public static final String SELECT_TOP_CARD = "SELECT DEF.CARD_ID, DEF.COLOR\n FROM DEFAULT_TRAIN_DECK AS DEF\n LEFT JOIN <TABLE_NAME> AS DECK\n ON DECK.CARD_ID = DEF.CARD_ID\n WHERE POSITION != 0\n ORDER BY POSITION ASC\n LIMIT 1";
    public static final String SELECT_HAND = "SELECT DEF.CARD_ID, DEF.COLOR\n FROM <TABLE_NAME> AS DECK\n LEFT JOIN DEFAULT_TRAIN_DECK AS DEF\n ON DECK.CARD_ID = DEF.CARD_ID\n WHERE PLAYER = ?";
    public static final String SELECT_ALL_DEFAULT = "SELECT *\n FROM DEFAULT_TRAIN_DECK";
    public static final String SELECT_DISCARD = "SELECT CARD_ID\n FROM <TABLE_NAME>\n WHERE POSITION = 0 AND PLAYER IS NULL";
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
        String TABLE_NAME = "\"TRAIN_DECK_" + gi.getId() + "\"";
        String INSERT = INSERT_CARD.replace("<TABLE_NAME>",TABLE_NAME);

        _db.open();

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

        _db.close(true);
    }

    public void updateCard(GameInfo gi, String card, int position, String player) {
        //implement later
    }

    public TrainCard getCard(String id) {
        //
        return null;
    }

    public TrainCard drawCard(Player player) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + player.getGameId() + "\"";
        String GET_TOP_CARD = SELECT_TOP_CARD.replace("<TABLE_NAME>",TABLE_NAME);
        TrainCard card = null;

        _db.open();

        try{
            //get card
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_TOP_CARD);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                card = new TrainCard(rs.getString("CARD_ID"), Color.TRAIN.valueOf(rs.getString("COLOR")));
            }
            rs.close();
            stmnt.close();

            if(card != null) {
                //set card to player's hand
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

        _db.close(true);
        return card;
    }

    public ArrayList<TrainCard> getHand(Player player) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + player.getGameId() + "\"";
        String GET_HAND = SELECT_HAND.replace("<TABLE_NAME>",TABLE_NAME);
        ArrayList<TrainCard> hand = new ArrayList<>();

        _db.open();

        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(GET_HAND);
            stmnt.setString(1, player.getName());
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                hand.add(new TrainCard(rs.getString("CARD_ID"), Color.TRAIN.valueOf(rs.getString("COLOR"))));
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        _db.close(true);
        return hand;
    }

    public void discardCard(Player player, TrainCard card) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + player.getGameId() + "\"";
        String UPDATE = UPDATE_CARD.replace("<TABLE_NAME>",TABLE_NAME);

        _db.open();

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

        _db.close(true);
    }

    public int getDeckSize(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + gi.getId() + "\"";
        String GET_COUNT = COUNT_GAME_DECK.replace("<TABLE_NAME>",TABLE_NAME);
        int count = 0;

        _db.open();

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

        _db.close(true);
        return count;
    }

    public ArrayList<String> getDiscardPile(GameInfo gi) throws DatabaseException {
        String TABLE_NAME = "\"TRAIN_DECK_" + gi.getId() + "\"";
        String GET_DISCARD = SELECT_DISCARD.replace("<TABLE_NAME>",TABLE_NAME);
        ArrayList<String> discardPile = new ArrayList<>();

        _db.open();

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

        _db.close(true);
        return discardPile;
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
