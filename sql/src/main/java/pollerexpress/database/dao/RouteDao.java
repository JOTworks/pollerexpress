package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.Color;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.Point;
import com.shared.models.Route;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument;

public class RouteDao {
    IDatabase _db;
    public static final String INSERT_ROUTE = "INSERT INTO ROUTES (ROUTE_ID, GAME_ID, OWNER) VALUES (?,?,?)";
    public static final String UPDATE_ROUTE = "UPDATE ROUTES SET OWNER = ? WHERE ROUTE_ID = ? AND GAME_ID = ?";
    public static final String SELECT_ROUTE = "SELECT * FROM ROUTES WHERE ROUTE_ID = ? AND GAME_ID = ?";
    public static final String SELECT_PLAYER_ROUTES = "SELECT * FROM ROUTES WHERE GAME_ID = ? AND OWNER = ?";
    public static final String DELETE_GAME_ROUTES = "DELETE FROM ROUTES WHERE GAME_ID = ?";

    public RouteDao(IDatabase db) {
        _db = db;
    }

    public void insertRoute(Route r, GameInfo gi) throws DatabaseException {
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_ROUTE);
            stmnt.setString( 1, r.getId() );
            stmnt.setString( 2, gi.getId() );
            stmnt.setString( 3, null );
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void claimRoute(Route r, Player p) throws DatabaseException {
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE_ROUTE);
            stmnt.setString( 1, p.getName() );
            stmnt.setString( 2, r.getId() );
            stmnt.setString( 3, p.getGameId() );
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public String getOwner(Route r, GameInfo gi) throws DatabaseException {
        String owner = null;
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ROUTE);
            stmnt.setString( 1, r.getId() );
            stmnt.setString( 2, gi.getId() );
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                owner = rs.getString("OWNER");
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return owner;
    }

    public ArrayList<String> getPlayerRoutes(Player p) throws DatabaseException {
        ArrayList<String> route_ids = new ArrayList<>();
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ROUTE);
            stmnt.setString( 1, p.getGameId() );
            stmnt.setString( 2, p.getName() );
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                route_ids.add(rs.getString("ROUTE_ID"));
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return route_ids;
    }

    public void deleteGameRoutes(GameInfo gi) throws DatabaseException {
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DELETE_GAME_ROUTES);
            stmnt.setString( 1, gi.getId() );
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }


