package com.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class City implements Serializable
{
    String name;
    Point point;

    List<Route> routes;

    public City(City toCopy)
    {
        this(toCopy.name, toCopy.point);
        //how to do this....
    }

    /**
     * returns a city
     * @param name
     * @param p
     */

    public City(String name, Point p)
    {

        this.name = name;
        this.point = p;
        this.routes = new ArrayList<>();
    }

    public Point getPoint()
    {
        return point;
    }

    public City setRoutes(List<Route> routes)
    {
        this.routes = routes;
        return this;
    }

    public List<Route> getRoutes()
    {
        return routes;
    }

    public City addRoute(Route route)
    {
        this.routes.add(route);
        return this;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || (!(o instanceof City))) return false;
        return ((City) o).getName().equals(this.name);
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }


    @Override
    public String toString()
    {
        return name;
    }
}
