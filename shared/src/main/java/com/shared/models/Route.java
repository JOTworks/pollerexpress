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

    public Route(Route r)
    {
        cities = r.cities;
        this.distance = r.distance;
    }

    public Route(City dest, City target, int distance)
    {
        cities = new ArrayList<>(2);
        cities.set(0, dest);
        cities.set(1, target);
        this.distance = distance;
        dest.addRoute(this);
        target.addRoute(this);
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
        return (cities.get(0)).hashCode() + cities.get(1).hashCode();
    }
}
