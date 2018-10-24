package com.shared.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Route
{
    List<City> cities;
    int distance;
    Player owner;

    public Route(City dest, City target, int distance)
    {
        cities = new ArrayList<>(2);
        cities.set(0, dest);
        cities.set(1, target);
        this.distance = distance;
    }


    /**
     * Ideally this would return an unmodificable list.
     * @return a list
     */
    public List<City> getCities()
    {
        return cities;
    }

    public void setOwner(Player player)
    {
        this.owner = player;
    }
    public Player getOwner()
    {
        return owner;
    }
}
