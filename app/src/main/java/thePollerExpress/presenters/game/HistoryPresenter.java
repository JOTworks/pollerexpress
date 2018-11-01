package thePollerExpress.presenters.game;

import java.util.Observable;
import java.util.Observer;

import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.interfaces.IHistoryPresenter;

public class HistoryPresenter implements IHistoryPresenter, Observer {

    private ClientData clientData = ClientData.getInstance();

    public HistoryPresenter() {}

    @Override
    public void PressedChatViewButton() {

    }

    @Override
    public void PressedDevViewButton() {

    }

    @Override
    public void update(Observable o, Object arg) {


    }
}
