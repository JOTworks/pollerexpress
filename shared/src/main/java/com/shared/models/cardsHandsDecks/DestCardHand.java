package com.shared.models.cardsHandsDecks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DestCardHand extends Hand implements Serializable {
    private List<DestinationCard> destCards;

    public DestCardHand()
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

    public void addToHand(DestinationCard card) {
        destCards.add(card);
        System.out.println("!!!" + card.toString());
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(card);
        }
    }

    public void removeFromHand(DestinationCard card) {
        destCards.remove(card); //TODO: make sure that will recognize which one to toss
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(card);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("Size: ");
        builder.append(destCards.size());
        for(DestinationCard card : destCards) {
            builder.append('\n');
            builder.append(card.toString());
        }
        return builder.toString();
    }
}
