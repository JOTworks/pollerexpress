package com.sql;

import com.plugin.ICommandDao;
import com.plugin.IDatabase;
import com.plugin.IGameDao;
import com.plugin.IUserDao;
import com.shared.exceptions.database.DatabaseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabase implements IDatabase
{
    final String url;
    boolean isOpen = false;
    Connection conn;
    SQLUserDao uDao;
    SQLGameDao gDao;
    SQLCommandDao cDao;

    static {
        try
        {
            final String driver = "org.sqlite.JDBC";
            File myJar = new File ("plugins/", "sqlite-jdbc.jar");
            URL pluginURL = myJar.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{pluginURL});
            Class c = Class.forName(driver, true, loader);
            Driver d = (Driver) c.newInstance();
            DriverManager.registerDriver(new SQLDriver(d));
        }
        catch (Exception e)
        {
            System.out.print("THERE WAS AN ERROR\n");
            e.printStackTrace();
            System.out.print("THERE WAS AN ERROR\n");
        }
    }

    public SQLDatabase() throws DatabaseException {
        url = "jdbc:sqlite:db.sqlite3";

        uDao = new SQLUserDao(this);
        gDao = new SQLGameDao(this);
        cDao = new SQLCommandDao(this);
        try {
            this.open();
            createTables();
            this.close(true);
        } catch(IOException e) {
            this.close(false);
            throw new DatabaseException(e.getMessage());
        }
    }

    public void resetDatabase() throws IOException
    {
        SQLDatabase db = new SQLDatabase();
        try
        {

            db.open();
            db.deleteTables();
            db.createTables();
            db.close(true);
        }
        finally
        {
            db.close(false);
        }
    }

    private void createTables() throws IOException {
        uDao.createTable();
        gDao.createTable();
        cDao.createTable();
    }

    private void deleteTables() throws IOException {
        uDao.deleteTable();
        gDao.deleteTable();
        cDao.deleteTable();
    }

    private void open()
    {
        if(this.getConnection() != null)
        {
            System.out.println("Tried to open an open line");
            return;
        }
        try
        {
            this.conn = DriverManager.getConnection(this.url);
            this.conn.setAutoCommit(false);
            this.isOpen = true;
        } catch (SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }

    private void close(boolean commit) {
        if(this.getConnection() == null) {
            return;
        }
        try {
            if (commit) {
                this.conn.commit();
            }

            this.conn.close();
            this.conn = null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }

    @Override
    public IUserDao getUserDao() {
        return uDao;
    }

    @Override
    public IGameDao getGameDao() {
        return gDao;
    }

    @Override
    public ICommandDao getCommandDao() {
        return cDao;
    }

    @Override
    public void startTransaction() {
        this.open();
    }

    @Override
    public void endTransaction(boolean commit)
    {
        this.close(commit);
    }


}
