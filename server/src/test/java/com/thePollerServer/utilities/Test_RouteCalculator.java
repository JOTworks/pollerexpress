package com.thePollerServer.utilities;

import com.shared.models.City;
import com.shared.models.Point;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.DestinationCard;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class Test_RouteCalculator {

    List<Route> routes;
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

    public Test_RouteCalculator() {

        routes = new ArrayList<>();
        routes.add(route1);
        routes.add(route2);
        routes.add(route3);
        routes.add(route4);
        routes.add(route5);
        routes.add(route6);
    }

    @Test
    public void findConnectedRoutes_canFindRoutes() {
        RouteCalculator routeCalculator = new RouteCalculator(routes);
        assertTrue(routeCalculator.checkDestinationReached(new DestinationCard(Jacktown, Rodhamtown, 5)));
    }

    @Test
    public void findConnectedRoutes_canFindRouteForAdjacentCity() {
        RouteCalculator routeCalculator = new RouteCalculator(routes);
        assertTrue(routeCalculator.checkDestinationReached(new DestinationCard(Jacktown, Natetown, 5)));
    }

    @Test
    public void findConnectedRoutes_cannotFindNonConnectedRoutes() {
        RouteCalculator routeCalculator = new RouteCalculator(routes);
        assertFalse(routeCalculator.checkDestinationReached(new DestinationCard(Jacktown, Santatown, 5)));
    }
}
