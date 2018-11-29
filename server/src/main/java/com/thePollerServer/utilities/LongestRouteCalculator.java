package com.thePollerServer.utilities;

import com.shared.models.City;
import com.shared.models.Player;
import com.shared.models.Route;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LongestRouteCalculator
{
    private List<City> cities;
    public LongestRouteCalculator(List<City> cities)
    {
        this.cities = new LinkedList<>(cities);
    }

    private int getLongestRoute(Route myRoute, City city,  Set<Route> used, Player p)
    {
        int result = myRoute.getDistance();
        int max = 0;
        for(Route r: city.getRoutes())
        {
            if(p.equals(r.getOwner()) && !used.contains(r))
            {
                used.add(r);
                max = Math.max(max, getLongestRoute(r, r.getDestination(city), used, p));
                used.remove(r);
            }
        }
        return result+max;
    }
    public int getLongestRoute(Player p)
    {

        int max = 0;
        for(City city: cities)
        {
            Set<Route> used = new HashSet<>();
            for(Route r: city.getRoutes())
            {
                if(p.equals(r.getOwner()))
                {
                    used.add(r);
                    max = Math.max(max, getLongestRoute(r, r.getDestination(city), used, p));
                    used.remove(r);
                }
            }
        }
        return max;
    }
}
