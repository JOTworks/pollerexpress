package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Command;
import com.shared.models.DestinationCard;
import com.shared.models.User;
import com.shared.models.interfaces.ICommand;
import com.shared.utilities.CommandsExtensions;

import java.io.ObjectInput;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IDestinationSelectionPresenter;
import thePollerExpress.presenters.game.interfaces.IGamePresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.game.interfaces.IDestinationSelectionView;
import thePollerExpress.views.game.interfaces.IGameView;

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

    @Override
    public void discardDestCard(final DestinationCard card) { //TODO: will have to be modified to handle discarding two cards
        AsyncRunner discardDestCardTask = new AsyncRunner(view);

        discardDestCardTask.setNextView(ViewFactory.createDestinationHandView());
        discardDestCardTask.execute(new ICommand()
        {
            @Override
            public Object execute() throws CommandFailed
            {
                return facade.discardDestCard(CD.getUser(), card);
            }
        });
    }

    @Override
    public void discardButtonPressed(List<Boolean> selected) {

        //k implement whatever, this is the list of cards selected. true means we are keeping the card, false means we are discarding the card
        view.displayError("discard Button Pressed!");
    }

    @Override
    public void update(Observable o, Object arg) {
        view.displayError("updated");
        view.renderCards(ClientData.getInstance().getUser().getDestCardOptions().getDestinationCards());
    }
}
