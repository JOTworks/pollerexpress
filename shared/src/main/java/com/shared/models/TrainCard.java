package com.shared.models;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class TrainCard implements Serializable

{
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainCard card = (TrainCard) o;
        return Objects.equals(_id, card._id);
    }
}
