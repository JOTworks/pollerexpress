package com.shared.models.states;

public class GameState {

    private String activePlayer;
    private SUBSTATE subState;

    public String getActivePlayer(){
        return activePlayer;
    }
    public SUBSTATE getSubState(){
        return subState;
    }
    public void setActivePlayer(String activePlayer){
        this.activePlayer = activePlayer;
    }
    public void setSubState(SUBSTATE substate){
        this.subState = substate;
    }

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


