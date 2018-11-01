package com.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Route extends Observable implements Serializable
{
    List<City> cities;
    int distance;
    Player owner;
    Color.TRAIN color;
    public int rotation;//used for double routes.
    /**
     *Constructs basic gray routes
     * @param dest
     * @param target
     * @param distance
     */
    public Route(City dest, City target, int distance)
    {
        this.rotation = 0;
        cities = new ArrayList<>();
        cities.add (dest);
        cities.add(target);
        this.distance = distance;
        dest.addRoute(this);
        target.addRoute(this);
        this.color= Color.TRAIN.RAINBOW;
    }

    /**
     * The main route constructor
     * @param dest
     * @param target
     * @param distance
     * @param rot rotation for route
     * @param color
     */
    public Route(City dest, City target, int distance, int rot, Color.TRAIN color)
    {
        this(dest, target, distance, rot);
        this.color = color;
    }

    /**
     *Constructs curved gray routes
     * @param dest
     * @param target
     * @param rot rotation for route
     * @param distance
     */
    public Route(City dest, City target, int distance, int rot)
    {
        this(dest, target, distance);
        this.rotation = rot;
    }

    public City getDestination(City me)
    {
        if(cities.get(0).equals(me))
        {
            return cities.get(1);
        }
        else if( cities.get(1).equals(me))
        {
            return cities.get(0);
        }

        assert(false);

        return null;//TODO throw exception raise a problem
    }

    @Override
    public String toString()
    {
        return "Path" +cities.toString();
    }

    /**
     * returns the number of train cards needed to reach the end
     * @return
     */
    public int getDistance()
    {
        return distance;
    }

    /**
     * Ideally this would return an unmodificable list.
     * @return a list
     */
    public List<City> getCities()
    {
        return cities;
    }

    public void setOwner(Player player)
    {
        this.owner = player;
        synchronized (this)
        {
            setChanged();
            notifyObservers(player);
        }
    }

    public int getRotation()
    {
        return rotation;
    }

    public Color.TRAIN getColor()
    {
        return color;
    }

    public Player getOwner()
    {
        return owner;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;

        if(this.cities.get(0).equals(route.cities.get(0)))
        {
            return this.cities.get(1).equals(route.cities.get(1));
        }
        else
        {
            return this.cities.get(0).equals(route.cities.get(1)) && this.cities.get(1).equals(route.cities.get(0)) ;
        }
    }

    @Override
    public int hashCode()
    {
        return (cities.get(0)).hashCode() + cities.get(1).hashCode() + rotation;
    }
}
