package com.shared.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class City
{
    String id;
    String name;
    Point point;

    List<Route> routes;
    public City(String name, Point p)
    {
        this.id = UUID.randomUUID().toString();
        this.name= name;
        this.point = p;
        this. routes = new ArrayList<>(routes);
    }

    public String getId() {
        return id;
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
