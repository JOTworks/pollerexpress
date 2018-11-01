package com.shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class VisibleCards extends Observable implements Serializable
{
    private TrainCard cards[];
    public VisibleCards()
    {
        cards = new TrainCard[5];
    }

    public void set(TrainCard cards[])
    {
        for(int i = 0; i < cards.length; ++i)
        {
            this.cards[i] = cards[i];
        }
        synchronized (this)
        {
            this.setChanged();
            this.notifyObservers(cards);
        }
    }

    public TrainCard get(int i)
    {
        return cards[i];
    }
}
