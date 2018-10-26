package pollerexpress.database.dao;

import com.shared.models.TrainCard;

import java.util.ArrayList;

public class TrainCardDao {
    private IDatabase _db;
    public static final String CREATE_TABLE = "";
    public static final String DROP_TABLE = "";
    public static final String INSERT_CARD = "";
    public static final String INSERT_DEFAULT_CARD = "";

    public TrainCardDao(IDatabase db) {
        this._db = db;
    }







    /*
    * -----------------------------------------------------
    * DEFAULT DECK FUNCTIONS
    * -----------------------------------------------------
    */

    public void insertIntoDefault() {
        //
    }

    public ArrayList<TrainCard> getDefaultDeck() {
        //
        return null;
    }
}
