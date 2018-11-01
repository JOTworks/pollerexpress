package com.shared.models.cardsHandsDecks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DestCardOptions extends Observable implements Serializable { //TODO: there should really be inheritance with card choices, hands, etc.
    private List<DestinationCard> destCards;

    public DestCardOptions()
    {
        destCards = new ArrayList<>();
    }

    public List<DestinationCard> getDestinationCards(){ return destCards; }
    public void setDestinationCards(List<DestinationCard> destinationCards)
    {
        this.destCards = destinationCards;
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(destinationCards);
        }
    }

    public void addToOptions(DestinationCard card) {
        destCards.add(card);
        synchronized (this)
        {

            this.setChanged();
            notifyObservers(card);
        }
    }
    public void doNothing() {
        synchronized (this)
        {
            this.setChanged();
            notifyObservers();
        }
    }
    public void removeFromOptions(DestinationCard card) {
        destCards.remove(card); //TODO: make sure that will recognize which one to toss
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(card);
        }
    }




}
