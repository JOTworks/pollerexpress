package com.thePollerServer.utilities;

import com.shared.exceptions.NotImplementedException;
import com.shared.models.Command;
import com.shared.models.Game;
import com.shared.models.Player;

import java.util.ArrayList;

public class PersistenceProvider {

    private int delta;

    public PersistenceProvider(int delta) {
        this.delta = delta;
    }

    public void register(Player player) {
        throw new NotImplementedException("PersistenceProvider::register");
    }

    public void saveGame(Game game) {
        throw new NotImplementedException("PersistenceProvider::saveGame");
    }

    public void getUserList() {
        throw new NotImplementedException("PersistenceProvider::getUserList");
    }

    public void joinGame(Player player, Game game) {
        throw new NotImplementedException("PersistenceProvider::joinGame");
    }

    public void addGame(Game game) {
        throw new NotImplementedException("PersistenceProvider::addGame");
    }

    public void getGameList() {
        throw new NotImplementedException("PersistenceProvider::getGameList");
    }

    /**
     * @param game
     * @return all commands that have yet to be saved for this game
     */
    public ArrayList getCommandList(Game game) {
        throw new NotImplementedException("PersistenceProvider::getCommandList");
    }

    public void addCommand(Command command) {
        throw new NotImplementedException("PersistenceProvider::addCommand");
    }

    public void onServerStart() {
        throw new NotImplementedException("PersistenceProvider::onServerStart");
    }
}
