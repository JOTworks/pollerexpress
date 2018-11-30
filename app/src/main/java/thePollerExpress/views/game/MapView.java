package thePollerExpress.views.game;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.shared.models.Color;
import com.shared.models.Map;
import com.shared.models.Route;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.utilities.AnchorPoints;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.interfaces.IMapPresenter;
import thePollerExpress.utilities.PresenterFactory;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IMapView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class MapView extends Fragment implements IMapView, IPollerExpressView
{

    HorizontalScrollView hScroll;
    VScrollView vScroll;
    IMapPresenter presenter;
    DrawView map;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View v = inflater.inflate(R.layout.fragment_bank, container, false);
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);
        hScroll = (HorizontalScrollView) v.findViewById(R.id.scrollHorizontal);
        vScroll = (VScrollView) v.findViewById(R.id.scrollVertical);
        vScroll.sv = hScroll;
        map = v.findViewById(R.id.map_imageView2);
        presenter = PresenterFactory.createIMapPresenter(this);
        map.setPresenter(presenter);

       // map.invalidate();

        map.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               onMapclicked();
            }
        });//*/
        return v;
    }

    public void onMapclicked()
    {
        Map myMap = presenter.getMap();
        float min_distance = 18;//TODO make this a constant
        Route chosen = null;
        //displayError(String.valueOf(vScroll.getScrollY()));

        float x = map.x - vScroll.getX();//+ hScroll.getScrollX()
        float y = map.y + vScroll.getScrollY() - vScroll.getY();
        displayError(String.format("%f,%f", x,y));
        for(Route r : myMap.getRoutes())
        {
            //find the distance
            AnchorPoints pts = new AnchorPoints(r);
            float distance = pts.aprox(x, y);
            
            //Log.d("drawView Click", String.format("%d, %d", hScroll.getScrollX(), vScroll.getScrollY()));
            //Log.d("drawViewListener",String.format("distance = %f, p(%f, %f)%s, %s", distance, x, y, pts.toString(), r.toString()) );
            if(distance < min_distance)
            {
                chosen = r;
                min_distance = distance;
            }
        }
        if(chosen != null)
        {
            //displayError(chosen.toString());
            presenter.claimRoute(chosen);

        }
    }


    public void showPopup(List<List<TrainCard>> permutations)
    {
        Context main = getActivity();
        LayoutInflater inflater = (LayoutInflater) main.getSystemService(LAYOUT_INFLATER_SERVICE);
        int width = (vScroll.getWidth()*3)/4;
        int height = HorizontalScrollView.LayoutParams.WRAP_CONTENT;


        HorizontalScrollView layout= new HorizontalScrollView(main);

        LinearLayout inner = new LinearLayout(main);
        layout.addView(inner);
        inner.setOrientation(LinearLayout.HORIZONTAL);

        //displayError("showing popup with permutations "+ String.valueOf(permutations.size()));

        final PopupWindow popUp = new PopupWindow(layout, width, height, true);

        for(final List<TrainCard> data: permutations)
        {
            int regularcard_count = 0;
            int loco_count = 0;
            TrainCard tempCard=data.get(0);
            for(TrainCard t : data)
            {
                if(t.getColor().equals(Color.TRAIN.RAINBOW))
                {
                    loco_count +=1;
                }
                else
                {
                    tempCard = t;
                    regularcard_count += 1;
                }
            }
            LinearLayout temp = (LinearLayout) inflater.inflate(R.layout.map_popup, null);
            TextView regular_card = temp.findViewById(R.id.map_popup_normal);
            regular_card.setBackground(getFromCard(tempCard));
            TextView loco = temp.findViewById(R.id.map_popup_loco);

            regular_card.setText( String.valueOf(regularcard_count));
            loco.setText(String.valueOf(loco_count));

            temp.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    presenter.claim(data);
                    popUp.dismiss();
                }
            });
            inner.addView(temp);
        }

        popUp.setContentView(layout);
        popUp.showAtLocation(getView(), Gravity.CENTER, 0, 0);
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        //Log.d("MapView", "onDestroy");
        //TODO tell my presenter to disconnect itself from what it is observing.
        this.presenter.onDestroy();
    }

    @Override
    public void displayError(String errorMessage)
    {
        android.widget.Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeView(IPollerExpressView view)
    {

    }


    @Override
    public void redrawMap()
    {
        map.invalidate();
    }


    private Drawable getFromCard(TrainCard card)
    {
        switch(card.getColor())
        {
            case RED:
                return getResources().getDrawable(R.drawable.red_train_card);
            case BLUE:
                return getResources().getDrawable(R.drawable.blue_train_card);
            case BLACK:
                return getResources().getDrawable(R.drawable.black_train_card);
            case YELLOW:
                return getResources().getDrawable(R.drawable.yellow_train_card);
            case GREEN:
                return getResources().getDrawable(R.drawable.green_train_card);
            case WHITE:
                return getResources().getDrawable(R.drawable.white_train_card);
            case ORANGE:
                return getResources().getDrawable(R.drawable.orange_train_card);
            case PURPLE:
                return getResources().getDrawable(R.drawable.purple_train_card);
            case RAINBOW:
                return getResources().getDrawable(R.drawable.rainbow_train_car);
        }
        return null;//TODO replace with blank
    }

    @Override
    public void setClickable(boolean b)
    {
        map.setClickable(b);
    }
}
