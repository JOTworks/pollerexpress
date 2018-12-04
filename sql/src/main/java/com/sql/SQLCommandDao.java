package com.sql;

import com.plugin.ICommandDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;

import java.util.ArrayList;

public class SQLCommandDao implements ICommandDao {
    SQLDatabase _db;
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `COMMANDS`\n" +
            "(`COMMAND_ID` INTEGER PRIMARY KEY, `GAME_ID` TEXT NOT NULL, `COMMAND_OBJ` BLOB)";

    public SQLCommandDao(SQLDatabase db) {
        _db = db;
    }

    public void createTable() throws DatabaseException {
        //
    }

    public void deleteTable() throws DatabaseException {
        //
    }

    @Override
    public Command getCommand(int id) throws DatabaseException {
        return null;
    }

    @Override
    public ArrayList<Command> getGameCommands(String gameId) throws DatabaseException {
        return null;
    }

    @Override
    public void addCommand(Command c) throws DatabaseException {

    }

    @Override
    public void removeCommand(Command c) throws DatabaseException {

    }

    @Override
    public void removeGameCommands(String gameId) throws DatabaseException {

    }
}
