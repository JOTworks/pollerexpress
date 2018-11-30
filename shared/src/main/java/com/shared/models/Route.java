package com.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Route extends Observable implements Serializable
{
    String id;
    List<City> cities;
    int distance;
    Player owner;
    Color.TRAIN color;

    public int rotation;//used for double routes.

    public Route(String id, City city1, City city2, int distance, Player owner, Color.TRAIN color, int rotation)
    {
        this.id = id;
        cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);
        this.distance = distance;
        this.owner = owner;
        this.color = color;
        this.rotation = rotation;
    }

    /**
     * Constructs basic gray routes
     * @param dest
     * @param target
     * @param distance the number of cards that must
     *                 be placed on the route
     */
    public Route(City dest, City target, int distance)
    {
        this.rotation = 0;
        cities = new ArrayList<>();
        if(dest.getName().compareTo(target.getName()) < 0)
        {

            cities.add (dest);
            cities.add(target);
        }
        else
        {
            cities.add(target);
            cities.add (dest);
        }
        this.distance = distance;
        dest.addRoute(this);
        target.addRoute(this);
        this.color= Color.TRAIN.RAINBOW;
    }

    /**
     * The main route constructor
     * @param dest
     * @param target
     * @param distance the number of cards that must
     *                 be placed on the route
     * @param rot rotation for route
     * @param color the color of cards the must
     *              be placed on the route
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
     * @param distance the number of cards that must
     *                 be placed on the route
     */
    public Route(City dest, City target, int distance, int rot)
    {
        this(dest, target, distance);
        this.rotation = rot;
    }

    public String getId() { return toString(); }

    /**
     * Given a city, gets the adjacent city on this route.
     * @param myCity a city on the map
     * @return the adjacent city on this route.
     */
    public City getDestination(City myCity)
    {
        if(cities.get(0).equals(myCity))
        {
            return cities.get(1);
        }
        else if( cities.get(1).equals(myCity))
        {
            return cities.get(0);
        }

        assert(false);

        return null;//TODO throw exception raise a problem
    }

    @Override
    public String toString()
    {
        return "Path" +cities.toString() + String.valueOf(rotation);
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
     * Ideally this would return an unmodifiable list
     * of the cities on this route. There will only
     * ever be two.
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

    public int getRouteValue() {
        int routeValue = 0;
        switch (distance) {
            case 1: routeValue = 1;
                break;
            case 2: routeValue = 2;
                break;
            case 3: routeValue = 4;
                break;
            case 4: routeValue = 7;
                break;
            case 5: routeValue = 10;
                break;
            case 6: routeValue = 15;
        }
        return routeValue;
    }

    /**
     * check if two routes are part of a double route
     * @param r
     * @return
     */
    public boolean isDoubleRoute(Route r)
    {
        if(this.cities.get(0).equals(r.cities.get(0)))
        {
            return this.cities.get(1).equals(r.cities.get(1));
        }
        else
        {
            return this.cities.get(0).equals(r.cities.get(1)) && this.cities.get(1).equals(r.cities.get(0));
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        if( this.rotation != route.rotation )
        {
            return false;
        }
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
        return toString().hashCode();
    }
}
