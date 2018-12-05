package com.polarplugin.flatdb;


import com.plugin.ICommandDao;
import com.plugin.IDatabase;
import com.plugin.IGameDao;
import com.plugin.IUserDao;
import com.polarplugin.flatdb.dao.FlatCommandDao;
import com.polarplugin.flatdb.dao.FlatGameDao;
import com.polarplugin.flatdb.dao.FlatUserDao;

import java.io.IOException;

public class FlatDatabase implements IDatabase {


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
}
