package com.plugin;

import java.io.IOException;

public interface IDatabase {
    public IUserDao getUserDao();

    public IGameDao getGameDao();

    public ICommandDao getCommandDao();

    public void startTransaction() throws IOException;

    public void endTransaction(boolean commit) throws IOException;
}
