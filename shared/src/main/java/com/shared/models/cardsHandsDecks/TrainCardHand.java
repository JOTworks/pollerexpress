package com.shared.models.cardsHandsDecks;

import com.shared.models.Color;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class TrainCardHand extends Hand implements Serializable
{
    private List<TrainCard> trainCards = new ArrayList<>();

    public void setTrainCards(List<TrainCard> trainCards)
    {
        for(TrainCard card : trainCards) {

            this.trainCards.add(card);
        }

        synchronized (this)
        {
            this.setChanged();
            notifyObservers(trainCards);
        }
    }

    public void addToHand(TrainCard card) {

        trainCards.add(card);

        synchronized (this)
        {
            this.setChanged();
            notifyObservers(card);
        }
    }

    public void removeFromHand(TrainCard card) {

        trainCards.remove(card);

        synchronized (this)
        {
            this.setChanged();
            notifyObservers(card);
        }
    }

    public ArrayList<String> getCardsAsStrings() {

        ArrayList<String> trainCardStrings = new ArrayList<>();

        for(int i = 0; i < trainCards.size(); i++) {

            trainCardStrings.add(trainCards.get(i).getColorAsString());
        }

        return trainCardStrings;
    }

    public List<TrainCard> getCardsByType(Color.TRAIN cardType)
    {
        List<TrainCard> cards = new ArrayList<>();
        for(TrainCard c: trainCards)
        {
            if(c.getColor().equals(cardType))
            {
                cards.add(c);
            }
        }
        return cards;
    }
    public boolean canClaimRoute(Route r)
    {
        List<TrainCard> loco = getCardsByType(Color.TRAIN.RAINBOW);
        if(r.getColor() == Color.TRAIN.RAINBOW)
        {
            for(Color.TRAIN color: Color.TRAIN.values())
            {
                if(!color.equals(Color.TRAIN.RAINBOW) && (loco.size() +getCardsByType(color).size() >= r.getDistance()))
                {
                    return true;
                }

            }
            return false;
        }
        else
        {
            List<TrainCard> cards = getCardsByType(r.getColor());

            return cards.size() + loco.size() >= r.getDistance();

        }
    }
    public List<List<TrainCard>> getClaimPermutatations(Route r)
    {
        List<List<TrainCard>> permutations = new ArrayList<>();
        List<TrainCard> loco = getCardsByType(Color.TRAIN.RAINBOW);
        if(r.getColor() == Color.TRAIN.RAINBOW)
        {
            for(Color.TRAIN color: Color.TRAIN.values())
            {
                //idon't like the duplication, but,
                //i don't want to call getCardsByType getLOCO multiple times either as its not the fastest operation....
                List<TrainCard> cards = getCardsByType(color);
                if(!color.equals(Color.TRAIN.RAINBOW) && cards.size() != 0)
                {

                    if(cards.size() >= r.getDistance())
                    {
                        cards = cards.subList(0,r.getDistance());
                    }
                    else
                    {
                        cards.addAll(loco.subList(0, Math.min(r.getDistance()-cards.size(), loco.size())));
                    }

                }
                if(cards.size() == r.getDistance())
                {
                    permutations.add(cards);
                }

            }
        }
        else
        {
            List<TrainCard> cards = getCardsByType(r.getColor());
            if(cards.size() >= r.getDistance())
            {
                cards = cards.subList(0,r.getDistance());
            }
            else
            {
                //add locomotives
                cards.addAll(loco.subList(0, r.getDistance()-cards.size()));
            }
            if(cards.size() == r.getDistance())
            {
                permutations.add(cards);
            }

        }
        return permutations;
    }
    public List<TrainCard> getList() {
        return trainCards;
    }

    public boolean contains(List<TrainCard> cards)
    {
        return this.trainCards.containsAll(cards);
    }
}
