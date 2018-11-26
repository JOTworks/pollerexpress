package com.shared.models.cardsHandsDecks;

import com.shared.models.Color;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static com.shared.models.Color.TRAIN.*;

public class TrainCard implements Serializable, Comparable
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

    public String getColorAsString() {

        return String.valueOf(_color);
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 0;
        TrainCard card = (TrainCard) o;
        //PURPLE, WHITE, BLUE, YELLOW, ORANGE, BLACK, RED, GREEN, RAINBOW
        if(this.getColor() == PURPLE)
            return -1;
        if(card.getColor() == PURPLE)
            return 1;
        if(this.getColor() == WHITE)
            return -1;
        if(card.getColor() == WHITE)
            return 1;
        if(this.getColor() == BLUE)
            return -1;
        if(card.getColor() == BLUE)
            return 1;
        if(this.getColor() == YELLOW)
            return -1;
        if(card.getColor() == YELLOW)
            return 1;
        if(this.getColor() == ORANGE)
            return -1;
        if(card.getColor() == ORANGE)
            return 1;
        if(this.getColor() == BLACK)
            return -1;
        if(card.getColor() == BLACK)
            return 1;
        if(this.getColor() == RED)
            return -1;
        if(card.getColor() == RED)
            return 1;
        if(this.getColor() == GREEN)
            return -1;
        if(card.getColor() == GREEN)
            return 1;
        if(this.getColor() == RAINBOW)
            return -1;
        if(card.getColor() == RAINBOW)
            return 1;

        return 0;
    }
}
