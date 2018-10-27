package com.shared.models;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Map
{
    HashMap<String, City> cities;
    List<Route> routes;
    public Map(List<City> cities, List<Route> routes)
    {
        this.cities = new HashMap<>();
        for(City city: cities)
        {
            this.cities.put(city.getName(), city);
        }

        this.routes = routes;
        //TODO add verification that this list of cities is a single interconnected graph.
        //easy enough to do.
    }

    /**
     *
     * @param source
     * @param Destination
     * @return
     */
    public int getShortestDistanceBetweenCities(City source, City Destination)
    {
        //TODO implement dijkstras for this...
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
        //TODO stuff implement a depth first search...
        return true;
    }

    public City getCityByName(String cityName)
    {
        return cities.get(cityName);//TODO implement this later at some point.
    }

    public Collection<City> getCities()
    {
        return cities.values();
    }

    public List<Route> getRoutes()
    {
        return routes;
    }

}
