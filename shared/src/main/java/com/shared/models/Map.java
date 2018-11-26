package com.shared.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Map implements Serializable
{
    public static Map DEFAULT_MAP;
    //*****************************************************************************************************************
    //
    //
    //
    //*******************************************************************************************************************

    static
    {
        DEFAULT_MAP = new Map();

        City boston = new City("Boston" , new Point(200,300) );

        City houston = new City("Houston", new Point(500, 1200));
        City phoenix= new City("Phoenix", new Point(1200, 400));
        City moscow = new City("Moscow", new Point(1350, 1350));
        City potlatch = new City("Potlatch", new Point(1550, 1500));
        new Route(boston, houston, 4);
        new Route(boston, phoenix, 5, 30, Color.TRAIN.BLACK );
        new Route(boston, phoenix, 5, -30, Color.TRAIN.GREEN);
        new Route(moscow, houston, 5, 0 , Color.TRAIN.PURPLE);
        new Route(phoenix, moscow, 5, 0 , Color.TRAIN.YELLOW);
        new Route(potlatch, moscow, 1, 0, Color.TRAIN.RAINBOW);
        new Route( boston, potlatch, 13, 230, Color.TRAIN.BLUE );
        new Route(houston, phoenix, 5, -14 );
        DEFAULT_MAP.add(boston);
        DEFAULT_MAP.add(houston);
        DEFAULT_MAP.add(phoenix);
        DEFAULT_MAP.add(moscow);
        DEFAULT_MAP.add(potlatch);
    }

    //End of default map creator

    HashMap<String, City> cities;
    HashMap<Route, Route> routes;
    public Map()
    {
        cities = new HashMap<>();
        routes = new HashMap<>();
    }
    public Map(Map toCopy)
    {
        this();
        //TODO have this do a deep copy of everything...
        for(City city: toCopy.getCities())
        {
            this.cities.put(city.getName(), city);
        }
        for(Route route: toCopy.getRoutes())
        {
            Route copy = new Route(getCityByName(route.getCities().get(0).name ), getCityByName(route.getCities().get(1).getName()), route.getDistance() , route.getRotation() , route.getColor() );
            copy.setOwner(route.getOwner());
            routes.put(copy, copy);
        }
    }
    /**
     *Create a map from a list of cities, the cities must have their routes set up.
     * @param cities a list of cities
     */
    public Map(List<City> cities)
    {
        this.cities = new HashMap<>();
        for(City city: cities)
        {
            this.add( city);
        }
        //TODO add verification that this list of cities is a single interconnected graph.
        //easy enough to do.
    }

    /**
     *Add a city and all of its routes to the map.
     * @param city city to add to the map
     */
    public void add(City city)
    {
        cities.put(city.name, city);
        for(Route route : city.routes)
        {
            routes.put(route ,route);
        }
    }
    /**
     * use the visitor pattern in the following two methods.
     */
    /**
     * Finds the distance in the connected graph
     * @pre
     * @param source a city in the map
     * @param Destination another city in the map
     * @return the number of train cars needed
     */
    public int getShortestDistanceBetweenCities(City source, City Destination)
    {
        //TODO implement dijkstras for this...
        return -1;//can't reach.
    }

    /**
     * For AI mostly with destinations.
     * @pre both cities are in the map
     * @param source a city in the map
     * @param Destination another city in the map
     * @return true if the player can connect the cities, false otherwise.
     */
    public boolean playerCanReachCity(City source, City Destination, Player player)
    {
        return true;
    }

    /**
     * gets a city from the map
     * @param cityName
     * @return
     */
    public City getCityByName(String cityName)
    {
        return cities.get(cityName);
    }

    /**
     * Get the cities in the map
     * @return all the cities in the map.
     */
    public Collection<City> getCities()
    {
        return cities.values();
    }

    /**
     * it might be better to go through each city and collate the routes into a set....
     * @return all the routes in the map
     */
    public Collection<Route> getRoutes()
    {
        return routes.values();
    }
    public void claimRoute(Player p, Route route)
    {
        if( routes.containsKey( route ) )
        {
            Route real = routes.get(route);
            real.setOwner(p);
        }
    }

}
