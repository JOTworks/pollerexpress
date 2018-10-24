package pollerexpress.database.utilities;

import com.shared.models.City;
import com.shared.models.DestinationCard;
import com.shared.models.GameInfo;
import com.shared.models.Point;
import com.shared.models.interfaces.IDatabaseFacade;

import java.util.UUID;

import pollerexpress.database.dao.DestinationCardDao;
import pollerexpress.database.dao.IDatabase;

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
    public void makeDefaultDecks() {
        DestinationCardDao dCardDao = _db.getDestinationCardDao();
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
            try {
                dCardDao.insertIntoDefault(card);
            } catch(Exception e) {
                System.out.println("Destination deck got built wrong!!!");
            }
        }

        //do same thing for train cards...
    }

    public void makeDestinationDeck(GameInfo gi) {
        //implement later
    }

    public void makeTrainDeck(GameInfo gi) {
        //implement later
    }




    public void shuffleDestinationDeck(GameInfo gi) {
        //implement later; calls this.shuffle()
    }

    public void shuffleTrainDeck(GameInfo gi) {
        //implement later; calls this.shuffle()
    }

    private void shuffle(String tablename){
        //implement later
    }
}
