package com.plugin;

public interface IDatabase {
    public IUserDao getUserDao();
    public IGameDao getGameDao();
    public ICommandDao getCommandDao();
}
