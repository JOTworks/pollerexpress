package thePollerExpress.views.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.shared.models.Game;
import com.shared.models.Player;
import com.shared.models.states.GameState;

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
        android.widget.Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeView(IPollerExpressView view) {
        FragmentManager fragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, (Fragment) view);
        fragmentTransaction.commit();
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

//        Fragment fragment = new MethodCallerFragment();
        Fragment fragment = new GameHistoryFragment();
        fm.beginTransaction()
                .add(R.id.chat_history_fragment_container, fragment)
                .commit();

        ///
        GameState gameState = ClientData.getInstance().getGame().getGameState();
        if( (gameState.getTurn()==null) || (gameState.getTurn().equals(ClientData.getInstance().getUser().getName()) && gameState.getState().equals(GameState.State.DRAWN_DEST))){
            fragment = new DestinationSelectionFragment();
            fm.beginTransaction()
                    .add(R.id.destination_fragment_container, fragment)
                    .commit();
        }else{
            fragment = new DestinationHandFragment();
            fm.beginTransaction()
                    .add(R.id.destination_fragment_container, fragment)
                    .commit();
        }

        /////

        fragment = new TrainCardHandFragment();
        fm.beginTransaction()
                .add(R.id.train_card_hand_fragment_container, fragment)
                .commit();

        ClientData CD = ClientData.getInstance();
        Game game = CD.getGame();

        //terrible for loop that i had to break out becasuse of the r.id.framentnames
        List<Player> players = game.getPlayers();
        String userName = CD.getUser().getName();
        String playerName;
        int itr = 0;

//        //gets rid of user from players
//        for (Player p:players) {
//            if(userName.equals(p.getName()))
//                players.remove(p);
//        }

        if (players.size() > itr) {
            fragment = PlayerFragment.newInstance(players.get(itr));
            fm.beginTransaction()
                    .add(R.id.player1_fragment_container, fragment)
                    .commit();
            itr++;
        }
        if (players.size() > itr) {
            fragment = PlayerFragment.newInstance(players.get(itr));
            fm.beginTransaction()
                    .add(R.id.player2_fragment_container, fragment)
                    .commit();
            itr++;
        }
        if (players.size() > itr) {
            fragment = PlayerFragment.newInstance(players.get(itr));
            fm.beginTransaction()
                    .add(R.id.player3_fragment_container, fragment)
                    .commit();
            itr++;
        }
        if (players.size() > itr) {
            fragment = PlayerFragment.newInstance(players.get(itr));
            fm.beginTransaction()
                    .add(R.id.player4_fragment_container, fragment)
                    .commit();
            itr++;
        }



        ClientData.getInstance().getGame().getVisibleCards().updateObservables();
        ClientData.getInstance().getUser().getDestCardOptions().updateObservables();
        ClientData.getInstance().getGame().updateObservables();

        return v;
    }



}
