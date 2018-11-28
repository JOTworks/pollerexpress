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
       default_map_init();
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
        class QO
        {
            City data;
            int priority;

            QO(City s1, int prior)
            {
                this.data = s1;
                this.priority = prior;
            }

        }
        class queueObjectComparitor implements Comparator<QO>
        {
            @Override
            public int compare(QO queueObject, QO t1)
            {
                return queueObject.priority - t1.priority;
            }
        }
        PriorityQueue<QO> queue = new PriorityQueue<>(new queueObjectComparitor());

        queue.add(new QO(source, 0));
        HashMap<City, City> prev= new HashMap<>();
        HashSet<City> seen = new HashSet<>();
        int best = Integer.MAX_VALUE;
        prev.put(source, null);
        while(!queue.isEmpty())
        {
            QO c = queue.poll();
            if(seen.contains(c.data))
            {
                continue;
            }
            seen.add(c.data);
            for( Route r: c.data.routes)
            {
                City other =r.getDestination(c.data);

            }
        }

        return true;
    }

    public boolean playerReachesCity(City source, City destination, Player player)
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



    private static void default_map_init()
    {
        DEFAULT_MAP = new Map();
        City[] Cities = new City[36];
        int i = 0;
        int k = 0;
        int Vancouver=0;
        int Seattle=1;
        int Portland=2;
        int San_Francisco=3;
        int Los_Angeles=4;
        int Las_Vegas=5;
        int Calgary=6;
        int Helena=7;
        int Phoenix=8;
        int Winnipeg=9;
        int Duluth=10;
        int Omaha=11;
        int Kansas_City=12;
        int Oklahoma_City=13;
        int Dallas=14;
        int Houston=15;
        int Sault_St_Marie=16;
        int Montreal=17;
        int Boston=18;
        int Toronto=19;
        int New_York=20;
        int Pittsburgh=21;
        int Washington=22;
        int New_Orleans=23;
        int Charleston=24;
        int Maimi=25;
        int Saint_Louis=26;
        int Nashville=27;
        int Raleigh=28;
        int Denver=29;
        int Santa_Fe=30;
        int El_Paso=31;
        int Chicago=32;
        int Little_Rock=33;
        int Atlanta=34;
        int Salt_Lake_City=35;

        Cities[i++] =new City("Vancouver", new Point(260,400));//0
        Cities[i++] =new City("Seattle", new Point(260,500));//1
        Cities[i++] =new City("Portland", new Point(240,600));//2
        Cities[i++] =new City("San Francisco", new Point(250,1200));//3
        Cities[i++] =new City("Los Angeles", new Point(300,2200));//4
        Cities[i++] =new City("Las Vegas", new Point(400,2000));//5
        Cities[i++] =new City("Calgary", new Point(700,300));//6
        Cities[i++] =new City("Helena", new Point(1000,600));//7
        Cities[i++] =new City("Phoenix", new Point(900,2200));//8
        Cities[i++] =new City("Winnipeg", new Point(1400,300));//9
        Cities[i++] =new City("Duluth", new Point(1500,600));//10
        Cities[i++] =new City("Omaha", new Point(1400,900));//11
        Cities[i++] =new City("Kansas City", new Point(1400,1100));//12
        Cities[i++] =new City("Oklahoma City", new Point(1600,1200));//13
        Cities[i++] =new City("Dallas", new Point(1600,1500));//14
        Cities[i++] =new City("Houston", new Point(1650,2300));//15
        Cities[i++] =new City("Sault St. Marie", new Point(2000,700));//16
        Cities[i++] =new City("Montreal", new Point(2400,300));//17
        Cities[i++] =new City("Boston", new Point(2400,500));//18
        Cities[i++] =new City("Toronto", new Point(2000,500));//19
        Cities[i++] =new City("New York", new Point(2100,700));//20
        Cities[i++] =new City("Pittsburgh", new Point(700,673));//21
        Cities[i++] =new City("Washington", new Point(2000,1000));//22
        Cities[i++] =new City("New Orleans", new Point(1600,1253));//23
        Cities[i++] =new City("Charleston", new Point(1900,1000));//24
        Cities[i++] =new City("Maimi", new Point(1800,1050));//25
        Cities[i++] =new City("Saint Louis", new Point(1340,700));//26
        Cities[i++] =new City("Nashville", new Point(230,800));//27
        Cities[i++] =new City("Raleigh", new Point(400,450));//28
        Cities[i++] =new City("Denver", new Point(600,230));//29
        Cities[i++] =new City("Santa Fe", new Point(30,100));//30
        Cities[i++] =new City("El Paso", new Point(450,1200));//31
        Cities[i++] =new City("Chicago", new Point(450,440));//32
        Cities[i++] =new City("Little Rock", new Point(30,550));//33
        Cities[i++] =new City("Atlanta", new Point(1200,220));//34
        Cities[i++] =new City("Salt Lake City", new Point(500,110));//35

        new Route(Cities[0],Cities[1], 1,-5, Color.TRAIN.RAINBOW);
        new Route(Cities[0],Cities[1], 1,5, Color.TRAIN.RAINBOW);//Vancouver
        new Route(Cities[0],Cities[6], 3,0, Color.TRAIN.RAINBOW);

        new Route(Cities[1],Cities[6], 4,0, Color.TRAIN.RAINBOW);
        new Route(Cities[1],Cities[2], 1,-5, Color.TRAIN.RAINBOW);//Seattle
        new Route(Cities[1],Cities[2], 1,5, Color.TRAIN.RAINBOW);
        new Route(Cities[1],Cities[7], 6,0, Color.TRAIN.YELLOW);

        new Route(Cities[2],Cities[3], 5,3, Color.TRAIN.GREEN);//Portland
        new Route(Cities[2],Cities[3], 5,0, Color.TRAIN.PURPLE);
        new Route(Cities[2],Cities[35], 6,1, Color.TRAIN.BLUE);

        new Route(Cities[3],Cities[4], 3,1, Color.TRAIN.YELLOW);//San Francisco
        new Route(Cities[3],Cities[4], 3,4, Color.TRAIN.PURPLE);
        new Route(Cities[3],Cities[35], 5,5, Color.TRAIN.ORANGE);
        new Route(Cities[3],Cities[35], 5,-5, Color.TRAIN.WHITE);

        new Route(Cities[4],Cities[5], 2,1, Color.TRAIN.RAINBOW);//Los Angeles
        new Route(Cities[4],Cities[8], 3,1, Color.TRAIN.RAINBOW);
        new Route(Cities[4],Cities[31], 6,-2, Color.TRAIN.BLACK);

        new Route(Cities[5],Cities[35], 3,-2, Color.TRAIN.ORANGE);//Las Vegas

        new Route(Cities[6],Cities[7], 4,1, Color.TRAIN.RAINBOW);//calgary
        new Route(Cities[6],Cities[9], 6,1, Color.TRAIN.WHITE);

        new Route(Cities[35],Cities[7], 3,1, Color.TRAIN.PURPLE); //SLC
        new Route(Cities[35],Cities[29], 3,5, Color.TRAIN.RED);
        new Route(Cities[35],Cities[29], 3,-5, Color.TRAIN.YELLOW);

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
        new Route(Cities[10],Cities[11], 2,-5, Color.TRAIN.RAINBOW);
        new Route(Cities[10],Cities[11], 2,5, Color.TRAIN.RAINBOW);
        new Route(Cities[10],Cities[32], 3,-1, Color.TRAIN.RED);
        new Route(Cities[10],Cities[19], 6,-1, Color.TRAIN.PURPLE);

        new Route(Cities[11],Cities[29], 4,1, Color.TRAIN.PURPLE);//OMH
        new Route(Cities[11],Cities[32], 4,2, Color.TRAIN.BLUE);
        new Route(Cities[11],Cities[12], 1,-5, Color.TRAIN.RAINBOW);
        new Route(Cities[11],Cities[12], 1,5, Color.TRAIN.RAINBOW);

        new Route(Cities[12],Cities[29], 4,-1, Color.TRAIN.BLACK);//kans
        new Route(Cities[12],Cities[29], 4,1, Color.TRAIN.ORANGE);
        new Route(Cities[12],Cities[26], 2,-5, Color.TRAIN.BLUE);
        new Route(Cities[12],Cities[26], 2,5, Color.TRAIN.PURPLE);
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

        new Route(Cities[23],Cities[33], 3,0, Color.TRAIN.GREEN);//new Orlean
        new Route(Cities[23],Cities[25], 6,0, Color.TRAIN.RED);
        new Route(Cities[23],Cities[34], 4,-5, Color.TRAIN.YELLOW);
        new Route(Cities[23],Cities[34], 4,5, Color.TRAIN.ORANGE);

        new Route(Cities[Sault_St_Marie],Cities[Toronto], 2,0, Color.TRAIN.RAINBOW);
        new Route(Cities[Sault_St_Marie],Cities[Montreal], 5,5, Color.TRAIN.BLACK);

        new Route(Cities[Toronto],Cities[Montreal], 3,2, Color.TRAIN.RAINBOW);
        new Route(Cities[Toronto],Cities[Pittsburgh], 2,0, Color.TRAIN.RAINBOW);

        new Route(Cities[Montreal],Cities[Boston], 2,5, Color.TRAIN.RAINBOW);
        new Route(Cities[Montreal],Cities[Boston], 2,-5, Color.TRAIN.RAINBOW);
        new Route(Cities[Montreal],Cities[New_York], 3,5, Color.TRAIN.BLUE);

        new Route(Cities[Boston], Cities[New_York], 2, 5, Color.TRAIN.YELLOW);
        new Route(Cities[Boston], Cities[New_York], 2, -5, Color.TRAIN.RED);

        new Route(Cities[New_York], Cities[Pittsburgh], 2, 5, Color.TRAIN.WHITE);
        new Route(Cities[New_York], Cities[Pittsburgh], 2, -5, Color.TRAIN.GREEN);
        new Route(Cities[New_York], Cities[Washington], 2, 5, Color.TRAIN.ORANGE);
        new Route(Cities[New_York], Cities[Washington], 2, -5, Color.TRAIN.RAINBOW);

        new Route(Cities[Chicago], Cities[Pittsburgh], 3, -5, Color.TRAIN.ORANGE);
        new Route(Cities[Chicago], Cities[Pittsburgh], 3, 5, Color.TRAIN.BLACK);
        new Route(Cities[Chicago], Cities[Saint_Louis], 2, -5, Color.TRAIN.GREEN);
        new Route(Cities[Chicago], Cities[Saint_Louis], 2, 5, Color.TRAIN.WHITE);

        new Route(Cities[Saint_Louis], Cities[Pittsburgh], 5, 0, Color.TRAIN.GREEN);
        new Route(Cities[Saint_Louis], Cities[Nashville], 2, 0, Color.TRAIN.RAINBOW);
        new Route(Cities[Saint_Louis], Cities[Little_Rock], 2, 0, Color.TRAIN.RAINBOW);

        new Route(Cities[Little_Rock], Cities[Nashville], 3, 0, Color.TRAIN.WHITE);

        new Route(Cities[Nashville], Cities[Atlanta], 1, 0, Color.TRAIN.RAINBOW);
        new Route(Cities[Nashville], Cities[Pittsburgh], 4, 0, Color.TRAIN.YELLOW);
        new Route(Cities[Nashville], Cities[Pittsburgh], 3, 0, Color.TRAIN.BLACK);

        new Route(Cities[Pittsburgh], Cities[Washington], 2, 0, Color.TRAIN.RAINBOW);
        new Route(Cities[Pittsburgh], Cities[Raleigh], 2, 0, Color.TRAIN.RAINBOW);
        new Route(Cities[Washington], Cities[Raleigh], 2, -5, Color.TRAIN.RAINBOW);
        new Route(Cities[Washington], Cities[Raleigh], 2, 5, Color.TRAIN.RAINBOW);

        new Route(Cities[Raleigh], Cities[Charleston], 2, 5, Color.TRAIN.RAINBOW);
        new Route(Cities[Raleigh], Cities[Atlanta], 2, 5, Color.TRAIN.RAINBOW);
        new Route(Cities[Raleigh], Cities[Atlanta], 2, -5, Color.TRAIN.RAINBOW);
        new Route(Cities[Atlanta], Cities[Charleston], 2, 0, Color.TRAIN.RAINBOW);
        new Route(Cities[Atlanta], Cities[Maimi], 5, 0, Color.TRAIN.BLUE);

        new Route(Cities[Charleston], Cities[Maimi], 4, 0, Color.TRAIN.PURPLE);



        for(City city: Cities)
        {
            DEFAULT_MAP.add(city);
        }
    }
}
