package com.sql;

import com.plugin.IUserDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDao implements IUserDao {
    SQLDatabase _db;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `USERS`\n" +
            "(`USERNAME` TEXT NOT NULL PRIMARY KEY, `PASSWORD` TEXT NOT NULL, `CURRENT_GAME` TEXT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS `USERS`";
    private static final String SELECT_USER = "SELECT * FROM `USERS` WHERE `USERNAME` = ?";
    private static final String INSERT_USER = "INSERT INTO `USERS`\n" +
            "(`USERNAME`, `PASSWORD`, `CURRENT_GAME`) VALUES (?,?,?)";
    private static final String UPDATE_USER = "UPDATE `USERS` SET\n" +
            "`PASSWORD` = ?, `CURRENT_GAME` = ? WHERE `USERNAME` = ?";

    public SQLUserDao(SQLDatabase db) {
        _db = db;
    }

    public void createTable() throws DatabaseException{
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(CREATE_TABLE);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteTable() throws DatabaseException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DROP_TABLE);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public User getUser(String username) throws DatabaseException {
        User user = null;
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_USER);
            stmnt.setString(1, username);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                user = new User(rs.getString("USERNAME"),rs.getString("PASSWORD"),rs.getString("CURRENT_GAME"));
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return user;
    }

    @Override
    public void addUser(User user) throws DatabaseException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_USER);
            stmnt.setString(1, user.getName());
            stmnt.setString(2, user.password);
            stmnt.setString(3, user.getGameId());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) throws DatabaseException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE_USER);
            stmnt.setString(1, user.password);
            stmnt.setString(2, user.getGameId());
            stmnt.setString(3, user.getName());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
