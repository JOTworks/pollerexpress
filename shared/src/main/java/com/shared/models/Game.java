package com.shared.models;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable implements Serializable
{
    GameInfo _info;


    // the chat history for the game
    ChatHistory chatHistory = new ChatHistory();
    public ChatHistory getChatHistory() {
        return chatHistory;
    }
    public void setChatHistory(ChatHistory chatHistory) {
        this.chatHistory = chatHistory;
    }

    public void addChat(Chat chat) {
        chatHistory.addChat(chat);
    }

    List<Player> _players;


    public Game(GameInfo info)
    {
        _info = info;
    }

    public Game(GameInfo info, Player[] players)
    {
        _info = info;
        _players = new LinkedList<Player>(Arrays.asList(players) );
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
        return _info.getNumPlayers();//might be better to return
        //_players.size();
    }

    /**
     * Increase the record of the number of _players in a game
     */
    public void addPlayer(Player p)
    {
        if(!_players.contains(p))
        {
            _players.add(p);
        }
        synchronized(this)
        {
            notifyObservers(p);
        }
    }

    public void removePlayer(Player p)
    {
        for(int i = 0; i < this.getMaxPlayers(); ++i)
        {
            if(_players.get(i) != null && _players.get(i).equals(p) )
            {
                _players.remove(p);
                _info.removePlayer();
                synchronized(this)
                {
                    notifyObservers(p);
                }
            }
        }
        //could not remove player, player already in game
        //TODO: Throw an error
    }

    @Deprecated
    public int getPlayerDex(Player p)
    {
        return _players.indexOf(p);
    }

    public boolean hasPlayer(Player p)
    {
        return _players.contains(p);
    }

    /**
     *
     * @return The maximum _players allowed in this game
     */
    public int getMaxPlayers()
    {
        return _info.getMaxPlayers();
    }



    public void setPlayers(List<Player> players)
    {
        this._players = players;
    }
    @Deprecated
    public Player[] get_players()
    {
        return  null;//_players;
    }

    public List<Player> getPlayers()
    {
        return _players;
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
