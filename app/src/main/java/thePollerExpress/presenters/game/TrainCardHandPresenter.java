package thePollerExpress.presenters.game;

import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.List;
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
    }

    @Override
    public void update(Observable o, Object arg) {
        handView.displayHand();
    }

    @Override
    public List<TrainCard> get() {
        return clientData.getUser().getTrainCardHand().getList();
    }
}
