package thePollerExpress.presenters.game.states;

import com.shared.models.states.GameState;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;

import static com.shared.models.states.GameState.State.*;

public class BankState {

    protected GameFacade facade = new GameFacade();
    protected ClientData CD = ClientData.getInstance();

    public String drawDestinationCards() {
        return null;
    }
    public String drawTrainCardFromDeck() {
        return null;
    }
    public String drawFaceupCard(int cardIndex){
       return null;
    }
    public BankState changeState(GameState gameState){
        if(gameState.getState()==null)
            return new BankStateInactive();
        if(gameState.getTurn()==null)
            return new BankStateInactive();
        if(gameState.getTurn().equals(CD.getUser().getName()))
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
        return null;
    }
    public String drawFaceupCard(int cardIndex){
        facade.drawVisibleCard(cardIndex);
        return null;
    }
}

class BankStateDrawn0 extends BankStateDrawn1 {
    public String drawDestinationCards() {
        facade.drawDestinationCards();
        return null;
    }
}

class BankStateInactive extends BankState {/*derpy class*/}
