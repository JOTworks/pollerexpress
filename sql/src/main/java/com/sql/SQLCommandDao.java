package com.sql;

import com.plugin.ICommandDao;
import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<Command> getCommands(String gameId) throws IOException {
        return null;
    }

    @Override
    public void addCommand(Command c, String gameId) throws IOException {

    }

    @Override
    public void removeCommands(String gameId) throws IOException {

    }

    @Override
    public void clearAllCommands() throws IOException {

    }
}
