package com.pollerexpress.models;

import java.io.Serializable;
import java.util.Objects;

public class Player implements Serializable
{
    public String name;
    public String gameId;
    /**
     * Creates a new Player object
     * @param name username of the player
     *
     * @pre name is not null
     * @pre name is the name of a user in the database
     * @post a n
     */
    public Player(String name)
    {
        this.name = name;
        this.gameId = "";
    }

    public Player(String name, String gameId)
    {
        this.name = name;
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public String getGameId() {
        return gameId;
    }

    /*
        Default Java Functions
     */

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || (!(o instanceof Player)) ) return false;
        Player player = (Player) o;
        return name.equals( player.name);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(name);
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "name='" + name + '\'' +
                ", gameId='" + gameId + '\'' +
                '}';
    }
}
