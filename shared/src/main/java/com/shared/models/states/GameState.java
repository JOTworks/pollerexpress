package com.shared.models.states;

public class GameState {

    String activePlayer;
    SUBSTATE subState;

    public GameState() {
        this.activePlayer = null;
        this.subState = null;
    }

    public enum SUBSTATE
   {
       //before first turn states
       NEED_DISCARD_5, NEED_DISCARD_4, NEED_DISCARD_3, NEED_DISCARD_2, NEED_DISCARD_1,
       //player turn states
       DRAWN_ONE, TURN_BEGINNING, DRAWN_DEST,
       //after last turn state
       GAME_OVER
   }

}


