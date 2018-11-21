package thePollerExpress.presenters.game.states;

import com.shared.models.states.GameState;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;

import static com.shared.models.states.GameState.State.*;

public class BankState {

    protected GameFacade facade = new GameFacade();
    protected ClientData CD = ClientData.getInstance();

    public String drawDestinationCards() {return "BankState-dest"; }
    public String drawTrainCardFromDeck() {
        return "BankState-deck";
    }
    public String drawFaceupCard(int cardIndex){
       return "BankState-faceup";
    }
    public BankState changeState(GameState gameState){
        if(gameState.getState()==null)
            return new BankStateInactive();
        if(gameState.getTurn()==null)
            return new BankStateInactive();
        if(!gameState.getTurn().equals(CD.getUser().getName()))
            return new BankStateInactive();
        if(gameState.getState() == NO_ACTION_TAKEN )
            return new BankStateDrawn0();
        if(gameState.getState() == DRAWN_ONE )
            return new BankStateDrawn1();

        return new BankStateInactive();
    }
}

class BankStateDrawn1 extends BankState {
    public String drawTrainCardFromDeck() {
        facade.drawTrainCardFromDeck();
        return "BankStateDrawn1-deck";
    }
    public String drawFaceupCard(int cardIndex){
        facade.drawVisibleCard(cardIndex);
        return "BankStateDrawn1-faceup";
    }
}

class BankStateDrawn0 extends BankStateDrawn1 {
    public String drawDestinationCards() {
        facade.drawDestinationCards();
        return "BankStateDrawn0-dest";
    }
}

class BankStateInactive extends BankState {/*derpy class*/}
