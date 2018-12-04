package com.plugin;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Game;

import java.util.ArrayList;

public interface IGameDao {
    public Game getGame(String id) throws DatabaseException;
    public ArrayList<Game> getAllGames() throws DatabaseException;
    public void addGame(Game game) throws DatabaseException;
    public void updateGame(Game game) throws DatabaseException;
    public void deleteGame(Game game) throws DatabaseException;
}
