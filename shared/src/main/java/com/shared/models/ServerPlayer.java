package com.shared.models;

import com.shared.models.Player;
import com.shared.models.cardsHandsDecks.DestCardHand;
import com.shared.models.cardsHandsDecks.DestCardOptions;
import com.shared.models.cardsHandsDecks.TrainCardHand;

public class ServerPlayer extends Player
{
    private TrainCardHand trainCardHand;
    private DestCardOptions destCardOptions;
    private DestCardHand destCardHand;
    private int discards;
    public ServerPlayer(String name)
    {
        super(name);
        destCardHand = new DestCardHand();
        trainCardHand = new TrainCardHand();
        destCardOptions = new DestCardOptions();
        setTrainCount(25);//TODO remove this.
    }

    public DestCardHand getDestCardHand()
    {
        return destCardHand;
    }

    public DestCardOptions getDestCardOptions()
    {
        return destCardOptions;
    }

    public TrainCardHand getTrainCardHand()
    {
        return trainCardHand;
    }


    public Player toPlayer()
    {
        Player temp =new Player(this.name);
        temp.setTrainCount(this.getTrainCount());
        temp.setTrainCardCount(this.getTrainCardCount());
        temp.setPoints(this.getPoints());
        temp.setDestinationCardCount(this.getDestinationCardCount());
        temp.setDestinationDiscardCount(this.getDestinationDiscardCount());
        temp.setColor(color);
        return temp;
    }


    /**
     *
     * @param i
     */
    public void setDiscards(int i)
    {
        discards = i;
    }

    public int getDiscards()
    {
        return discards;
    }
}
