package thePollerExpress.views.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.HorizontalScrollView;


import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import thePollerExpress.utilities.PresenterFactory;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.game.interfaces.IMapView;

public class MapView extends Fragment implements IMapView
{

    HorizontalScrollView hScroll;
    VScrollView vScroll;
    IMapPresenter presenter;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        hScroll = (HorizontalScrollView) v.findViewById(R.id.scrollHorizontal);
        vScroll = (VScrollView) v.findViewById(R.id.scrollVertical);
        vScroll.sv = hScroll;
        presenter = PresenterFactory.createIMapPresenter(this);
        return v;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("MapView", "onDestroy");
        //TODO tell my presenter to disconnect itself from what it is observing.
    }


    @Override
    public void displayError(String errorMessage) {

    }

    @Override
    public void changeView(IPollerExpressView view) {

    }


    @Override
    public void claimRoute()
    {

    }

}
