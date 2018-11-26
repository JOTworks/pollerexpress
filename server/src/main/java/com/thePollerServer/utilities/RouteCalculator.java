package com.thePollerServer.utilities;

import com.shared.models.City;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.ArrayList;
import java.util.List;


public class RouteCalculator {

    private List<Route> routes;

    public RouteCalculator(List<Route> routes) {
        this.routes = routes;
    }

    public boolean checkDestinationReached(DestinationCard card) {
        // if the two cities are not present in any of the routes claimed by a player, return false
        if (routesIncludeBothCities(card.getCity1(), card.getCity2())) {
             return findConnectedPath(card.getCity1(), card.getCity2());
        }
        else return false;
    }

    /**
     * A simple maze algorithm with the added bonus of not knowing how many directions there are
     * @param currentCity
     * @param destination
     * @return
     */
    private boolean findConnectedPath(City currentCity, City destination) {
        // base case
        if (currentCity == destination)
            return true;

        // find all connected cities and a routes that takes you there
        List<CityRoutePair> nextCitiesAndTheRoutesToGetThere = getConnectedCitiesAndRoutes(currentCity);
        // for each of these routeCityPairs...
        for (CityRoutePair cityRoutePair : nextCitiesAndTheRoutesToGetThere) {
            City nextCity = cityRoutePair.city;
            Route routeToNextCity = cityRoutePair.route;

            // step 1: remove the route that gets you to the next city so you do not backtrack
            routes.remove(routeToNextCity);
            // step 3: recurse for the win!
            if (findConnectedPath(nextCity, destination))
                return true;
        }
        // if we loop all the way through the connected routes and we never reach our destination
        // then we return false
        return false;
    }

    /**
     *
     * @param city
     * @return null if no cities are found, the connected city otherwise
     */
    private List<CityRoutePair> getConnectedCitiesAndRoutes(City city) {
        List<CityRoutePair> connectedCitiesAndRoutes = new ArrayList<>();

        for (Route route : routes) {
            if (route.getCities().get(0) == city)
                connectedCitiesAndRoutes.add(new CityRoutePair(route.getCities().get(1), route));
            if (route.getCities().get(1) == city)
                connectedCitiesAndRoutes.add(new CityRoutePair(route.getCities().get(0), route));
        }
        return connectedCitiesAndRoutes;
    }

    private boolean routeIncludesCity(City city) {
        boolean hasCity = false;

        for (Route route : routes) {
            if (route.getCities().get(0) == city ||
                    route.getCities().get(1) == city)
                hasCity = true;
        }
        return hasCity;
    }

    private boolean routesIncludeBothCities(City city1, City city2) {
        return routeIncludesCity(city1) && routeIncludesCity(city2);
    }

    /**
     * The routes need to be reset every time the calculator is used since traversal removes routes
     * @param routes
     */
    public void resetRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public int getLongestRouteLength(List<Route> routes) {
        //TODO: Calculate longest route here
        return 0;
    }

    private class CityRoutePair {
        public City city;
        public Route route;

        public CityRoutePair(City city, Route route) {
            this.city = city;
            this.route = route;
        }
    }
}
