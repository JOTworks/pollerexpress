package pollerexpress.database.dao;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.City;
import com.shared.models.Point;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.crypto.Data;

public class CityDao {
    private IDatabase _db;
    public static final String INSERT_CITY = "INSERT INTO CITIES (NAME, X_COOR, Y_COOR)\n VALUES(?,?,?)";
    public static final String SELECT_CITY = "SELECT * FROM CITIES WHERE NAME = ?";
    public static final String SELECT_ALL_CITIES = "SELECT * FROM CITIES";

    public CityDao(IDatabase db) {
        this._db = db;
    }

    public void insertCity(City city) throws DatabaseException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_CITY);
            stmnt.setString(1, city.getName());
            stmnt.setDouble(2, city.getPoint().getX());
            stmnt.setDouble(3, city.getPoint().getY());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public City getCity(String name) throws DatabaseException {
        City city = null;
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_CITY);
            stmnt.setString(1, name);
            ResultSet rs = stmnt.executeQuery();

            if(rs.next()) {
                city = new City(name, new Point(rs.getDouble("X_COOR"), rs.getDouble("Y_COOR")));
            }

            rs.close();
            stmnt.close();
            return city;
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public ArrayList<City> getAllCities() throws DatabaseException {
        ArrayList<City> cities = new ArrayList<>();
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ALL_CITIES);
            ResultSet rs = stmnt.executeQuery();

            while(rs.next()) {
                cities.add(new City(rs.getString("NAME"), new Point(rs.getDouble("X_COOR"), rs.getDouble("Y_COOR"))));
            }

            rs.close();
            stmnt.close();
            return cities;
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
