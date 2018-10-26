package com.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class Player extends Observable implements Serializable
{
    public String name;
    public String gameId;
    public int destinationDiscardCount;
    public int destinationCardCount;
    public int trainCardCount;
    public List<Route> routes;

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
        destinationDiscardCount = 0;
        destinationCardCount = 0;
        trainCardCount = 0;

        this.routes = new ArrayList<>();
    }

    public Player(String name, String gameId)
    {
        this(name);
        this.gameId = gameId;
    }
    public Player(String name, String gameId, int destinationDiscards)
    {
        this(name, gameId);
        this.destinationDiscardCount = destinationDiscards;
    }

    public String getName()
    {
        return name;
    }

    public String getGameId()
    {
        return gameId;
    }


    public int getDestinationCardCount()
    {
        return destinationCardCount;
    }

    public void setDestinationCardCount(int destinationCards)
    {
        this.destinationCardCount = destinationCards;
        synchronized (this)
        {
            this.notifyObservers();
        }
    }

    public int getDestinationDiscardCount()
    {
        return destinationDiscardCount;
    }

    public void setDestinationDiscardCount(int destinationDiscardCount)
    {
        this.destinationDiscardCount = destinationDiscardCount;
        synchronized (this)
        {
            this.notifyObservers();
        }
    }

    public int getTrainCardCount()
    {
        return trainCardCount;
    }

    public void setTrainCardCount(int trainCardCount)
    {
        this.trainCardCount = trainCardCount;
        synchronized (this)
        {
            this.notifyObservers();
        }
    }

    public List<Route> getRoutes()
    {
        return routes;
    }

    public void setRoutes(List<Route> routes)
    {
        this.routes = routes;
        synchronized (this)
        {
            this.notifyObservers();
        }
    }

    public void addRoute(Route route)
    {
        this.routes.add(route);
        synchronized (this)
        {
            this.notifyObservers(route);
        }
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
