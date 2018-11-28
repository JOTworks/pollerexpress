package pollerexpress.database;

import com.shared.models.City;
import com.shared.models.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

import pollerexpress.database.dao.CityDao;
import pollerexpress.database.utilities.MapBuilder;

public class TestCityDao {
    Database db;
    MapBuilder builder;
    CityDao cDao;
    City c;

    @Before
    public void up() {
        db = new Database();
        builder = new MapBuilder(db);
        cDao = db.getCityDao();
        c = new City("North Pole", new Point(0.0,0.1));
        try {
            db.open();

            db.deleteTables();
            db.createTables();

            db.close(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void down() {
        try {
//            db.open();
//
//            db.close(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test() {
        try {
            db.open();

            //add city, read city
            cDao.insertCity(c);
            City city = cDao.getCity(c.getName());

            assertEquals(c, city);
            assertEquals(c.getPoint(), city.getPoint());

            //add another city
            City c2 = new City("South Pole", new Point(255.0, 255.0));
            cDao.insertCity(c2);

            //get all cities, make sure there are two and that both inserted cities are in the arraylist
            ArrayList<City> cities = cDao.getAllCities();
            assertEquals(2, cities.size());
            assertTrue(cities.contains(c));
            assertTrue(cities.contains(c2));

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            if(db.isOpen()){db.close(false);}
        }
    }

    @Test
    public void testMapBuilderMakeCities() {
        try {
            db.open();
            builder.makeCities();
            assertEquals(36, cDao.getAllCities().size());

        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            if(db.isOpen()){db.close(false);}
        }
    }
}
