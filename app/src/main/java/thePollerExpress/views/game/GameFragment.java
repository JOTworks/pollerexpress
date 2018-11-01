package thePollerExpress.views.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shared.models.Game;
import com.shared.models.Player;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodCallerFragment;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.GamePresenter;
import thePollerExpress.presenters.game.interfaces.IGamePresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.setup.GameSelectionFragment;

public class GameFragment extends Fragment implements IGameView {

    IGamePresenter gamePresenter;
    @Override
    public void displayError(String errorMessage) {

        // todo: fill out
    }

    @Override
    public void changeView(IPollerExpressView view) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamePresenter = new GamePresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game, container, false);

        FragmentManager fm = getFragmentManager();

        Fragment fragment = new MethodCallerFragment();
        fm.beginTransaction()
                .add(R.id.chat_history_fragment_container, fragment)
                .commit();

        fragment = new DestinationSelectionFragment();
        fm.beginTransaction()
                .add(R.id.destination_fragment_container, fragment)
                .commit();


        // Abby
        fragment = new TrainCardHandFragment();
        fm.beginTransaction()
                .add(R.id.train_card_hand_fragment_container, fragment)

        ClientData CD = ClientData.getInstance();
        Game game = CD.getGame();

        List<Player> Players = game.getPlayers();

        fragment = PlayerFragment.newInstance(game.getPlayers().get(0).getName());
        fm.beginTransaction()
                .add(R.id.player1_fragment_container, fragment)
                .commit();
        fragment = PlayerFragment.newInstance(game.getPlayers().get(0).getName());
        fm.beginTransaction()
                .add(R.id.player2_fragment_container, fragment)
                .commit();
        fragment = PlayerFragment.newInstance(game.getPlayers().get(0).getName());
        fm.beginTransaction()
                .add(R.id.player3_fragment_container, fragment)
                .commit();
        fragment = PlayerFragment.newInstance(game.getPlayers().get(0).getName());
        fm.beginTransaction()
                .add(R.id.player4_fragment_container, fragment)
                .commit();

        gamePresenter.startGame();


       /* fragment = new PlayerFragment();
        fm.beginTransaction()
                .add(R.rotation.player_1, fragment)
                .commit();
        fragment = new PlayerFragment();
        fm.beginTransaction()
                .add(R.rotation.player_2, fragment)
                .commit();
        fragment = new PlayerFragment();
        fm.beginTransaction()
                .add(R.rotation.player_3, fragment)
                .commit();
        fragment = new PlayerFragment();
        fm.beginTransaction()
                .add(R.rotation.player_4, fragment)
                .commit();*/

        return v;
    }



}
