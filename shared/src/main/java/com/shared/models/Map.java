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
        City[] Cities = new City[36];
        int i = 0;
        Cities[i++] =new City("Vancouver", new Point(260,430));//0
        Cities[i++] =new City("Seattle", new Point(260,470));//1
        Cities[i++] =new City("Portland", new Point(240,500));//2
        Cities[i++] =new City("San Francisco", new Point(250,900));//3
        Cities[i++] =new City("Los Angeles", new Point(300,1200));//4
        Cities[i++] =new City("Las Vegas", new Point(230,1200));//5
        Cities[i++] =new City("Calgary", new Point(300,500));//6
        Cities[i++] =new City("Helena", new Point(200,400));//7
        Cities[i++] =new City("Phoenix", new Point(300,1200));//8
        Cities[i++] =new City("Winnipeg", new Point(0,0));//9
        Cities[i++] =new City("Duluth", new Point(0,0));//10
        Cities[i++] =new City("Omaha", new Point(0,0));//11
        Cities[i++] =new City("Kansas City", new Point(0,0));//12
        Cities[i++] =new City("Oklahoma City", new Point(0,0));//13
        Cities[i++] =new City("Dallas", new Point(0,0));//14
        Cities[i++] =new City("Houston", new Point(0,0));//15
        Cities[i++] =new City("Sault St. Marie", new Point(0,0));//16
        Cities[i++] =new City("Montreal", new Point(0,0));//17
        Cities[i++] =new City("Boston", new Point(0,0));//18
        Cities[i++] =new City("Toronto", new Point(0,0));//19
        Cities[i++] =new City("New York", new Point(0,0));//20
        Cities[i++] =new City("Pittsburgh", new Point(0,0));//21
        Cities[i++] =new City("Washington", new Point(0,0));//22
        Cities[i++] =new City("New Orleans", new Point(0,0));//23
        Cities[i++] =new City("Charleston", new Point(0,0));//24
        Cities[i++] =new City("Maimi", new Point(0,0));//25
        Cities[i++] =new City("Saint Louis", new Point(0,0));//26
        Cities[i++] =new City("Nashville", new Point(0,0));//27
        Cities[i++] =new City("Raleigh", new Point(0,0));//28
        Cities[i++] =new City("Denver", new Point(0,0));//29
        Cities[i++] =new City("Santa Fe", new Point(0,0));//30
        Cities[i++] =new City("El Paso", new Point(0,0));//31
        Cities[i++] =new City("Chicago", new Point(0,0));//32
        Cities[i++] =new City("Little Rock", new Point(0,0));//33
        Cities[i++] =new City("Atlanta", new Point(0,0));//34
        Cities[i++] =new City("Salt Lake City", new Point(0,0));//35

        new Route(Cities[0],Cities[1], 1,-1, Color.TRAIN.RAINBOW);
        new Route(Cities[0],Cities[1], 1,1, Color.TRAIN.RAINBOW);//Vancouver
        new Route(Cities[0],Cities[6], 3,0, Color.TRAIN.RAINBOW);

        new Route(Cities[1],Cities[6], 4,0, Color.TRAIN.RAINBOW);
        new Route(Cities[1],Cities[2], 1,-1, Color.TRAIN.RAINBOW);//Seattle
        new Route(Cities[1],Cities[2], 1,1, Color.TRAIN.RAINBOW);
        new Route(Cities[1],Cities[7], 6,0, Color.TRAIN.YELLOW);

        new Route(Cities[2],Cities[3], 5,3, Color.TRAIN.GREEN);//Portland
        new Route(Cities[2],Cities[3], 5,1, Color.TRAIN.PURPLE);
        new Route(Cities[2],Cities[35], 6,1, Color.TRAIN.BLUE);

        new Route(Cities[3],Cities[4], 3,1, Color.TRAIN.YELLOW);//San Francisco
        new Route(Cities[3],Cities[4], 3,4, Color.TRAIN.PURPLE);
        new Route(Cities[3],Cities[35], 5,2, Color.TRAIN.ORANGE);
        new Route(Cities[3],Cities[35], 5,-2, Color.TRAIN.WHITE);

        new Route(Cities[4],Cities[5], 2,1, Color.TRAIN.RAINBOW);//Los Angeles
        new Route(Cities[4],Cities[8], 3,1, Color.TRAIN.RAINBOW);
        new Route(Cities[4],Cities[31], 6,-2, Color.TRAIN.BLACK);

        new Route(Cities[5],Cities[35], 3,-2, Color.TRAIN.ORANGE);//Las Vegas

        new Route(Cities[6],Cities[7], 4,1, Color.TRAIN.RAINBOW);//calgary
        new Route(Cities[6],Cities[9], 6,1, Color.TRAIN.WHITE);

        new Route(Cities[35],Cities[7], 3,1, Color.TRAIN.PURPLE); //SLC
        new Route(Cities[35],Cities[29], 3,1, Color.TRAIN.RED);
        new Route(Cities[35],Cities[29], 3,-1, Color.TRAIN.YELLOW);

        new Route(Cities[7],Cities[9], 4,1, Color.TRAIN.BLUE);//HEL
        new Route(Cities[7],Cities[29], 4,0, Color.TRAIN.GREEN);
        new Route(Cities[7],Cities[10], 6,0, Color.TRAIN.ORANGE);
        new Route(Cities[7],Cities[11], 5,0, Color.TRAIN.RED);

        new Route(Cities[8],Cities[29], 5,1, Color.TRAIN.WHITE);//phx
        new Route(Cities[8],Cities[30], 3,0, Color.TRAIN.RAINBOW);
        new Route(Cities[8],Cities[31], 2,0, Color.TRAIN.RAINBOW);
        new Route(Cities[8],Cities[13], 3,0, Color.TRAIN.BLUE);

        new Route(Cities[9],Cities[10], 4,0, Color.TRAIN.BLACK);//wpg
        new Route(Cities[8],Cities[16], 6,0, Color.TRAIN.RAINBOW);


        new Route(Cities[10],Cities[16], 3,0, Color.TRAIN.RAINBOW);//duluth
        new Route(Cities[10],Cities[11], 2,-1, Color.TRAIN.RAINBOW);
        new Route(Cities[10],Cities[11], 2,1, Color.TRAIN.RAINBOW);
        new Route(Cities[10],Cities[32], 3,-1, Color.TRAIN.RED);
        new Route(Cities[10],Cities[19], 6,-1, Color.TRAIN.PURPLE);

        new Route(Cities[11],Cities[29], 4,1, Color.TRAIN.PURPLE);//OMH
        new Route(Cities[11],Cities[32], 4,2, Color.TRAIN.BLUE);
        new Route(Cities[11],Cities[12], 1,-1, Color.TRAIN.RAINBOW);
        new Route(Cities[11],Cities[12], 1,1, Color.TRAIN.RAINBOW);

        new Route(Cities[12],Cities[29], 4,-1, Color.TRAIN.BLACK);//kans
        new Route(Cities[12],Cities[29], 4,1, Color.TRAIN.ORANGE);
        new Route(Cities[12],Cities[26], 2,-1, Color.TRAIN.BLUE);
        new Route(Cities[12],Cities[26], 2,1, Color.TRAIN.PURPLE);
        new Route(Cities[12],Cities[13], 2,-1, Color.TRAIN.RAINBOW);
        new Route(Cities[12],Cities[13], 2,1, Color.TRAIN.RAINBOW);

        new Route(Cities[29],Cities[13], 4,-1, Color.TRAIN.RED);//DENV
        new Route(Cities[29],Cities[30], 2,-1, Color.TRAIN.RAINBOW);

        new Route(Cities[30],Cities[13], 3,0, Color.TRAIN.BLUE);//SANTA
        new Route(Cities[30],Cities[31], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[31],Cities[13], 5,-2, Color.TRAIN.YELLOW);//el paso
        new Route(Cities[31],Cities[14], 4,0, Color.TRAIN.RED);
        new Route(Cities[31],Cities[15], 6,-2, Color.TRAIN.GREEN);

        new Route(Cities[13],Cities[14], 2,1, Color.TRAIN.RAINBOW);//okh
        new Route(Cities[13],Cities[14], 2,-1, Color.TRAIN.RAINBOW);
        new Route(Cities[13],Cities[33], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[14],Cities[15], 1,1, Color.TRAIN.RAINBOW);//DAL
        new Route(Cities[14],Cities[15], 1,-1, Color.TRAIN.RAINBOW);
        new Route(Cities[14],Cities[33], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[15],Cities[23], 2,0, Color.TRAIN.RAINBOW);//HOUS

        for(City city: Cities)
        {
            DEFAULT_MAP.add(city);
        }
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
