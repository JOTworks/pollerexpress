package thePollerExpress.presenters.game.interfaces;

import java.util.Observer;

public interface IBankPresenter extends Observer
{
    int getTrainDeckSize();
    int getDestinationDeckSize();
}
