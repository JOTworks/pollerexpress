package com.sql;

import com.plugin.ICommandDao;
import com.plugin.IDatabase;
import com.plugin.IGameDao;
import com.plugin.IUserDao;
import com.shared.exceptions.database.DatabaseException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDatabase implements IDatabase{
    final String url;
    Connection conn;
    SQLUserDao uDao;
    SQLGameDao gDao;
    SQLCommandDao cDao;

    public SQLDatabase() throws IOException {
        url = "jdbc:sqlite:db.sqlite3";

        uDao = new SQLUserDao(this);
        gDao = new SQLGameDao(this);
        cDao = new SQLCommandDao(this);

        this.open();
        if(this.getConnection() == null) {
            System.out.println("WHY ISN'T IT OPEN");
        }
        createTables();
        this.close(true);
    }

    public static void rebuildDB() throws IOException {
        SQLDatabase db = new SQLDatabase();
        db.open();
        db.deleteTables();
        db.createTables();
        db.close(true);
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

    private void open() {
        if(this.getConnection() != null) {
            System.out.print("Tried to open an open line");
            return;
        }
        try {
            this.conn = DriverManager.getConnection(this.url);
            this.conn.setAutoCommit(false);
        } catch (SQLException e) {
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
    public void endTransaction(boolean commit) {
        this.close(commit);
    }
}
