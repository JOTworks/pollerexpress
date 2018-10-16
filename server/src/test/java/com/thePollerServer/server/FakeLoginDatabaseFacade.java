package com.thePollerServer.server;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Authtoken;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.Player;
import com.shared.models.User;

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
