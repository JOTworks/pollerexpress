package thePollerExpress.presenters.game;

import com.shared.exceptions.CommandFailed;
import com.shared.models.Chat;

import com.shared.models.HistoryItem;
import com.shared.models.interfaces.ICommand;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.facades.GameFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IChatPresenter;
import thePollerExpress.presenters.game.interfaces.IGameHistoryPresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.views.game.interfaces.IChatView;
import thePollerExpress.views.game.interfaces.IGameHistoryView;

public class GameHistoryPresenter implements IGameHistoryPresenter, Observer {

    private IGameHistoryView gameHistoryView;
    private ClientData clientData;

    public GameHistoryPresenter(IGameHistoryView view)
    {
        this.gameHistoryView = view;
        clientData = ClientData.getInstance();
//        clientData.getGame().getChatHistory().addObserver(this);
        clientData.getGame().getGameHistory().addObserver(this);
    }


    @Override
    public ArrayList<String> getHistoryItems()
    {
        return clientData.getGame().getGameHistory().getItemsAsStrings();
    }

    @Override
    public void PressedChatViewButton() {
        gameHistoryView.changeToChatView();
    }

    @Override
    public void PressedGameHistoryViewButton() {
        gameHistoryView.displayError("Already in Game History");
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if( !(arg instanceof HistoryItem) ) return;

        // display the history
        String item = ((HistoryItem) arg).toString();
        gameHistoryView.displayGameHistoryItems(item);
    }
}
