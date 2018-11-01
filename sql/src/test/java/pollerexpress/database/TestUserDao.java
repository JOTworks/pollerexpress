package pollerexpress.database;

import com.shared.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pollerexpress.database.dao.UserDao;

import static org.junit.Assert.*;

public class TestUserDao {
    Database db;
    UserDao uDao;
    User u;

    @Before
    public void up() {
        db = new Database();
        uDao = db.uDao;
        u = new User("username","password");

        try{
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
        try{
            //db.close(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testPoints() {
        try {
            db.open();
            uDao.write(u);
            //points should be 0 at start
            assertEquals(0,uDao.getPlayerPoints(u));

            //set and check
            uDao.setPlayerPoints(u,5);
            assertEquals(5,uDao.getPlayerPoints(u));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            db.close(false);
        }
    }

    @Test
    public void testTrainCars() {
        try {
            db.open();
            uDao.write(u);
            //points should be 0 at start
            assertEquals(0,uDao.getPlayerTrainCars(u));

            //set and check
            uDao.setPlayerTrainCars(u,5);
            assertEquals(5,uDao.getPlayerTrainCars(u));
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
        finally
        {
            db.close(false);
        }
    }
}
