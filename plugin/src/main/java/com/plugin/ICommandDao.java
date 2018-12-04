package com.plugin;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Command;

import java.util.ArrayList;

public interface ICommandDao {
    public Command getCommand(int id) throws DatabaseException;
    public ArrayList<Command> getGameCommands(String gameId) throws DatabaseException;
    public void addCommand(Command c) throws DatabaseException;
    public void removeCommand(Command c) throws DatabaseException;
    public void removeGameCommands(String gameId) throws DatabaseException;
}
