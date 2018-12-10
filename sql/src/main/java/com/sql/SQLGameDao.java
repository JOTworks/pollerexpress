package com.sql;

import com.plugin.IGameDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.utilities.Serializer;
import com.plugin.models.ServerGame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

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
    private static final String DELETE_GAME = "DELETE FROM `GAMES` WHERE `GAME_ID` = ?";

    public SQLGameDao(SQLDatabase db) {
        _db = db;
    }

    public void createTable() throws IOException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(CREATE_TABLE);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public void deleteTable() throws IOException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DROP_TABLE);
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public ServerGame getGame(String id) throws IOException {
        ServerGame game = null;
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_GAME);
            stmnt.setString(1, id);
            ResultSet rs = stmnt.executeQuery();
            if(rs.next()) {
                ByteArrayInputStream stream = new ByteArrayInputStream(rs.getBytes("GAME_OBJ"));
                game = (ServerGame)Serializer.readData(stream);
                stream.close();
            }
            rs.close();
            stmnt.close();
        } catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return game;
    }

    @Override
    public ArrayList<ServerGame> getAllGames() throws IOException {
        ArrayList<ServerGame> games = new ArrayList<>();
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_ALL_GAMES);
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                ByteArrayInputStream stream = new ByteArrayInputStream(rs.getBytes("GAME_OBJ"));
                games.add( (ServerGame)Serializer.readData(stream) );
                stream.close();
            }
            rs.close();
            stmnt.close();
        } catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return games;
    }

    @Override
    public void addGame(ServerGame game) throws IOException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_GAME);
            stmnt.setString(1, game.getId());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Serializer.writeData(game,stream);
            stmnt.setBytes(2, stream.toByteArray());
            stmnt.execute();
            stmnt.close();
            stream.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void updateGame(ServerGame game) throws IOException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(UPDATE_GAME);
            stmnt.setString(2, game.getId());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Serializer.writeData(game,stream);
            stmnt.setBytes(1, stream.toByteArray());
            stmnt.execute();
            stmnt.close();
            stream.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void deleteGame(ServerGame game) throws IOException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DELETE_GAME);
            stmnt.setString(1, game.getId());
            stmnt.execute();
            stmnt.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
