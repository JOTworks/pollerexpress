package com.pollerexpress.server;

import com.pollerexpress.database.exceptions.DatabaseException;
import com.pollerexpress.models.Authtoken;
import com.pollerexpress.models.Game;
import com.pollerexpress.models.GameInfo;
import com.pollerexpress.models.IDatabaseFacade;
import com.pollerexpress.reponse.LoginResponse;
import com.pollerexpress.models.Player;
import com.pollerexpress.models.User;

public class FakeLoginDatabaseFacade implements IDatabaseFacade
{
    @Override
    public LoginResponse login(User user)
    {
        return null;
    }

    @Override
    public LoginResponse register(User user)
    {
        return null;
    }

    @Override
    public Game join(Player player, GameInfo info) throws DatabaseException
    {
        return null;
    }
    @Override
    public void leave(Player player, GameInfo info) throws DatabaseException
    {
        return;
    }
    @Override
    public void create(Player player, Game game) throws DatabaseException
    {

    }

    @Override
    public boolean validate(Authtoken token) throws DatabaseException
    {
        return true;
    }
    @Override
    public Game getGame(GameInfo info) throws DatabaseException
    {
        return null;
    }
    @Override
    public Player getPlayer(String user) throws DatabaseException
    {
        return null;
    }

    @Override
    public GameInfo getGameInfo(String id) throws DatabaseException
    {
        return null;
    }
}
