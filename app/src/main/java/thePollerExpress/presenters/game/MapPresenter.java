package thePollerExpress.presenters.game;

import android.widget.Toast;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.Map;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.interfaces.ICommand;
import com.shared.models.states.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.views.game.interfaces.IMapView;

public class MapPresenter implements IMapPresenter
{
    IMapView view;
    ClientData CD;
    Observable observing[];
    Route claiming = null;

    public MapPresenter(IMapView view)
    {
        this.view = view;
        CD = ClientData.getInstance();
        observing = new Observable[CD.getGame().getMap().getRoutes().size()];
        int i = 0;
        for (Observable o: CD.getGame().getMap().getRoutes() )
        {
            o.addObserver(this);
            observing[i] = o;
            i += 1;
        }
        CD.getGame().addObserver(this);
    }
    @Override
    public void update(Observable observable, Object o)
    {
        if(observable instanceof Game)
        {
            //set view to not clickable.
            //
            if(o instanceof GameState)
            {
                GameState state = (GameState) o;
                if(state.getState().equals(GameState.State.NO_ACTION_TAKEN) && state.getTurn().equals(CD.getUser().getName()))
                {
                    view.setClickable(true);
                }
                else
                {
                    view.setClickable(false);
                }
            }
        }
        else
        {
            view.redrawMap();
        }
    }

    @Override
    public void claimRoute(Route r)
    {
        if(r.getOwner()==null)
        {
            if(CD.getUser().getTrainCardHand().canClaimRoute(r) && CD.getUser().getTrainCount() >= r.getDistance())
            {
                //find the permutations and let the user pick
                //display using a popup window...
                claiming=r;
                List<List<TrainCard>> permutations = CD.getUser().getTrainCardHand().getClaimPermutatations(r);
                view.showPopup(permutations);
            }
            else
            {
                if(CD.getUser().getTrainCount() < r.getDistance())
                {
                    view.displayError("Not enough train cars to claim");
                }
                else
                {
                    view.displayError("Not enough train cards to claim " + r.toString());
                }
            }
        }
        else
        {
            //do nothing the route is claimed.
        }
    }
    @Override
    public void claim(List<TrainCard> cards_to_use)
    {
        final List<TrainCard> cards = new ArrayList<>();
        cards.addAll(cards_to_use);
        AsyncRunner task = new AsyncRunner(view);
        final Route r = claiming;
        claiming=null;
        task.execute(new ICommand()
        {
            @Override
            public Object execute() throws CommandFailed
            {
                return new GameFacade().claimRoute(r, cards);
            }
        });

        //do the stuff of claiming.
    }
    @Override
    public void onDestroy()
    {
        for (Observable o: observing)
        {
            o.deleteObserver(this);
        }
        CD.getGame().deleteObserver(this);
    }

    @Override
    public Map getMap()
    {
        return CD.getGame().getMap();
    }

}
