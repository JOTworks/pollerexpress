package com.shared.models;

import java.util.UUID;

public class DestinationCard {
    private String _id;
    private City _city1;
    private City _city2;
    private int _points;

    public DestinationCard(City city1, City city2, int points) {
        this._id = UUID.randomUUID().toString();
        this._city1 = city1;
        this._city2 = city2;
        this._points = points;
    }

    public DestinationCard(String id, City city1, City city2, int points) {
        this._id = id;
        this._city1 = city1;
        this._city2 = city2;
        this._points = points;
    }

    public String getId() {
        return _id;
    }
}
