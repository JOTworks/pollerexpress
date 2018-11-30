package com.shared.models.cardsHandsDecks;

import com.shared.models.City;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DestinationCard implements Serializable
{
    private String _id;
    private City _city1;
    private City _city2;
    private int _points;
    private boolean discardable;

    public DestinationCard(City city1, City city2, int points)
    {
        this(UUID.randomUUID().toString(), city1, city2, points);
        this.discardable = false;
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
    public String print() {return _city1.getName()+"\nto "+_points+"\n"+_city2;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DestinationCard card = (DestinationCard) o;
        return Objects.equals(_id, card._id);
    }

    @Override
    public int hashCode()
    {
        return _id.hashCode();
    }

    @Override
    public String toString() {
        return _city1.getName() + " to " + _city2.getName() + ", " + _points;
    }
}
