package com.shared.models;

import java.io.Serializable;

public class User extends Player implements Serializable
{
    public String password;//this really shouldn't be public ;) keep your password private
    public Authtoken token;
    public DestCardHand destCardHand;
    public TrainCardHand trainCardHand;
    public User(String name, String password)
    {
        super(name);
        this.password = password;
        destCardHand = new DestCardHand();
        trainCardHand = new TrainCardHand();
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

    public TrainCardHand getTrainCardHand()
    {
        return trainCardHand;
    }
}
