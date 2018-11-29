package com.shared.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
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

    }

    public static Map getDefaultMap(){ return DEFAULT_MAP; }

    //End of default map creator

    HashMap<String, City> cities;
    HashMap<String, Route> routes;
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
            routes.put(copy.toString(), copy);
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
            routes.put(route.toString() ,route);
        }
    }
    /**
     * use the visitor pattern in the following two methods.
     */
    /**
     * Finds the distance in the connected graph
     * @pre
     * @param source a city in the map
     * @param destination another city in the map
     * @return the number of train cars needed
     */
    public int getShortestDistanceBetweenCities(String source, String destination)
    {
        class Visited
        {
            int distance;
            City city;
            public Visited(City city, int distance)
            {
                this.distance= distance;
                this.city = city;
            }

        }
        class Comp implements Comparator<Visited>
        {
            @Override
            public int compare(Visited x, Visited y)
            {
                return Integer.compare(x.distance, y.distance);
            }
        }

        //HashMap<City, City> prev = new HashMap<>();
        Set<City> processed = new HashSet<>();
        City start = getCityByName(source);
        City end = getCityByName(destination);

        PriorityQueue<Visited> queue = new PriorityQueue<>(new Comp());
        queue.add(new Visited(start, 0));

        while(!queue.isEmpty())
        {
            Visited cur = queue.poll();

            if(processed.contains(cur.city))
            {
                continue;
            }

            processed.add(cur.city);
            for(Route r: cur.city.routes)
            {
                City other=r.getDestination(cur.city);
                if(processed.contains(other))
                {
                    continue;
                }
                int distance =cur.distance + r.getDistance();
                if(other.equals(end))
                {
                    return distance;
                }
                else
                {
                    queue.add(new Visited(other, distance));
                }

            }
        }
        return -1;//can't reach.
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
     *
     * @param routeId
     * @return
     */
    public Route getRouteByName(String routeId)
    {
        return routes.get(routeId);
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
        claimRoute(p, route.toString());
    }
    public void claimRoute(Player p, String routeId)
    {
        if( routes.containsKey( routeId ) )
        {
            Route real = routes.get(routeId);
            real.setOwner(p);
        }
    }
}
