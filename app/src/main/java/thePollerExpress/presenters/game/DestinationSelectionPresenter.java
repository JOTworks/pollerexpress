package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.interfaces.ICommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IDestinationSelectionPresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.game.interfaces.IDestinationSelectionView;

/**
 * Abby
 */
public class DestinationSelectionPresenter implements IDestinationSelectionPresenter, Observer {

    private IDestinationSelectionView view;
    private GameFacade facade;
    ClientData CD = ClientData.getInstance();

    public DestinationSelectionPresenter(IDestinationSelectionView view) {
        this.view = view;
        CD.getUser().getDestCardOptions().addObserver(this);
    }

    public void discardDestCard(List<DestinationCard> cards){
        //just so it still builds
    }
    @Override
    public void discardDestCards(final List<DestinationCard> cards) {
        AsyncRunner discardDestCardTask = new AsyncRunner(view);

        discardDestCardTask.setNextView(ViewFactory.createDestinationHandView());
        discardDestCardTask.execute(new ICommand()
        {
            @Override
            public Object execute() throws CommandFailed
            {
                return facade.discardDestCard(CD.getUser(), cards);
            }
        });
    }

    @Override
    public void discardButtonPressed(List<Boolean> selected) {
        List cards = new ArrayList();
        for(int i=0;i<selected.size();i++){
            if(!selected.get(i)){
                cards.add(CD.getUser().getDestCardOptions().getDestinationCards().get(i));
            }
        }
        if(cards.size()>CD.getUser().getDestinationDiscardCount()){
            view.displayError("You can only discard "+CD.getUser().getDestinationDiscardCount()+" card");
        }else {
            discardDestCard(cards);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        view.displayError("updated");
        view.renderCards(ClientData.getInstance().getUser().getDestCardOptions().getDestinationCards());
    }
}
