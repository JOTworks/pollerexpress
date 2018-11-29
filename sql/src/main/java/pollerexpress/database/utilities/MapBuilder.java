package pollerexpress.database.utilities;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.GameInfo;
import com.shared.models.Map;
import com.shared.models.Player;
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

    public MapBuilder(Database db) {
        this._db = db;
        this.map = Map.getDefaultMap();
        rDao = _db.getRouteDao();
    }

    public void makeGameRoutes(GameInfo gi) throws DatabaseException {
        for(Route r : map.getRoutes()) {
            rDao.insertRoute(r, gi);
        }
    }

    public Route getRoute(String routeId) {
        return map.getRouteById(routeId);
    }

    public Route getGameRoute(String routeId, GameInfo gi) throws DatabaseException {
        Route r = map.getRouteById(routeId);
        String owner = rDao.getOwner(r, gi);
        r.setOwner(_db.getUserDao().read(owner));
        return r;
    }
}
