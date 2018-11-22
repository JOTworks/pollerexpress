package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Game;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.cardsHandsDecks.VisibleCards;
import com.shared.models.interfaces.ICommand;

import java.util.List;
import java.util.Observable;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IBankPresenter;
import thePollerExpress.presenters.game.states.BankState;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.game.interfaces.IBankView;

public class BankPresenter implements IBankPresenter
{

    IBankView view;
    ClientData CD;

    public BankPresenter(IBankView view)
    {
        this.view = view;
        CD = ClientData.getInstance();
        CD.getGame().getVisibleCards().addObserver(this);
        CD.getGame().addObserver(this);
    }
    @Override
    public void update(Observable observable, Object o)
    {
        updateState();
        if(observable instanceof VisibleCards)
        {
            for (int i = 0; i < 5; ++i)
            {
                view.update(i, CD.getGame().getVisibleCards().get(i));
            }
        }
        else if(observable instanceof Game)
        {
            view.update();
        }
        else if(o instanceof String)
        {
            view.update();
        }
    }


    @Override
    public int getDestinationDeckSize()
    {
        return CD.getGame().DestinationCardDeck;
    }

    @Override
    public int getTrainDeckSize()
    {
        return CD.getGame().TrainCardDeck;
    }
    public void onDestroy()
    {
        try
        {
            CD.getGame().getVisibleCards().deleteObserver(this);
            CD.getGame().deleteObserver(this);
        }
        catch (Exception e)
        {
            //TODO log the exception.
        }
    }

    /*---------------------Player Actions-----------------------*/
    BankState bankState = new BankState();

    private void updateState() {
        //get state from cc
        bankState = bankState.changeState(CD.getGame().getGameState());
    }

    public void drawDestinationCards() {
        AsyncRunner drawDestCardTask = new AsyncRunner(view);

        drawDestCardTask.setNextView(ViewFactory.createDestinationHandView());
        drawDestCardTask.execute(new ICommand()
        {
            @Override
            public Object execute() throws CommandFailed
            {
                return bankState.drawDestinationCards();
            }
        });
    }

    public void drawTrainCardFromDeck(){
        AsyncRunner drawTrainCardTask = new AsyncRunner(view);
        drawTrainCardTask.execute(new ICommand()
        {
            @Override
            public Object execute() throws CommandFailed
            {
                return bankState.drawTrainCardFromDeck();
            }
        });
    }

    public void drawFaceupCard(final int cardIndex){

        AsyncRunner drawTrainCardTask = new AsyncRunner(view);
        drawTrainCardTask.execute(new ICommand()
        {
            @Override
            public Object execute() throws CommandFailed
            {
                return bankState.drawFaceupCard(cardIndex);
            }
        });
    }


}
