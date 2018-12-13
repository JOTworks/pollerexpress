package com.polarplugin.flatdb;


import com.plugin.ICommandDao;
import com.plugin.IDatabase;
import com.plugin.IGameDao;
import com.plugin.IUserDao;
import com.polarplugin.flatdb.dao.FlatCommandDao;
import com.polarplugin.flatdb.dao.FlatGameDao;
import com.polarplugin.flatdb.dao.FlatUserDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FlatDatabase implements IDatabase {

    public FlatDatabase() throws IOException {
        try {
            if (!Files.exists(Paths.get("allGames.txt")))
                new FlatGameDao().createAllGamesFile();
            if (!Files.exists(Paths.get("allUsers.txt")))
                new FlatUserDao().createAllUsersFile();
            if (!Files.exists(Paths.get("games")))
                Files.createDirectories(Paths.get("games"));
        } catch (Exception e) {
            System.out.println("unable to start FlatDB. Check thrown error");
            throw e;
        }
    }

    @Override
    public IUserDao getUserDao() {
        return new FlatUserDao();
    }

    @Override
    public IGameDao getGameDao() {
        return new FlatGameDao();
    }

    @Override
    public ICommandDao getCommandDao() {
        return new FlatCommandDao();
    }

    @Override
    public void startTransaction() throws IOException {
        // do nothing
    }

    @Override
    public void endTransaction(boolean commit) throws IOException {
        // do nothing
    }

    @Override
    public void resetDatabase() throws IOException {
            new FlatCommandDao().clearAllCommands();
            new FlatGameDao().clearGames();
            new FlatUserDao().clearUsers();
    }
}
