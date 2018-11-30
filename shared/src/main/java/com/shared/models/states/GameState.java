package com.shared.models.states;

import java.io.Serializable;

public class GameState implements Serializable {

    private String turn;

    private State state;

    public enum State {

        READY_FOR_GAME_START,
        WAITING_FOR_ONE_PLAYER,
        WAITING_FOR_TWO_PLAYERS,
        WAITING_FOR_THREE_PLAYERS,
        WAITING_FOR_FOUR_PLAYERS,
        WAITING_FOR_FIVE_PLAYERS,

        NO_ACTION_TAKEN,
        //These are Jack's enumerations and he's okay with them being renamed.
        //player turn states
        DRAWN_ONE, DRAWN_DEST,
        //after last turn state
        GAME_OVER;

        public State next() {
            return values()[ordinal()-1];
        }

    }

    public GameState() {
    }

    public GameState(String turn, State state)
    {
        this.turn = turn;
        this.state = state;
    }

    public String getTurn() {
        return turn;
    }

    public State getState() {
        return state;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public void setState(State state) {
        this.state = state;
    }
}

