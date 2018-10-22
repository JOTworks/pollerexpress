package com.shared.models;

import java.util.ArrayList;
import java.util.List;

public class City
{
    String name;
    Point point;

    List<Route> routes;
    public City(String name, Point p)
    {
        this.name= name;
        this.point = p;
        this. routes = new ArrayList<>(routes);
    }

    public City setRoutes(List<Route> routes)
    {
        this.routes=  routes;
        return this;
    }

    public City addRoute(Route route)
    {
        this.routes.add(route);
        return this;
    }
}
