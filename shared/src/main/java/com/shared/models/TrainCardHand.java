package com.shared.models;

import com.shared.models.TrainCard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class TrainCardHand extends Observable implements Serializable {

    private List<TrainCard> trainCards;

    public void setTrainCards(List<TrainCard> trainCards)
    {
        this.trainCards = trainCards;
        synchronized (this)
        {
            notifyObservers(trainCards);
        }
    }

    public void addToHand(TrainCard card) {
        trainCards.add(card);
        synchronized (this)
        {
            notifyObservers(card);
        }
    }

    public void removeFromHand(TrainCard card) {
        trainCards.remove(card); //TODO: make sure that will recognize which one to toss
        synchronized (this)
        {
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
}
