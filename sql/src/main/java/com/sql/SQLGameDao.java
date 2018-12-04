package com.sql;

import com.plugin.IGameDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Game;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLGameDao implements IGameDao {
    SQLDatabase _db;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `GAMES`\n" +
            "(`GAME_ID` TEXT NOT NULL PRIMARY KEY, `GAME_OBJ` BLOB)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS `GAMES`";
    private static final String SELECT_GAME = "SELECT * FROM `GAMES` WHERE `GAME_ID` = ?";
    private static final String SELECT_ALL_GAMES = "SELECT * FROM `GAMES`";
    private static final String INSERT_GAME = "INSERT INTO `GAMES`\n" +
            "(`GAME_ID`, `GAME_OBJ`) VALUES (?,?)";
    private static final String UPDATE_GAME = "UPDATE `GAMES` SET `GAME_OBJ` = ? WHERE `GAME_ID` = ?";
    private static final String DELETE_GAME = "DELETE `GAMES` WHERE `GAME_ID` = ?";

    public SQLGameDao(SQLDatabase db) {
        _db = db;
    }

    public void createTable() throws DatabaseException {
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
    public Game getGame(String id) throws DatabaseException {
        Game game = null;
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_GAME);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                byte[] blob = rs.getBytes("GAME_OBJ");
                //how to unserialize blob???
                //TODO: SERIALIZE BLOB
            }
            rs.close();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return game;
    }

    @Override
    public ArrayList<Game> getAllGames() throws DatabaseException {
        return null;
    }

    @Override
    public void addGame(Game game) throws DatabaseException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DROP_TABLE);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void updateGame(Game game) throws DatabaseException {

    }

    @Override
    public void deleteGame(Game game) throws DatabaseException {

    }
}
