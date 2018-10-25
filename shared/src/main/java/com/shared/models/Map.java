package com.shared.models;

import java.util.HashMap;
import java.util.List;

public class Map
{
    HashMap<String, City> cities;

    public Map(List<City> cities)
    {
        this.cities = new HashMap<>();
        for(City city: cities)
        {
            this.cities.put(city.getName(), city);
        }
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

    public City getCityByName(String cityName)
    {
        return null;//TODO implement this later at some point.
    }

}
