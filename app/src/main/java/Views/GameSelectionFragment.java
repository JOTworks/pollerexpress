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
import presenter.LoginPresenter;

public class GameSelectionFragment extends Fragment implements IGameSelectionView {

    private IGameSelectionPresenter gameSelectionPresenter;

    private RecyclerView mGameRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button createGameButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSelectionPresenter = new GameSelectionPresenter(this);
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
        mAdapter = new GameSelectAdapter(new GameInfo[] {
                new GameInfo("id", "game1", 5, 2),
                new GameInfo("id", "game2", 4, 1)}, gameSelectionPresenter); //TODO: use getGameList()
        mGameRecyclerView.setAdapter(mAdapter);


        //---------------------Create Game Button---------------------------------------------------
        createGameButton = (Button) v.findViewById(R.id.create_game_button);

        Button mCreateGameButton = (Button) createGameButton;
        mCreateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: pull this to a method and give control to the presenter
                FragmentManager fm = getFragmentManager();
                //Fragment createGameFragment = fm.findFragmentById(R.id.fragment_create_game);
                Fragment createGameFragment = new CreateGameFragment();

                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.right_side_fragment_container, createGameFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }


        @Override
    public void changeLobbyView() {

    }

    @Override
    public void changeCreateGameView() {

    }

    @Override
    public void renderGames(GameInfo[] gameinfoList) {

    }

    @Override
    public void disableGame(int gameListIndex) {

    }

    @Override
    public void enableGame(int gameListIndex) {

    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }
}
