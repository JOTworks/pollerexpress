package thePollerExpress.presenters.game.states;

import com.shared.models.states.GameState;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;

import static com.shared.models.states.GameState.SUBSTATE.*;

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
        if(gameState.getSubState()==null || !gameState.getActivePlayer().equals(CD.getUser().getName()))
            return new BankStateInactive();
        if(gameState.getSubState() == TURN_BEGINNING )
            return new BankStateDrawn0();
        if(gameState.getSubState() == DRAWN_ONE )
            return new BankStateDrawn1();

        return new BankStateInactive();
    }
}

class BankStateDrawn1 extends BankState {
    public String drawTrainCardFromDeck() {
        //facade.
        return null;
    }
    public String drawFaceupCard(int cardIndex){
        facade.drawVisibleCard(cardIndex);
        return null;
    }
}

class BankStateDrawn0 extends BankStateDrawn1 {
    public String drawDestinationCards() {
        facade.drawDestCard();
        return null;
    }
}

class BankStateInactive extends BankState {/*derpy class*/}
