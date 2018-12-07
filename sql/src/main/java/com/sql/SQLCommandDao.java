package com.sql;

import com.plugin.ICommandDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;
import com.shared.utilities.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLCommandDao implements ICommandDao {
    SQLDatabase _db;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `COMMANDS`\n" +
            "(`COMMAND_ID` INTEGER PRIMARY KEY, `GAME_ID` TEXT NOT NULL, `COMMAND_OBJ` BLOB)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS `COMMANDS`";
    private static final String SELECT_COMMANDS = "SELECT * FROM `COMMANDS` WHERE `GAME_ID` = ? ORDER BY `COMMAND_ID` ASC";
    private static final String INSERT_COMMAND = "INSERT INTO `COMMANDS` (`GAME_ID`,`COMMAND_OBJ`) VALUES (?,?)";
    private static final String DELETE_COMMANDS = "DELETE FROM `COMMANDS` WHERE `GAME_ID` = ?";

    public SQLCommandDao(SQLDatabase db)
    {
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
    public List<Command> getCommands(String gameId) throws IOException {
        ArrayList<Command> commands = new ArrayList<>();
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(SELECT_COMMANDS);
            stmnt.setString(1, gameId);
            ResultSet rs = stmnt.executeQuery();
            while(rs.next()) {
                ByteArrayInputStream stream = new ByteArrayInputStream(rs.getBytes("COMMAND_OBJ"));
                commands.add( (Command) Serializer.readData(stream) );
                stream.close();
            }
            rs.close();
            stmnt.close();
        } catch(Exception e) {
            throw new DatabaseException(e.getMessage());
        }
        return commands;
    }

    @Override
    public void addCommand(Command c, String gameId) throws IOException {
        try{
            PreparedStatement stmnt = _db.getConnection().prepareStatement(INSERT_COMMAND);
            stmnt.setString(1, gameId);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Serializer.writeData(c,stream);
            stmnt.setBytes(2, stream.toByteArray());
            stmnt.execute();
            stmnt.close();
            stream.close();
        } catch(SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void removeCommands(String gameId) throws IOException {
        try {
            PreparedStatement stmnt = _db.getConnection().prepareStatement(DELETE_COMMANDS);
            stmnt.setString(1, gameId);
            stmnt.execute();
            stmnt.close();
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
