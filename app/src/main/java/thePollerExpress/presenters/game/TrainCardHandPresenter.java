package thePollerExpress.presenters.game;

import com.shared.models.TrainCard;
import com.shared.models.TrainCardHand;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.ITrainCardHandPresenter;
import thePollerExpress.views.game.interfaces.ITrainCardHandView;

public class TrainCardHandPresenter implements ITrainCardHandPresenter, Observer{

    ITrainCardHandView handView;
    ClientData clientData;

    public TrainCardHandPresenter(ITrainCardHandView handView) {
        this.handView = handView;
        clientData = ClientData.getInstance();

        // add this class as an observer of TrainCardHand.
        clientData.getUser().getTrainCardHand().addObserver(this);

        // display the hand right away....not sure if this is going to work
//        handView.displayHand(clientData.getUser().getTrainCardHand().getCardsAsStrings());
//
    }

    @Override
    public void update(Observable o, Object arg) {

        if( !(arg instanceof TrainCard) ) return;

        /*
        * I want to give the view a list of strings,
        * where each string tells me what
        * color train card I have.
        * */

        ArrayList<String> trainCardHand = clientData.getUser().getTrainCardHand().getCardsAsStrings();
        handView.displayHand(trainCardHand);
    }
}
