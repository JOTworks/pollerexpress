package com.plugin;

import com.shared.exceptions.database.DatabaseException;

public interface IDatabase {
    public IUserDao getUserDao();
    public IGameDao getGameDao();
    public ICommandDao getCommandDao();
    public void startTransaction() throws DatabaseException;
    public void endTransaction(boolean commit) throws DatabaseException;
}
