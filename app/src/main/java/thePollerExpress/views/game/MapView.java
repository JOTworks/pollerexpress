package thePollerExpress.views.game;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import cs340.pollerexpress.R;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.setup.GameSelectionPresenter;
import thePollerExpress.views.MainActivity;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.setup.SetupViewAdapters.GameSelectAdapter;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.ListPopupWindow.MATCH_PARENT;

public class MapView extends Fragment implements IGameView
{

    HorizontalScrollView hScroll;
    VScrollView vScroll;
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
        return v;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("MapView", "onDestroy");
    }
}
