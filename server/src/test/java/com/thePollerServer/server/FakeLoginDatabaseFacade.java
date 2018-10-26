package com.thePollerServer.server;

import com.shared.exceptions.database.DatabaseException;
import com.shared.models.Authtoken;
import com.shared.models.DestinationCard;
import com.shared.models.Game;
import com.shared.models.GameInfo;
import com.shared.models.interfaces.IDatabaseFacade;
import com.shared.models.reponses.LoginResponse;
import com.shared.models.Player;
import com.shared.models.User;

import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;

public class FakeLoginDatabaseFacade implements IDatabaseFacade
{

    @Override
    public LoginResponse login(User user)
    {
        return null;
    }

    @Override
    public void register(User user)
    {
        //return null;
    }

    @Override
    public Game join(Player player, GameInfo info) throws DatabaseException
    {
        return null;
    }

    @Override
    public void leave(Player player, GameInfo info) throws DatabaseException
    {

    }

    @Override
    public void create(Player player, Game game) throws DatabaseException
    {

    }

    @Override
    public boolean validate(Authtoken token) throws DatabaseException
    {
        return false;
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

    @Override
    public Player[] getPlayersInGame(GameInfo info) throws DatabaseException
    {
        return new Player[0];
    }

    @Override
    public List<DestinationCard> drawDestinationCards(Player player, int canDiscard) throws DatabaseException
    {
        return null;
    }

    @Override
    public void discardDestinationCard(Player player, List<DestinationCard> cards) throws DatabaseException
    {
        //do nothing.
    }

    @Override
    public int getPlayerDiscards(Player player) throws DatabaseException
    {
        if(player.getName().equals("Torsten") )
        {
            return 2;
        }
        else
        {
            return 1;
        }
    }

}

