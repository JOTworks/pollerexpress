package thePollerExpress.views.game;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.shared.models.Game;
import com.shared.models.Player;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodCallerFragment;
import thePollerExpress.presenters.setup.ILobbyPresenter;
import thePollerExpress.presenters.setup.LobbyPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.GameViewAdapters.CardAdapter;
import thePollerExpress.views.setup.ILobbyView;
import thePollerExpress.views.setup.SetupGameFragment;
import thePollerExpress.views.setup.SetupViewAdapters.PlayerAdapter;

public class DestinationHandFragment extends Fragment implements IPollerExpressView {


        //**private GamePlayPresenter gamePlayPresenter;

        private RecyclerView mGameRecyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;



        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //**gamePlayPresenter = new gamePlayPresenter(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_destination_hand, container, false);


            mGameRecyclerView = (RecyclerView) v.findViewById(R.id.player_recycler_view);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mGameRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext());
            mGameRecyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            //**Game game = lobbyPresenter.getGame();
            //**mAdapter = new CardAdapter(game.));
            //**mGameRecyclerView.setAdapter(mAdapter);
            //---------------------Start Game Button---------------------------------------------------

            return v;
        }


        @Override
        public void displayError(String errorMessage) {

        }

        @Override
        public void changeView(IPollerExpressView view) {

        }


}
