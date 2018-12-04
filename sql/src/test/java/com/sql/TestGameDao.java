package com.sql;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGameDao {
    SQLDatabase db;
    SQLGameDao gDao;
    Game g;

    @Before
    public void up() {
        g = new Game(new GameInfo("A Game", 5));
        try{
            db = new SQLDatabase();
            gDao = db.gDao;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void down() {
        try {
            db.startTransaction();
            gDao.deleteTable();
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void test() {
        try{
            //
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
