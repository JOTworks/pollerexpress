package thePollerExpress.presenters.game;

import android.util.Log;

import com.shared.models.Command;
import com.shared.models.GameInfo;
import com.shared.models.Player;
import com.shared.models.User;
import com.shared.utilities.CommandsExtensions;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import thePollerExpress.facades.SetupFacade;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.setup.IGameSelectionPresenter;
import thePollerExpress.utilities.AsyncRunner;
import thePollerExpress.utilities.ViewFactory;
import thePollerExpress.views.game.BankFragment;
import thePollerExpress.views.setup.IGameSelectionView;

/**
 * Abby
 * Responsible for implementing logic for game selection view
 */
public class BankPresenter implements Observer {

    private BankFragment view;
    private SetupFacade facade;
    ClientData clientData = ClientData.getInstance();

    public BankPresenter(BankFragment view) {
        this.view = view;
        Log.d("BankPresenter", "createdNew");
        facade = new SetupFacade();
        clientData.getGame().addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg)
    {
        System.out.println("!!!updated");
        float fail = 2454/0;

        // get the list of existing games
        Log.d("update", "ran update");
        // refresh the list of games in the view
        view.render();

    }
}
