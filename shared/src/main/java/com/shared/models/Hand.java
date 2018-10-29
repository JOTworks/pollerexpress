package com.shared.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Hand extends Observable
{
    List<DestinationCard> destinationCards;
    List<TrainCard> trainCards;
    public Hand()
    {
        destinationCards = new ArrayList<>();
        trainCards = new ArrayList<>();
    }


    public void setDestinationCards(List<DestinationCard> destinationCards)
    {
        this.destinationCards = destinationCards;
        synchronized (this)
        {
            notifyObservers(destinationCards);
        }
    }

    public void setTrainCards(List<TrainCard> trainCards)
    {
        this.trainCards = trainCards;
        synchronized (this)
        {
            notifyObservers(trainCards);
        }
    }

    public List<DestinationCard> getDestinationCards()
    {
        return destinationCards;
    }

    public List<TrainCard> getTrainCards()
    {
        return trainCards;
    }
    public void addCard(TrainCard card)
    {
        this.trainCards.add(card);
        synchronized (this)
        {
            notifyObservers(card);
        }
    }
    public void addCard(DestinationCard card)
    {
        this.destinationCards.add(card);
        synchronized (this)
        {
            notifyObservers(card);
        }
    }
}
