package com.shared.models;

import java.util.List;

public class Map
{
    List<Route> routes;
    List<City> cities;

    public Map(List<Route> routes, List<City> cities)
    {
        this.routes = routes;
        this.cities = cities;
    }

    /**
     *
     * @param source
     * @param Destination
     * @return
     */
    public int getShortestDistanceBetweenCities(City source, City Destination)
    {
        //implement dijkstras for this...
        return -1;//can't reach.
    }

    /**
     * For AI
     * @param source
     * @param Destination
     * @return
     */
    public boolean playerCanReachCity(City source, City Destination, Player player)
    {
        //do stuff implement a depth first search...
        return true;
    }
}
