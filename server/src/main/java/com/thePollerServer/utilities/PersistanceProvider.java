package com.thePollerServer.utilities;

import com.shared.exceptions.NotImplementedException;
import com.shared.models.Command;
import com.shared.models.Game;
import com.shared.models.Player;

import java.util.ArrayList;

public class PersistanceProvider {

    private int delta;

    public PersistanceProvider(int delta) {
        this.delta = delta;
    }

    public void register(Player player) {
        throw new NotImplementedException("register");
    }

    public void saveGame(Game game) {
        throw new NotImplementedException("register");
    }

    public void getUserList() {
        throw new NotImplementedException("register");
    }

    public void joinGame(Player player, Game game) {
        throw new NotImplementedException("register");
    }

    public void addGame(Game game) {
        throw new NotImplementedException("register");
    }

    public void getGameList() {
        throw new NotImplementedException("register");
    }

    /**
     * @param game
     * @return all commands that have yet to be saved for this game
     */
    public ArrayList getCommandList(Game game) {
        throw new NotImplementedException("register");
    }

    public void addCommand(Command command) {
        throw new NotImplementedException("register");
    }

    public void onServerStart() {
        throw new NotImplementedException("register");
    }
}
