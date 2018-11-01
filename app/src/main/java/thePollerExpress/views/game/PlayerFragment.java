package thePollerExpress.views.game;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shared.models.Player;

import cs340.pollerexpress.R;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.presenters.game.IPlayerPresenter;
import thePollerExpress.presenters.game.PlayerPresenter;
import thePollerExpress.views.game.interfaces.IPlayerView;

public class PlayerFragment extends Fragment implements IPlayerView {

    LinearLayout playerBackground;
    TextView playerName;
    TextView playerPoints;
    TextView playerTrainCards;
    TextView playerTrains;
    TextView playerDestinationCards;

    IPlayerPresenter playerPresenter;

    public static PlayerFragment newInstance(String playerName) {
        PlayerFragment f = new PlayerFragment();
        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString("playerName", playerName);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        String playerName = args.getString("playerName", "Nameless");
        playerPresenter = new PlayerPresenter(this,playerName);
        //playerPresenter = new PlayerPresenter(this,"saragate name");
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
        isTurn();
        return v;
    }

    @Override
    public void renderPlayer(Player p) {
        playerName.setText(p.getName());
        playerPoints.setText(Integer.toString(p.getPoints()));
        playerTrains.setText(Integer.toString(p.getTrainCount()));
        playerDestinationCards.setText(Integer.toString(p.getDestinationCardCount()));
        playerTrainCards.setText(Integer.toString(p.getTrainCardCount()));
    }

    @Override
    public void isTurn() {
        System.out.println("!!!isTurn");
        playerBackground.setBackgroundColor(0xffff00d4);
    }

    @Override
    public void isNotTurn() {
        playerBackground.setBackgroundColor(0x000000);
    }

    @Override
    public void displayError(String errorMessage) {

    }

    @Override
    public void changeView(IPollerExpressView view) {

    }
}
