package pollerexpress.database.utilities;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.GameInfo;
import com.shared.models.Point;

import java.util.ArrayList;

import pollerexpress.database.Database;
import pollerexpress.database.dao.CityDao;

public class MapBuilder {
    Database _db;

    public MapBuilder(Database db) {
        this._db = db;
    }

    public void makeMap() throws DatabaseException {
        makeCities();
        makeRoutes();
    }

    public void makeCities() throws DatabaseException {
        CityDao cDao = _db.getCityDao();
        ArrayList<City> cities = new ArrayList<>();

        cities.add(new City("Alert", new Point(0.0,0.0)));
        cities.add(new City("Alpine", new Point(0.0,0.0)));
        cities.add(new City("An Ice Place", new Point(0.0,0.0)));
        cities.add(new City("Anadyrskol", new Point(0.0,0.0)));
        cities.add(new City("Anchorage", new Point(0.0,0.0)));
        cities.add(new City("Atlantis", new Point(0.0,0.0)));
        cities.add(new City("Berexow", new Point(0.0,0.0)));
        cities.add(new City("Bergen", new Point(0.0,0.0)));
        cities.add(new City("Blitzenberg", new Point(0.0,0.0)));
        cities.add(new City("Boreas", new Point(0.0,0.0)));
        cities.add(new City("Bronlundhus", new Point(0.0,0.0)));
        cities.add(new City("Cometville", new Point(0.0,0.0)));
        cities.add(new City("Cupidforth", new Point(0.0,0.0)));
        cities.add(new City("Dancerkirk", new Point(0.0,0.0)));
        cities.add(new City("Dasherbury", new Point(0.0,0.0)));
        cities.add(new City("Deadhorse", new Point(0.0,0.0)));
        cities.add(new City("Donnerbrough", new Point(0.0,0.0)));
        cities.add(new City("Elfland", new Point(0.0,0.0)));
        cities.add(new City("Elsa's Castle", new Point(0.0,0.0)));
        cities.add(new City("Hammerfest", new Point(0.0,0.0)));
        cities.add(new City("Home of Polaris", new Point(0.0,0.0)));
        cities.add(new City("Isachsen", new Point(0.0,0.0)));
        cities.add(new City("Kuuk", new Point(0.0,0.0)));
        cities.add(new City("Murmansk", new Point(0.0,0.0)));
        cities.add(new City("New Tokyo", new Point(0.0,0.0)));
        cities.add(new City("Nord", new Point(0.0,0.0)));
        cities.add(new City("Nuorgam", new Point(0.0,0.0)));
        cities.add(new City("Prancerstadt", new Point(0.0,0.0)));
        cities.add(new City("Rudolphford", new Point(0.0,0.0)));
        cities.add(new City("Santa's Workshop", new Point(0.0,0.0)));
        cities.add(new City("Surgut", new Point(0.0,0.0)));
        cities.add(new City("Taimura", new Point(0.0,0.0)));
        cities.add(new City("Ulukhaktok", new Point(0.0,0.0)));
        cities.add(new City("Utqiagvik", new Point(0.0,0.0)));
        cities.add(new City("Weather Station", new Point(0.0,0.0)));
        cities.add(new City("Yetifurt", new Point(0.0,0.0)));

        for(City city : cities) {
            cDao.insertCity(city);
        }
    }

    public void makeRoutes() throws DatabaseException {
        //
    }

    public void makeGameRoutes(GameInfo gi) throws DatabaseException {
        //
    }
}
