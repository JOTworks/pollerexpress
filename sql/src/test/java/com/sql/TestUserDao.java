package com.sql;

import com.shared.models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestUserDao {
    SQLDatabase db;
    SQLUserDao uDao;
    User u;

    @Before
    public void up() {
        u = new User("username","password","game");
        try{
            db = new SQLDatabase();
            uDao = db.uDao;
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
            uDao.deleteTable();
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testAddUser() {
        try{
            db.startTransaction();
            uDao.addUser(u);
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetUser() {
        try{
            db.startTransaction();
            uDao.addUser(u);
            User user = uDao.getUser(u.getName());
            assertEquals(u.getName(), user.getName());
            assertEquals(u.password, user.password);
            assertEquals(u.getGameId(), user.getGameId());
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetAllUsers() {
        try{
            User u2 = new User("name","code");
            db.startTransaction();
            uDao.addUser(u);
            uDao.addUser(u2);
            List<User> users = uDao.getAllUsers();
            assertEquals(2, users.size());
            assertTrue(users.contains(u));
            assertTrue(users.contains(u2));
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testUpdateUser() {
        try{
            db.startTransaction();
            uDao.addUser(u);
            u.password = "newpassword";
            u.setGameID("newgame");
            uDao.updateUser(u);
            User user = uDao.getUser(u.getName());
            assertEquals(u.getName(), user.getName());
            assertEquals(u.password, user.password);
            assertEquals(u.getGameId(), user.getGameId());
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
