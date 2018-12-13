package thePollerExpress.presenters.game;

import thePollerExpress.models.ClientData;
import thePollerExpress.views.ISpecialView;

public class MainPresenter
{
    ISpecialView view;
    public MainPresenter(ISpecialView view)
    {
        this.view = view;
        ClientData.getInstance().setMainPresenter(this);
    }

    public void goToGame()
    {
        view.goToGame();
    }

}
