package com.shared.models;

import java.util.UUID;

public class TrainCard {
    String _id;
    Color.TRAIN _color;

    public TrainCard(String id, Color.TRAIN color) {
        _id = id;
        _color = color;
    }

    public TrainCard(Color.TRAIN color) {
        this(UUID.randomUUID().toString(), color);
    }

    public String getId() { return _id; }

    public Color.TRAIN getColor() { return _color; }
}
