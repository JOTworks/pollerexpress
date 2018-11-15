package pollerexpress.database;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pollerexpress.database.dao.GameDao;

import static org.junit.Assert.*;

public class TestGameDao {
    Database db;
    GameDao gDao;
    GameInfo gi;
    Game g;
    User u;

    @Before
    public void up() {
        db = new Database();
        gDao = db.gDao;
        gi = new GameInfo("Game",3);
        g = new Game(gi);
        u = new User("username","password");
        //p = new Player("username", gi.getId());
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
//            dcDao.deleteDeck(gi);
//            db.close(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testCreateGame() {
        try {
            db.open();

            gDao.write(g);
            Game game = gDao.read(g.getId());

            db.close(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
