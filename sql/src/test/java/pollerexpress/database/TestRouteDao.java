package pollerexpress.database;

import com.shared.models.GameInfo;
import com.shared.models.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestRouteDao {
    Database db;
    RouteDao rDao;
    MapBuilder builder;
    GameInfo gi;
    Player p;

    @Before
    public void up() {
        db = new Database();
        rDao = db.getRouteDao();
        builder = new MapBuilder(db);
        gi = new GameInfo("GAME", 3);
        p = new Player("username", gi.getId());
        try {
            db.open();

            db.deleteTables();
            db.createTables();
            builder.makeGameRoutes(gi);

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
    public void testBuildRoutes() {
        try {
            db.open();
            builder.makeGameRoutes(gi);
            ///uhhhh there's no actual test in here, is there....
            db.close(true);

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
    public void testClaimRoute() {
        try {
            db.open();

            //Route r = builder.getMap().getRoutes();

            db.close(true);

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
