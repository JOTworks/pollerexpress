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
    public int points;
    public int trainCount;
    public Color.PLAYER color;

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
        this.points = 0;
        this.trainCount = 0;
        this.routes = new ArrayList<>();
        this.color = Color.PLAYER.BLACK;
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
    public int getTrainCount(){return trainCount;}
    public void setTrainCount(int trainCount) {this.trainCount = trainCount;}
    public int getPoints(){return points;}
    public void setPoints(int points){this.points = points;}
    public String getGameId()
    {
        return gameId;
    }
    public void setGameID(String gameId){ this.gameId = gameId;}
    public Color.PLAYER getColor(){return this.color; }
    public void setColor(Color.PLAYER color)
    {
        this.color = color;
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(color);
        }
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
            this.setChanged();
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
            this.setChanged();
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
        return name.hashCode();
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
