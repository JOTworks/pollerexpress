package com.shared.models.cardsHandsDecks;

import java.util.Observable;

public class Hand extends Observable {

    private final String UPDATE_ALL_STRING = "updateAll";

    public void updateObservables() {
        this.setChanged();
        this.notifyObservers(UPDATE_ALL_STRING);
    }
}
