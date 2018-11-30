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
        DEFAULT_MAP = new Map();
        City[] Cities = new City[25];


        int Santas = 0;
        int Atlantis = 1;
        int Utquigvik = 2;
        int Deadhorse = 3;
        int Alpine = 4;
        int Anchorage = 5;
        int Alert = 6;
        int Isachsen = 7;
        int Ulukhaktok = 8;
        int Bronlundhus = 9;
        int Nord = 10;
        int Reykjavik = 11;
        int Bergen = 12;
        int Hammerfest = 13;
        int Anadrysky = 14;
        int Surgut = 15;
        int Omsk = 16;
        int Novosibirsk = 17;
        int Weather = 18;
        int Elsas = 19;
        int Tokyo = 20;
        int Yetifurt = 21;
        int Rudolphford = 22;
        int Dasherbury = 23;
        int Blitzenberg = 24;

        int i = 0;
        Cities[Santas]      = new City("Santa's Workshop", new Point(1490,1370));//0
        Cities[Atlantis]    = new City("Atlantis", new Point(1780,1983));//1
        Cities[Utquigvik]   = new City("Utquigvik", new Point(977,672));//2
        Cities[Deadhorse]   = new City("Deadhorse", new Point(841,848));//3
        Cities[Alpine]      = new City("Alpine", new Point(484,648));//4
        Cities[Anchorage]   = new City("Anchorage", new Point(767,364));//5
        Cities[Alert]       = new City("Alert", new Point(838,1272));//6
        Cities[Isachsen]    = new City("Isachsen", new Point(572,981));//7
        Cities[Ulukhaktok]  = new City("Ulukhaktok", new Point(1277,367));//8
        Cities[Bronlundhus] = new City("Bronlundhus", new Point(628,2284));//9
        Cities[Nord]        = new City("Nord", new Point(1043,2134));//10
        Cities[Reykjavik]   = new City("Reykjavik", new Point(1185,2396));//11
        Cities[Bergen]      = new City("Bergen", new Point(1817,2209));//12
        Cities[Hammerfest]  = new City("Hammerfest", new Point(1704,2612));//13
        Cities[Anadrysky]   = new City("Anadrysky", new Point(2376,2153));//14
        Cities[Surgut]      = new City("Surgut", new Point(2446,1838));//15
        Cities[Omsk]        = new City("Omsk", new Point(2559,1062));//16
        Cities[Novosibirsk] = new City("Novosibirsk", new Point(2218,687));//17
        Cities[Weather]     = new City("Weather Station", new Point(1740,141));//18
        Cities[Elsas]       = new City("Elsa's Castle", new Point(2091,1600));//19
        Cities[Tokyo]       = new City("New Tokyo", new Point(1837,796));//20
        Cities[Yetifurt]    = new City("Yetifurt", new Point(453,1866));//21
        Cities[Rudolphford] = new City("Rudolphford", new Point(1077,1833));//22
        Cities[Dasherbury]  = new City("Dasherbury", new Point(218,1239));//23
        Cities[Blitzenberg] = new City("Blitzenberg", new Point(2043,1240));//24




        new Route(Cities[Santas],Cities[Atlantis], 3,-15, Color.TRAIN.RED); //Santa's workshop
        new Route(Cities[Santas],Cities[Atlantis], 3,15, Color.TRAIN.GREEN);
        new Route(Cities[Santas],Cities[Weather], 6,-35, Color.TRAIN.GREEN);
        new Route(Cities[Santas],Cities[Weather], 6,5, Color.TRAIN.RED);
        new Route(Cities[Santas],Cities[Alert], 4,-10, Color.TRAIN.RED);
        new Route(Cities[Santas],Cities[Alert], 4,10, Color.TRAIN.GREEN);

        new Route(Cities[Atlantis],Cities[Reykjavik], 4,0, Color.TRAIN.RAINBOW); // Atlantis
        new Route(Cities[Atlantis],Cities[Elsas], 3,10, Color.TRAIN.RAINBOW); // Atlantis
        new Route(Cities[Atlantis],Cities[Rudolphford], 4,-10, Color.TRAIN.WHITE); // Atlantis

        new Route(Cities[Reykjavik],Cities[Bronlundhus], 3,0, Color.TRAIN.RAINBOW); //Reykjavik
        new Route(Cities[Reykjavik],Cities[Nord], 1,0, Color.TRAIN.YELLOW);
        new Route(Cities[Reykjavik],Cities[Bergen], 3,0, Color.TRAIN.BLACK);
        new Route(Cities[Reykjavik],Cities[Hammerfest], 3,0, Color.TRAIN.ORANGE);

        new Route(Cities[Nord],Cities[Bronlundhus], 2,0, Color.TRAIN.WHITE); //Nord
        new Route(Cities[Nord],Cities[Rudolphford], 2,0, Color.TRAIN.YELLOW);

        new Route(Cities[Yetifurt],Cities[Bronlundhus], 2,0, Color.TRAIN.PURPLE); //Yetifurt
        new Route(Cities[Yetifurt],Cities[Rudolphford], 3,0, Color.TRAIN.YELLOW);
        new Route(Cities[Yetifurt],Cities[Dasherbury], 4,0, Color.TRAIN.ORANGE);
        new Route(Cities[Yetifurt],Cities[Alert], 4,0, Color.TRAIN.BLACK);

        new Route(Cities[Rudolphford],Cities[Alert], 3,-15, Color.TRAIN.RAINBOW);

        new Route(Cities[Dasherbury],Cities[Alert], 2,0, Color.TRAIN.PURPLE); //Dasherbury
        new Route(Cities[Dasherbury],Cities[Alpine], 3,5, Color.TRAIN.BLUE);

        new Route(Cities[Alpine],Cities[Isachsen], 2,0, Color.TRAIN.YELLOW); //Alpine
        new Route(Cities[Alpine],Cities[Anchorage], 2,0, Color.TRAIN.RAINBOW);
        new Route(Cities[Alpine],Cities[Deadhorse], 2,0, Color.TRAIN.WHITE);
        new Route(Cities[Alpine],Cities[Utquigvik], 2,0, Color.TRAIN.ORANGE);

        new Route(Cities[Isachsen],Cities[Alert], 2,0, Color.TRAIN.BLACK); //Isachsen
        new Route(Cities[Isachsen],Cities[Deadhorse], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[Deadhorse],Cities[Utquigvik], 1,0, Color.TRAIN.BLUE);
        new Route(Cities[Deadhorse],Cities[Alert], 2,0, Color.TRAIN.YELLOW);

        new Route(Cities[Utquigvik],Cities[Ulukhaktok], 3,0, Color.TRAIN.PURPLE);

        new Route(Cities[Anchorage],Cities[Ulukhaktok], 3,0, Color.TRAIN.BLACK);


        new Route(Cities[Weather],Cities[Ulukhaktok], 3,0, Color.TRAIN.RAINBOW);
        new Route(Cities[Weather],Cities[Tokyo], 3,15, Color.TRAIN.WHITE);
        new Route(Cities[Weather],Cities[Novosibirsk], 3,60, Color.TRAIN.BLACK);
        new Route(Cities[Weather],Cities[Novosibirsk], 3,10, Color.TRAIN.PURPLE);
        new Route(Cities[Weather],Cities[Anchorage], 3,-35, Color.TRAIN.WHITE);

        new Route(Cities[Tokyo],Cities[Blitzenberg], 3,15, Color.TRAIN.YELLOW);
        new Route(Cities[Tokyo],Cities[Novosibirsk], 3,0, Color.TRAIN.YELLOW);

        new Route(Cities[Novosibirsk],Cities[Blitzenberg], 3,0, Color.TRAIN.BLUE);

        new Route(Cities[Novosibirsk],Cities[Omsk], 3,5, Color.TRAIN.ORANGE);

        new Route(Cities[Omsk],Cities[Surgut], 4,-10, Color.TRAIN.BLACK);
        new Route(Cities[Omsk],Cities[Elsas], 3,0, Color.TRAIN.RAINBOW);

        new Route(Cities[Elsas],Cities[Blitzenberg], 3,-5, Color.TRAIN.PURPLE);
        new Route(Cities[Elsas],Cities[Surgut], 3,0, Color.TRAIN.ORANGE);
        new Route(Cities[Elsas],Cities[Anadrysky], 3,0, Color.TRAIN.WHITE);

        new Route(Cities[Surgut],Cities[Anadrysky], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[Anadrysky],Cities[Hammerfest], 3,-10, Color.TRAIN.YELLOW);
        new Route(Cities[Anadrysky],Cities[Bergen], 3,10, Color.TRAIN.ORANGE);
        new Route(Cities[Anadrysky],Cities[Bergen], 3,-10, Color.TRAIN.BLACK);

        new Route(Cities[Bergen],Cities[Hammerfest], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[Bronlundhus],Cities[Hammerfest], 5,40, Color.TRAIN.BLUE);

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
            City newCity = new City(city);
            this.cities.put(city.getName(), newCity);
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
    public Route getRouteById(String routeId)
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


// other possible city names
//
//    Murmansk	Russia
//    Boreas
//    Home of Polaris
//    An Ice Place
//    Cupidforth
//    Donnerbrough
//    Cometville
//            Prancerstadt
//    Dancerkirk
//            Elfland