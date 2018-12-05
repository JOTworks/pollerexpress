package com.polarplugin.flatdb.dao;

import com.plugin.IGameDao;
import com.shared.models.Game;

import java.io.IOException;
import java.util.ArrayList;

public class FlatGameDao implements IGameDao {
    @Override
    public Game getGame(String id) throws IOException {
        return null;
    }

    @Override
    public ArrayList<Game> getAllGames() throws IOException {
        return null;
    }

    @Override
    public void addGame(Game game) throws IOException {

    }

    @Override
    public void updateGame(Game game) throws IOException {

    }

    @Override
    public void deleteGame(Game game) throws IOException {

    }
}
