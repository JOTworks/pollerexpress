package com.sql;

import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestGameDao {
    SQLDatabase db;
    SQLGameDao gDao;
    Game g;

    @Before
    public void up() {
        g = new Game(new GameInfo("A Game", 5));
        Player p = new Player("name");
        g.addPlayer(p);
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
    public void testAddGame() {
        try{
            db.startTransaction();
            gDao.addGame(g);
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetGame() {
        try{
            db.startTransaction();

            gDao.addGame(g);
            Game game = gDao.getGame(g.getId());

            assertEquals(g.getPlayers(),game.getPlayers());

            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testUpdateGame() {
        try{
            db.startTransaction();

            gDao.addGame(g);
            Game game = gDao.getGame(g.getId());

            g.addPlayer(new Player("player2"));
            gDao.updateGame(g);
            Game game2 = gDao.getGame(g.getId());

            assertNotEquals(game.getPlayers(),game2.getPlayers());
            assertEquals(g.getPlayers(),game2.getPlayers());

            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testDeleteGame() {
        try{
            db.startTransaction();

            gDao.addGame(g);
            assertNotNull(gDao.getGame(g.getId()));

            gDao.deleteGame(g);
            assertNull(gDao.getGame(g.getId()));

            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetAllGames() {
        try{
            Game g2 = new Game(new GameInfo("Another Game", 5));
            Game g3 = new Game(new GameInfo("Also Game", 5));
            db.startTransaction();

            gDao.addGame(g);
            gDao.addGame(g2);
            gDao.addGame(g3);

            ArrayList<Game> games = gDao.getAllGames();
            assertEquals(3, games.size());
            assertTrue(games.contains(g));
            assertTrue(games.contains(g2));
            assertTrue(games.contains(g3));

            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
