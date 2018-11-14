package com.shared.models.states;

public class GameState {

    String activePlayer;
    SUBSTATE subState;

   public enum SUBSTATE
   {
       //before first turn states
       NEED_DISCARD_5, NEED_DISCARD_4, NEED_DISCARD_3, NEED_DISCARD_2, NEED_DISCARD_1,
       //player turn states
       DRAWN_ONE, TURN_BEGINNING, DRAWN_DEST,
       //after last turn state
       GAME_OVER
   }

   public GameState(String activePlayer, SUBSTATE subState) {
       this.activePlayer = activePlayer;
       this.subState = subState;
   }
}


