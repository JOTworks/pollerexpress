package com.pollerexpress.models;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.reponse.LoginResponse;

public interface IDatabaseFacade
{
    LoginResponse login(User user);
    void register(User user) throws DatabaseException;
    Game join(Player player, GameInfo info) throws DatabaseException;
    void leave(Player player, GameInfo info) throws DatabaseException;
    void create(Player player, Game game) throws DatabaseException;
    boolean validate(Authtoken token) throws DatabaseException;
    Game getGame(GameInfo info) throws DatabaseException;
    Player getPlayer(String user) throws DatabaseException;
    GameInfo getGameInfo(String id) throws DatabaseException;
    Player[] getPlayersInGame(GameInfo info) throws DatabaseException;
}
