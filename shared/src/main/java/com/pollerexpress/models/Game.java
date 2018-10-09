package com.pollerexpress.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Game implements Serializable
{
    GameInfo _info;



    Player[] _players;


    public Game(GameInfo info)
    {
        _info = info;
    }

    public Game(GameInfo info, Player[] players)
    {
        _info = info;
        _players = players;
    }
    /*
     -----------------------------------------------------------------------
                                  Getters & Setters
     -----------------------------------------------------------------------
     */

    /**
     *
     * @return Returns a non linked copy of the games info
     */
    public GameInfo getGameInfo()
    {
        return new GameInfo(_info);
    }
    /**
     * Getter for gameId
     * @return the id of the game this game info is connected to.
     */
    public String getId()
    {
        return _info.getId();
    }

    /**
     * Getter for game name
     * @return name of the game
     */
    public String getName()
    {
        return _info.getName();
    }

    /**
     *
     * @return number of _players in the game.
     */
    public int getNumPlayers()
    {
        return _info.getNumPlayers();
    }

    /**
     * Increase the record of the number of _players in a game
     */
    public void addPlayer(Player p)
    {
        for(int i = 0; i < this.getMaxPlayers(); ++i)
        {
            if(_players[i] == null)
            {
                _players[i] = p;
                _info.addPlayer();
                return;
            }

        }
            //could not add player, too many _players in the game.
            //TODO: Throw an error
    }

    public void removePlayer(Player p)
    {
        for(int i = 0; i < this.getMaxPlayers(); ++i)
        {
            if(_players[i] != null && _players[i].equals(p) )
            {
                _players[i] = null;
                _info.removePlayer();
            }
        }
        //could not remove player, player already in game
        //TODO: Throw an error
    }

    /**
     *
     * @return The maximum _players allowed in this game
     */
    public int getMaxPlayers()
    {
        return _info.getMaxPlayers();
    }





    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Game))
        {
            return false;
        }
        Game game = (Game) o;
        return _info.equals( game.getGameInfo() );
    }

    @Override
    public int hashCode()
    {
        return _info.hashCode();
    }
}
