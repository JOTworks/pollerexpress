package pollerexpress.database.utilities;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.GameInfo;
import com.shared.models.Map;
import com.shared.models.Point;
import com.shared.models.Route;

import java.util.ArrayList;

import pollerexpress.database.Database;
import pollerexpress.database.dao.CityDao;
import pollerexpress.database.dao.RouteDao;

public class MapBuilder {
    Database _db;
    Map map;
    RouteDao rDao;

    public MapBuilder(Database db, Map map) {
        this._db = db;
        this.map = map;
        rDao = _db.getRouteDao();
    }

    public void makeGameRoutes(GameInfo gi) throws DatabaseException {
        for(Route r : map.getRoutes()) {
            rDao.insertRoute(r, gi);
        }
    }

    public void getRoute(String routeId) {

    }
}
