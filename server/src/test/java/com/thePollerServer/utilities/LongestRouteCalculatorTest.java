package com.thePollerServer.utilities;

import com.shared.models.City;
import com.shared.models.Player;
import com.shared.models.Point;
import com.shared.models.Route;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LongestRouteCalculatorTest
{

    List<City> cities;
    City Jacktown = new City("Jacktown", new Point(1,1));
    City Natetown = new City("Natetown", new Point(1,1));
    City Morgantown = new City("Morgantown", new Point(1,1));
    City Torstentown = new City("Torstentown", new Point(1,1));
    City Abbytown = new City("Abbytown", new Point(1,1));
    City Rodhamtown = new City("Rodhamtown", new Point(1,1));
    City Santatown = new City("Santatown", new Point(1,1));
    City Elftown = new City("Elftown", new Point(1,1));


    Route route1 = new Route(Jacktown, Natetown, 3);
    Route route2 = new Route(Natetown, Morgantown, 3);
    Route route3 = new Route(Morgantown, Torstentown, 3);
    Route route4 = new Route(Morgantown, Abbytown, 3);
    Route route5 = new Route(Abbytown, Rodhamtown, 3);
    Route route6 = new Route(Santatown, Elftown, 3);
    Route route7 = new Route(Jacktown, Elftown, 2);
    Player p = new Player("torsten");
    public LongestRouteCalculatorTest() {

        cities = new ArrayList<>();
        route1.setOwner(p);
        route2.setOwner(p);
        route3.setOwner(p);
        route4.setOwner(p);
        route5.setOwner(p);
        route7.setOwner(p);
        cities.add(Jacktown);
        cities.add(Natetown);
        cities.add(Morgantown);
        cities.add(Torstentown);
        cities.add(Santatown);
        cities.add(Abbytown);
        cities.add(Rodhamtown);
        cities.add(Elftown);

    }
    @Test
    public void getLongestRoute()
    {
        LongestRouteCalculator calc = new LongestRouteCalculator(cities);
        assertEquals(14, calc.getLongestRoute(p));

    }
}