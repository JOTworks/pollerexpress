package thePollerExpress.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shared.models.Color;
import com.shared.models.Player;

import cs340.pollerexpress.R;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.presenters.game.interfaces.IPlayerPresenter;
import thePollerExpress.presenters.game.PlayerPresenter;
import thePollerExpress.views.game.interfaces.IPlayerView;
import android.widget.Toast;

public class PlayerFragment extends Fragment implements IPlayerView {

    LinearLayout playerBackground;
    TextView playerName;
    TextView playerPoints;
    TextView playerTrainCards;
    TextView playerTrains;
    TextView playerDestinationCards;

    IPlayerPresenter playerPresenter;
    boolean isTurn = false;
    int isTurnColor;
    int isNotTurnColor;

    public static PlayerFragment newInstance(Player p) {
        PlayerFragment f = new PlayerFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("playerName", p.getName());
        args.putString("playerColor", p.getColor().toString());
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        String playerName = args.getString("playerName", "Nameless");
        //String tempColor = args.getString("playerColor", "pink");
        //tempColor = tempColor.toLowerCase();

        playerPresenter = new PlayerPresenter(this,playerName);

        Color.PLAYER color = playerPresenter.getPlayer().getColor();
        isNotTurnColor = Color.getIsNotTurnColor(color);
        isTurnColor = Color.getIsTurnColor(color);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_player, container, false);
        playerBackground = (LinearLayout) v.findViewById(R.id.player_background);
        playerName = (TextView) v.findViewById(R.id.player_name);
        playerPoints = (TextView) v.findViewById(R.id.player_points);
        playerTrains = (TextView) v.findViewById(R.id.player_trains);
        playerTrainCards = (TextView) v.findViewById(R.id.player_train_cards);
        playerDestinationCards = (TextView) v.findViewById(R.id.player_destination_cards);


        //this line isnt needed when observer works correctly i think
        renderPlayer(playerPresenter.getPlayer());
        return v;
    }

    @Override
    public void renderPlayer(Player p)
    {
        playerName.setText(p.getName());
        playerPoints.setText(Integer.toString(p.getPoints()));
        playerTrains.setText(Integer.toString(p.getTrainCount()));
        playerDestinationCards.setText(Integer.toString(p.getDestinationCardCount()));
        playerTrainCards.setText(Integer.toString(p.getTrainCardCount()));
        if(isTurn){ playerBackground.setBackgroundColor(isTurnColor);}
        else{ playerBackground.setBackgroundColor(isNotTurnColor);}
    }

    @Override
    public void isTurn()
    {
        isTurn = true;
        playerBackground.setBackgroundColor(isTurnColor);
    }

    @Override
    public void isNotTurn()
    {
        isTurn = false;
        playerBackground.setBackgroundColor(isNotTurnColor);
    }

    @Override
    public void displayError(String errorMessage) {

    }

    @Override
    public void changeView(IPollerExpressView view) {

    }
}
