package Views;

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

import com.pollerexpress.models.GameInfo;

import Views.recycleViewAdapters.GameSelectAdapter;
import cs340.pollerexpress.R;
import presenter.GameSelectionPresenter;
import presenter.IGameSelectionPresenter;
import presenter.ILobbyPresenter;
import presenter.LobbyPresenter;
import presenter.LoginPresenter;

public class LobbyFragment extends Fragment implements ILobbyView {

    private ILobbyPresenter lobbyPresenter;

    private RecyclerView mGameRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button startGameButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lobbyPresenter = new LobbyPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_game_selection, container, false);


        mGameRecyclerView = (RecyclerView) v.findViewById(R.id.game_selection_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mGameRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mGameRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //TODO: fix this mAdapter = new GameSelectAdapter();
        mGameRecyclerView.setAdapter(mAdapter);


        //---------------------Start Game Button---------------------------------------------------
        startGameButton = (Button) v.findViewById(R.id.start_game_button);

        Button mStartGameButton = (Button) startGameButton;
        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: pull this to a method and give control to the presenter
                FragmentManager fm = getFragmentManager();
                //Fragment startGameFragment = fm.findFragmentById(R.id.fragment_create_game);
                Fragment GameFragment = new GameFragment();

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.right_side_fragment_container, GameFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return v;
    }


    @Override
    public void displayMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToSetupGameView() {
        FragmentManager fm = getFragmentManager();
        //Fragment createGameFragment = fm.findFragmentById(R.id.fragment_create_game);
        Fragment fragment = new SetupGameFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
        fm.popBackStack();
    }

}
