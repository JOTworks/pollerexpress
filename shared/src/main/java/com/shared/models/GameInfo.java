package com.shared.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * This class contains just the game information
 * you need from the game selection screen.
 * Leaves out information only needed by those
 * actually in the game.
 */
public class GameInfo implements Serializable
{
    private String _id;
    private String _name;
    private int _numPlayers;
    private int _maxPlayers;

    public GameInfo(String name, int maxPlayers)
    {
        _id = UUID.randomUUID().toString();
        _name = name;
        _numPlayers = 0;
        _maxPlayers = maxPlayers;
    }
    
    public GameInfo(String id, String name, int maxPlayers)
    {
        _id = id;
        _name = name;
        _numPlayers = 0;
        _maxPlayers = maxPlayers;
    }

    public GameInfo(String id, String name, int maxPlayers, int curPlayers)
    {
        this(id, name, maxPlayers);
        _numPlayers = curPlayers;
    }

    public GameInfo(GameInfo info)
    {
        _id = info._id;
        _name = info._name;
        _numPlayers = info._numPlayers;
        _maxPlayers =  info._maxPlayers;
    }
    /*
     -----------------------------------------------------------------------
                                  Getters & Setters
     -----------------------------------------------------------------------
     */

    /**
     * Getter for gameId
     * @return the rotation of the game this game info is connected to.
     */
    public String getId()
    {
        return _id;
    }

    /**
     * Getter for game name
     * @return name of the game
     */
    public String getName()
    {
        return _name;
    }

    /**
     *
     * @return number of _players in the game.
     */
    public int getNumPlayers()
    {
        return _numPlayers;
    }

    /**
     * Increase the record of the number of _players in a game
     */
    public void addPlayer()
    {
        this._numPlayers += 1;
        if(this._numPlayers > _maxPlayers )
        {
            //TODO: throw an exception for trying to add too many _players
            //maybe throw an exception
        }
    }
    /**
     * Increase the record of the number of _players in a game
     */
    public void removePlayer()
    {
        this._numPlayers -= 1;
        if(this._numPlayers < 1)
        {
            //TODO: throw an exception for removing all of the _players
            //maybe throw an exception
        }
    }

    /**
     *
     * @return The maximum _players allowed in a game
     */
    public int getMaxPlayers()
    {
        return _maxPlayers;
    }

    /*

        Default Java Functionality.


     */
    @Override
    public String toString()
    {
        return String.format("Game{'%s','%s' - %s/%s}",_name,_id,_numPlayers,_maxPlayers);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameInfo gameInfo = (GameInfo) o;
        return Objects.equals(_id, gameInfo._id);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(_id);
    }
}
