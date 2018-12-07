package com.sql;

import com.shared.models.Command;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestCommandDao {
    SQLDatabase db;
    SQLCommandDao cDao;
    Command c;

    @Before
    public void up() {
        try{
            db = new SQLDatabase();
            cDao = db.cDao;
            Class<?>[] types = {String.class};
            Object[] params = {"param"};
            c = new Command("ClassName","MethodName",types,params);
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
            cDao.deleteTable();
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testAddCommand() {
        try{
            db.startTransaction();
            cDao.addCommand(c,"game");
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetCommands() {
        try{
            db.startTransaction();
            cDao.addCommand(c,"game");
            List<Command> commands = cDao.getCommands("game");
            assertEquals(1, commands.size());
            Command command = commands.get(0);
            assertEquals(c.getClassName(), command.getClassName());
            assertEquals(c.getMethodName(), command.getMethodName());
            assertEquals(c.getParamTypes(), command.getParamTypes());
            assertEquals(c.getParamValues(), command.getParamValues());
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testRemoveCommand() {
        try{
            db.startTransaction();
            cDao.addCommand(c,"game");
            List<Command> commands = cDao.getCommands("game");
            assertEquals(1, commands.size());
            cDao.removeCommands("game");
            commands = cDao.getCommands("game");
            assertEquals(0, commands.size());
            db.endTransaction(true);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail();
        }
    }
}
