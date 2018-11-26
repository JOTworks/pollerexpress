package thePollerExpress.presenters.game;

import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.states.GameState;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import thePollerExpress.models.ClientData;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.DestinationSelectionFragment;
import thePollerExpress.views.game.interfaces.IBankView;
import thePollerExpress.views.game.interfaces.IDestinationPresenter;
import thePollerExpress.views.game.interfaces.IDestinationSelectionView;
import thePollerExpress.views.game.interfaces.IDestinationView;
import com.shared.models.*;

import static com.shared.models.states.GameState.State.DRAWN_DEST;

public class DestinationPresenter implements IDestinationPresenter, Observer {
    IDestinationView view;
    ClientData CD;
    public DestinationPresenter(IDestinationView view)
    {
            this.view = view;
            CD = ClientData.getInstance();
            CD.getGame().getVisibleCards().addObserver(this);
            CD.getGame().addObserver(this);

    }
    @Override
    public List<DestinationCard> get()
    {
        return ClientData.getInstance().getUser().getDestCardHand().getDestinationCards();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(CD.getGame().getGameState().getTurn().equals(CD.getUser().getName())){
            if(CD.getGame().getGameState().getState().equals(DRAWN_DEST)){
                view.changeView(new DestinationSelectionFragment());
            }

        }
    }
}
