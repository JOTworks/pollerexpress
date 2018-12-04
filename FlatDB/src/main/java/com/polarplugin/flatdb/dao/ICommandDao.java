package com.polarplugin.flatdb.dao;

import com.shared.models.Command;

import java.io.FileNotFoundException;
import java.util.List;

interface ICommandDao {

    public List<Command> getCommands(String gameId) throws FileNotFoundException;

    public void addCommand(Command command, String gameId);

    public void clearCommands();

}