//
//    public static final String CREATE_GAME_ROUTE_TABLE = "CREATE TABLE IF NOT EXISTS <TABLE_NAME> (`ROUTE_ID` TEXT NOT NULL, `OWNER` TEXT NOT NULL, PRIMARY KEY(`ROUTE_ID`) )";
//    public static final String DROP_GAME_ROUTE_TABLE = "DROP TABLE IF EXISTS <TABLE_NAME>";
//    public static final String INSERT_DEFAULT_ROUTE = "INSERT INTO ROUTES (ROUTE_ID, CITY_1, CITY_2, COLOR, LENGTH, ROTATION)\n VALUES(?,?,?,?,?,?)";
//    public static final String INSERT_GAME_ROUTE = "INSERT INTO <TABLE_NAME> (ROUTE_ID, OWNER)\n VALUES(?,?)";
//    public static final String SELECT_DEFAULT_ROUTE = "SELECT * FROM ROUTES WHERE ROUTE_ID = ?";
//    public static final String SELECT_ALL_DEFAULT_ROUTES = "SELECT * FROM ROUTES";
//    public static final String SELECT_GAME_ROUTE = "SELECT DEF.ROUTE_ID, DEF.CITY_1, DEF.CITY_2, DEF.COLOR, DEF.LENGTH, DEF.ROTATION, GR.OWNER\n" +
//                                                    "FROM ROUTES AS DEF LEFT JOIN <TABLE_NAME> AS GR ON DEF.ROUTE_ID = GR.ROUTE_ID" +
//                                                    "WHERE GR.ROUTE_ID = ?";
//    public static final String SELECT_PLAYER_ROUTES = "SELECT DEF.ROUTE_ID, DEF.CITY_1, DEF.CITY_2, DEF.COLOR, DEF.LENGTH, DEF.ROTATION, GR.OWNER\n" +
//                                                    "FROM ROUTES AS DEF LEFT JOIN <TABLE_NAME> AS GR ON DEF.ROUTE_ID = GR.ROUTE_ID" +
//                                                    "WHERE GR.OWNER = ?";
//    public static final String UPDATE_GAME_ROUTE = "UPDATE <TABLE_NAME>\n SET OWNER = ?\n WHERE ROUTE_ID = ?";
//
//    //    (`ROUTE_ID`, `CITY_1`, `CITY_2`, `COLOR`, `LENGTH`, `ROTATION`";
//
//
//    public RouteDao(IDatabase db) {
//        _db = db;
//    }
//
//    public void createGameRouteTable(GameInfo gi) throws DatabaseException {
//        String TABLE_NAME = "\"ROUTES_" + gi.getId() + "\"";
//        String CREATE = CREATE_GAME_ROUTE_TABLE.replace("<TABLE_NAME>",TABLE_NAME);
//
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(CREATE);
//            stmnt.execute();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//    }
//
//    public void deleteGameRouteTable(GameInfo gi) throws DatabaseException {
//        String TABLE_NAME = "\"ROUTES_" + gi.getId() + "\"";
//        String DROP = DROP_GAME_ROUTE_TABLE.replace("<TABLE_NAME>",TABLE_NAME);
//
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(DROP);
//            stmnt.execute();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//    }
//
//    public void insertGameRoute(GameInfo gi, Route r) throws DatabaseException {
//        String TABLE_NAME = "\"ROUTES_" + gi.getId() + "\"";
//        String INSERT = INSERT_GAME_ROUTE.replace("<TABLE_NAME>",TABLE_NAME);
//
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT);
//            stmnt.setString( 1, r.getId() );
//            stmnt.setString( 2, null );
//            stmnt.execute();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//    }
//
//    public void claimRoute(Player p, Route r) throws DatabaseException {
//        String TABLE_NAME = "\"ROUTES_" + p.getGameId() + "\"";
//        String UPDATE = UPDATE_GAME_ROUTE.replace("<TABLE_NAME>",TABLE_NAME);
//
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE);
//            stmnt.setString( 1, p.getName() );
//            stmnt.setString( 2, r.getId() );
//            stmnt.execute();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//    }
//
//    public ArrayList<Route> getPlayerRoutes(Player p) throws DatabaseException {
//        String TABLE_NAME = "\"ROUTES_" + p.getGameId() + "\"";
//        String SELECT = SELECT_PLAYER_ROUTES.replace("<TABLE_NAME>",TABLE_NAME);
//        ArrayList<Route> routes = new ArrayList<>();
//
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT);
//            stmnt.setString( 1, p.getName() );
//            ResultSet rs = stmnt.executeQuery();
//            while(rs.next()) {
//                City city1 = new City(rs.getString("CITY_1"), new Point(0.0,0.0)); //TODO: ACTUALLY GET THE CITIES SOMEHOW
//                City city2 = new City(rs.getString("CITY_2"), new Point(0.0,0.0));
//
//                routes.add(new Route(rs.getString("ROUTE_ID"), city1, city2, rs.getInt("LENGTH"),p, Color.TRAIN.valueOf(rs.getString("COLOR")), rs.getInt("ROTATION")));
//            }
//            rs.close();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//        return routes;
//    }
//
//    public Route getGameRoute(GameInfo gi, String routeId) throws DatabaseException {
//        String TABLE_NAME = "\"ROUTES_" + gi.getId() + "\"";
//        String SELECT = SELECT_GAME_ROUTE.replace("<TABLE_NAME>",TABLE_NAME);
//        Route r = null;
//
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT);
//            stmnt.setString( 1, routeId );
//            ResultSet rs = stmnt.executeQuery();
//            if(rs.next()) {
//                Player p = new Player(rs.getString("OWNER")); //TODO: ACTUALLY GET THE PLAYER SOMEHOW
//                City city1 = new City(rs.getString("CITY_1"), new Point(0.0,0.0)); //TODO: ACTUALLY GET THE CITIES SOMEHOW
//                City city2 = new City(rs.getString("CITY_2"), new Point(0.0,0.0));
//
//                r = new Route(rs.getString("ROUTE_ID"), city1, city2, rs.getInt("LENGTH"),p, Color.TRAIN.valueOf(rs.getString("COLOR")), rs.getInt("ROTATION"));
//            }
//            rs.close();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//        return r;
//    }
//
//    /*-------------------------------DEFAULT ROUTE FUNCTIONS-----------------------------*/
//
//    public void insertRoute(Route r) throws DatabaseException {
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_DEFAULT_ROUTE);
//            //ROUTE_ID, CITY_1, CITY_2, COLOR, LENGTH, ROTATION
//            stmnt.setString( 1, r.getId() );
//            stmnt.setString( 2, r.getCities().get(0).getName() );
//            stmnt.setString( 3, r.getCities().get(1).getName() );
//            stmnt.setString( 4, r.getColor().name() );
//            stmnt.setInt( 5, r.getDistance() );
//            stmnt.setInt( 6, r.getRotation() );
//            stmnt.execute();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//    }
//
//    public Route getRoute(String routeId) throws DatabaseException {
//        Route r = null;
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_DEFAULT_ROUTE);
//            stmnt.setString( 1, routeId );
//            ResultSet rs = stmnt.executeQuery();
//            if(rs.next()) {
//                City city1 = new City(rs.getString("CITY_1"), new Point(0.0,0.0)); //TODO: ACTUALLY GET THE CITIES SOMEHOW
//                City city2 = new City(rs.getString("CITY_2"), new Point(0.0,0.0));
//
//                r = new Route(rs.getString("ROUTE_ID"), city1, city2, rs.getInt("LENGTH"),null, Color.TRAIN.valueOf(rs.getString("COLOR")), rs.getInt("ROTATION"));
//            }
//            rs.close();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//        return r;
//    }
//
//    public ArrayList<Route> getAllRoutes() throws DatabaseException {
//        ArrayList<Route> routes = new ArrayList<>();
//        try{
//            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ALL_DEFAULT_ROUTES);
//            ResultSet rs = stmnt.executeQuery();
//            while(rs.next()) {
//                City city1 = new City(rs.getString("CITY_1"), new Point(0.0,0.0)); //TODO: ACTUALLY GET THE CITIES SOMEHOW
//                City city2 = new City(rs.getString("CITY_2"), new Point(0.0,0.0));
//
//                routes.add(new Route(rs.getString("ROUTE_ID"), city1, city2, rs.getInt("LENGTH"),null, Color.TRAIN.valueOf(rs.getString("COLOR")), rs.getInt("ROTATION")));
//            }
//            rs.close();
//            stmnt.close();
//        } catch(SQLException e) {
//            throw new DatabaseException(e.getMessage());
//        }
//        return routes;
//    }
}
