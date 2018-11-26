package thePollerExpress.views.setup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.shared.models.Game;
import com.shared.models.Player;



import thePollerExpress.Development.MethodCallerFragment;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.GameFragment;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.setup.SetupViewAdapters.PlayerAdapter;


import cs340.pollerexpress.R;
import thePollerExpress.presenters.setup.ILobbyPresenter;
import thePollerExpress.presenters.setup.LobbyPresenter;

public class LobbyFragment extends Fragment implements ILobbyView,  IPollerExpressView
{

    private ILobbyPresenter lobbyPresenter;

    private RecyclerView mGameRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button startGameButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lobbyPresenter = new LobbyPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_lobby, container, false);


        mGameRecyclerView = (RecyclerView) v.findViewById(R.id.player_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mGameRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mGameRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        Game game = lobbyPresenter.getGame();
        mAdapter = new PlayerAdapter(game.getPlayers());
        mGameRecyclerView.setAdapter(mAdapter);
        //---------------------Start Game Button---------------------------------------------------
        startGameButton = (Button) v.findViewById(R.id.start_game_button);

        Button mStartGameButton = (Button) startGameButton;
        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lobbyPresenter.startButtonPressed();
            }
        });

        return v;
    }

    /*This method if for testing purposes only.
    * This should be deleted when the game view is filled out.*/
    private void changeToMethodCallerFragment() {

        FragmentManager fm = getFragmentManager();
        Fragment fragment = new MethodCallerFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
        fm.popBackStack();
    }

    @Override
    public void changeToGameView() {
        FragmentManager fm = getFragmentManager();
        Fragment fragment = new GameFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
        fm.popBackStack();

        lobbyPresenter.onDestroy();

    }

    @Override
    public void displayMessage(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToSetupGameView() {
        FragmentManager fm = getFragmentManager();
        //Fragment createGameFragment = fm.findFragmentById(R.rotation.fragment_create_game);
        Fragment fragment = new SetupGameFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
        fm.popBackStack();

        lobbyPresenter.onDestroy();
    }

    @Override
    public void playerJoined(Player p)
    {

        mAdapter.notifyDataSetChanged();

        //mAdapter.notifyItemInserted(-1);
        //mAdapter.notifyItemChanged(-1, p);
    }

    @Override
    public void playerLeft(int i)
    {
        mAdapter.notifyItemRemoved(i);
    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeView(IPollerExpressView view)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        if (view instanceof IGameView)
//            fragmentTransaction.replace(R.id.fragment_container, (Fragment) view);
//        else
//            fragmentTransaction.replace(R.id.right_side_fragment_container, (Fragment) view);
        fragmentTransaction.commit();

        lobbyPresenter.onDestroy();
    }

//    @Override
//    public void onDetach()
//    {
//
//        super.onDetach();
//    }
//
//    @Override
//    public void onDestroyView() {
//        lobbyPresenter.onDestroy();
//        super.onDestroyView();
//    }

}
