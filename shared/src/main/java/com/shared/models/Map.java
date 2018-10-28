package com.shared.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map
{
    public static Map DEFAULT_MAP;
    //*****************************************************************************************************************8
    //
    //
    //
    //*******************************************************************************************************************

    static
    {
        DEFAULT_MAP = new Map();

        City boston = new City("Boston" , new Point(200,300) );

        City houston = new City("Houston", new Point(500, 1200));
        new Route(boston, houston, 4);
        DEFAULT_MAP.add(boston);
        DEFAULT_MAP.add(houston);


    }









    //End of default map creator

    HashMap<String, City> cities;
    Set<Route> routes;
    public Map()
    {
        cities = new HashMap<>();
        routes = new HashSet<>();
    }

    /**
     *
     * @param cities
     * @param routes
     */
    public Map(List<City> cities, Set<Route> routes)
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
     * @param city
     */
    public void add(City city)
    {
        cities.put(city.name, city);
        for(Route route : city.routes)
        {
            routes.add(route);
        }
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
        return true;
    }

    /**
     *
     * @param cityName
     * @return
     */
    public City getCityByName(String cityName)
    {
        return cities.get(cityName);
    }

    public Collection<City> getCities()
    {
        return cities.values();
    }

    public Set<Route> getRoutes()
    {
        return routes;
    }

}
