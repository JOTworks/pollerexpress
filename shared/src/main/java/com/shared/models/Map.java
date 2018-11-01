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
        City phoenix= new City("Phoenix", new Point(1200, 400));
        City moscow = new City("Moscow", new Point(1350, 1350));
        City potlatch = new City("Potlatch", new Point(1550, 1500));
        new Route(boston, houston, 4);
        new Route(boston, phoenix, 5, 30, Color.TRAIN.BLACK );
        new Route(boston, phoenix, 5, -30, Color.TRAIN.GREEN);
        new Route(moscow, houston, 5, 0 , Color.TRAIN.PURPLE);
        new Route(phoenix, moscow, 5, 0 , Color.TRAIN.YELLOW);
        new Route(potlatch, moscow, 2, 0, Color.TRAIN.RED);
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
     *
     * @param cities
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
     *
     * @param city
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

    public Collection<Route> getRoutes()
    {
        return routes.values();
    }
    public void claimRoute(Player p, Route route)
    {
        System.out.print("Entering claim route");
        if( routes.containsKey( route ) )
        {
            System.out.print(" Claimed a route");
            Route real = routes.get(route);
            real.setOwner(p);
        }
        System.out.print(" leaving \n");
    }

}
