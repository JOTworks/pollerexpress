package com.shared.models;

import java.util.Objects;
import java.util.UUID;

public class DestinationCard {
    private String _id;
    private City _city1;
    private City _city2;
    private int _points;

    public DestinationCard(City city1, City city2, int points)
    {
        this(UUID.randomUUID().toString(), city1, city2, points);
    }

    public DestinationCard(String id, City city1, City city2, int points)
    {
        this._id = id;
        this._city1 = city1;
        this._city2 = city2;
        this._points = points;
    }

    public String getId()
    {
        return _id;
    }
    public City getCity1() { return _city1; }
    public City getCity2() { return _city2; }
    public int getPoints() { return _points; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinationCard card = (DestinationCard) o;
        return Objects.equals(_id, card._id);
    }
}
