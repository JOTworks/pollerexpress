package com.shared.models;

import com.shared.models.cardsHandsDecks.DestCardHand;
import com.shared.models.cardsHandsDecks.DestCardOptions;
import com.shared.models.cardsHandsDecks.TrainCardHand;

import java.io.Serializable;

public class User extends Player implements Serializable
{
    public String password;//this really shouldn't be public ;) keep your password private
    public Authtoken token;
    private DestCardHand destCardHand;
    private TrainCardHand trainCardHand;
    private DestCardOptions destCardOptions;
    public User(String name, String password)
    {
        super(name);
        this.password = password;
        destCardHand = new DestCardHand();
        trainCardHand = new TrainCardHand();
        destCardOptions = new DestCardOptions();
    }
    public User(String name, String password, String gameId)
    {
        super(name, gameId);
        this.password = password;
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
}
