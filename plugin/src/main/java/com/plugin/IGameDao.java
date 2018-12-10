package com.plugin;

import com.plugin.models.ServerGame;
import com.shared.models.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface IGameDao {
    public ServerGame getGame(String id) throws IOException;

    public List<ServerGame> getAllGames() throws IOException;

    public void addGame(ServerGame game) throws IOException;

    public void updateGame(ServerGame game) throws IOException;

    public void deleteGame(ServerGame game) throws IOException;
}
