package com.pollerexpress.models;

import com.pollerexpress.database.exceptions.DatabaseException;

public interface IDatabaseFacade
{
    LoginResponse login(User user);
    LoginResponse register(User user);
    Game join(Player player, GameInfo info) throws DatabaseException;
    void leave(Player player, GameInfo info) throws DatabaseException;
    void create(Player player, Game game) throws DatabaseException;

}
