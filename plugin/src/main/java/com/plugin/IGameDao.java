package com.plugin;

import com.shared.models.Game;

import java.io.IOException;
import java.util.ArrayList;

public interface IGameDao {
    public Game getGame(String id) throws IOException;

    public ArrayList<Game> getAllGames() throws IOException;

    public void addGame(Game game) throws IOException;

    public void updateGame(Game game) throws IOException;

    public void deleteGame(Game game) throws IOException;
}
