package com.plugin;

import com.shared.models.Command;

import java.io.IOException;
import java.util.List;

public interface ICommandDao {
    public List<Command> getCommands(String gameId) throws IOException;

    public void addCommand(Command c, String gameId) throws IOException;

    public void removeCommands(String gameId) throws IOException;

    public void clearAllCommands() throws IOException;
}
