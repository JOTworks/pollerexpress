package pollerexpress.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import pollerexpress.database.dao.RouteDao;
import pollerexpress.database.utilities.MapBuilder;

public class TestRouteDao {
    Database db;
    RouteDao rDao;
    MapBuilder builder;

    @Before
    public void up() {
        db = new Database();
        rDao = db.getRouteDao();
        builder = new MapBuilder(db);
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
    public void testBuildDefaultRoutes() {
        try {
            db.open();
            builder.makeRoutes();

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
