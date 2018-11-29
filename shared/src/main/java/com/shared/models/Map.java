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

//    Alert	Canada
//    Bronlundhus	Greenland
//    Nord	Greenland
//    Anadyrskol	Russia?
//     Novosibirsk siberia
//    Utqiagvik	Alaska
//    New Tokyo
//    Atlantis
//    Santa's Workshop
//    Elsa's Castle
//    Surgut	Russia
//    Anchorage
//    Bergen	Norway
//    Murmansk	Russia
//    Isachsen	Canada
//    Weather Station
//    Reykjavik	iceland
//    Ulukhaktok	Canada
//    Hammerfest	Norway
//    Alpine
//            Deadhorse
//    Omsk siberia
//    Boreas
//    Home of Polaris
//    An Ice Place
//        Cupidforth
//    Donnerbrough
//            Rudolphford
//    Cometville
//            Blitzenberg
//    Dasherbury
//            Prancerstadt
//    Dancerkirk
//            Elfland
//    Yetifurt


    static
    {

        DEFAULT_MAP = new Map();
        City[] Cities = new City[25];
        int i = 0;
        Cities[i++] =new City("Santa's Workshop", new Point(1490,1370));//0
        Cities[i++] =new City("Atlantis", new Point(1780,1983));//1
        Cities[i++] =new City("Utquigvik", new Point(977,672));//2
        Cities[i++] =new City("Deadhorse", new Point(841,848));//3
        Cities[i++] =new City("Alpine", new Point(484,648));//4
        Cities[i++] =new City("Anchorage", new Point(767,364));//5
        Cities[i++] =new City("Alert", new Point(838,1272));//6
        Cities[i++] =new City("Isachsen", new Point(572,981));//7
        Cities[i++] =new City("Ulukhaktok", new Point(1277,367));//8
        Cities[i++] =new City("Bronlundhus", new Point(628,2284));//9
        Cities[i++] =new City("Nord", new Point(1043,2134));//10
        Cities[i++] =new City("Reykjavik", new Point(1185,2396));//11
        Cities[i++] =new City("Bergen", new Point(1817,2209));//12
        Cities[i++] =new City("Hammerfest", new Point(1704,2612));//13
        Cities[i++] =new City("Anadrysky", new Point(2376,2153));//14
        Cities[i++] =new City("Surgut", new Point(2446,1838));//15
        Cities[i++] =new City("Omsk", new Point(2509,1062));//16
        Cities[i++] =new City("Novosibirsk", new Point(2218,787));//17
        Cities[i++] =new City("Weather Station", new Point(1740,141));//18
        Cities[i++] =new City("Elsa's Castle", new Point(2091,1600));//19
        Cities[i++] =new City("New Tokyo", new Point(1737,796));//20
        Cities[i++] =new City("Yetifurt", new Point(453,1866));//21
        Cities[i++] =new City("Rudolphford", new Point(1077,1833));//22
        Cities[i++] =new City("Dasherbury", new Point(218,1239));//23
        Cities[i++] =new City("Blitzenberg", new Point(2043,1240));//24
//        Cities[i++] =new City("Maimi", new Point(1800,1050));//25
//        Cities[i++] =new City("Saint Louis", new Point(1340,700));//26
//        Cities[i++] =new City("Nashville", new Point(230,800));//27
//        Cities[i++] =new City("Raleigh", new Point(400,450));//28
//        Cities[i++] =new City("Denver", new Point(600,230));//29
//        Cities[i++] =new City("Santa Fe", new Point(30,100));//30
//        Cities[i++] =new City("El Paso", new Point(450,1200));//31
//        Cities[i++] =new City("Chicago", new Point(450,440));//32
//        Cities[i++] =new City("Little Rock", new Point(30,550));//33
//        Cities[i++] =new City("Atlanta", new Point(1200,220));//34
//        Cities[i++] =new City("Salt Lake City", new Point(500,110));//35
//
        new Route(Cities[0],Cities[1], 3,-5, Color.TRAIN.GREEN); //Santa's workshop
        new Route(Cities[0],Cities[18], 6,5, Color.TRAIN.RED);

        new Route(Cities[1],Cities[11], 4,0, Color.TRAIN.RAINBOW); // Atlantis

        new Route(Cities[11],Cities[9], 3,0, Color.TRAIN.RAINBOW); //Reykjavik
        new Route(Cities[11],Cities[10], 1,0, Color.TRAIN.YELLOW);
        new Route(Cities[11],Cities[12], 3,0, Color.TRAIN.RED);
        new Route(Cities[11],Cities[13], 3,0, Color.TRAIN.ORANGE);

        new Route(Cities[10],Cities[9], 2,0, Color.TRAIN.RAINBOW); //Nord
        new Route(Cities[10],Cities[22], 2,0, Color.TRAIN.GREEN);

        new Route(Cities[12],Cities[13], 2,0, Color.TRAIN.YELLOW);
//
        for(City city: Cities)
        {
            DEFAULT_MAP.add(city);
        }
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
