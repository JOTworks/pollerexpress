package com.shared.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class Route extends Observable
{
    List<City> cities;
    int distance;
    Player owner;
    int id;//used for double routes.

    /**
     * produces a copy of the route r..
     * @param r
     */
    public Route(Route r)
    {
        this.id = r.id;
        cities = r.cities;
        this.distance = r.distance;
    }

    /**
     *The main route constructor
     * @param dest
     * @param target
     * @param distance
     */
    public Route(City dest, City target, int distance)
    {
        this.id = 0;
        cities = new ArrayList<>();
        cities.add (dest);
        cities.add(target);
        this.distance = distance;
        dest.addRoute(this);
        target.addRoute(this);
    }

    /**
     *The main route constructor
     * @param dest
     * @param target
     * @param distance
     */
    public Route(City dest, City target, int distance, int id)
    {
        this(dest, target, distance);
        this.id = id;
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
            notifyObservers(player);
        }
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
        return route.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode()
    {
        return (cities.get(0)).hashCode() + cities.get(1).hashCode() +id;
    }
}
