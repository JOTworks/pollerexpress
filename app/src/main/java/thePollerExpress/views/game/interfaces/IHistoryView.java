package thePollerExpress.views.game.interfaces;

import java.util.ArrayList;

import thePollerExpress.views.IPollerExpressView;

public interface IHistoryView extends IPollerExpressView
{

    public void displayError(String message);
    public void changeToDevView();
    public void displayChats(ArrayList<String> message);
}
