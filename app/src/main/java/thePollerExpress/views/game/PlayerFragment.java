package thePollerExpress.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shared.models.Player;

import cs340.pollerexpress.R;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.presenters.game.interfaces.IPlayerPresenter;
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
        String tempColor = args.getString("playerColor", "pink");
        tempColor = tempColor.toLowerCase();
        switch(tempColor){
            case "red":
                isNotTurnColor = 0x88ff0000;
                isTurnColor = 0xffff0000;
                break;
            case "green":
                isNotTurnColor = 0x8800ff00;
                isTurnColor = 0xff00ff00;
                break;
            case "blue":
                isNotTurnColor = 0x880000ff;
                isTurnColor = 0xff0000ff;
                break;
            default:
                isNotTurnColor = 0x88ffffff;
                isTurnColor = 0xffffffff;
                break;

        }


        playerPresenter = new PlayerPresenter(this,playerName);
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
    public void renderPlayer(Player p) {
        playerName.setText(p.getName());
        playerPoints.setText(Integer.toString(p.getPoints()));
        playerTrains.setText(Integer.toString(p.getTrainCount()));
        playerDestinationCards.setText(Integer.toString(p.getDestinationCardCount()));
        playerTrainCards.setText(Integer.toString(p.getTrainCardCount()));
        if(isTurn){ playerBackground.setBackgroundColor(isTurnColor);}
        else{ playerBackground.setBackgroundColor(isNotTurnColor);}
    }

    @Override
    public void isTurn() {
        isTurn = true;
    }

    @Override
    public void isNotTurn() {
        isTurn = false;
    }

    @Override
    public void displayError(String errorMessage) {

    }

    @Override
    public void changeView(IPollerExpressView view) {

    }
}
