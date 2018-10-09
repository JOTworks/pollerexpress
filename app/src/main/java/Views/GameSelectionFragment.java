package Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pollerexpress.models.GameInfo;

import java.io.Serializable;
import java.util.List;

import Views.recycleViewAdapters.GameSelectAdapter;
import cs340.pollerexpress.ClientData;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        gameSelectionPresenter = new GameSelectionPresenter(this);
        View v = inflater.inflate(R.layout.fragment_game_selection, container, false);

        mGameRecyclerView = (RecyclerView) v.findViewById(R.id.game_selection_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mGameRecyclerView.setHasFixedSize(true);

        // specify an adapter (see also next example)
        mAdapter = new GameSelectAdapter(gameSelectionPresenter.getGameList(), gameSelectionPresenter);
        mGameRecyclerView.setAdapter(mAdapter);

        mLayoutManager = new LinearLayoutManager(getContext());
        mGameRecyclerView.setLayoutManager(mLayoutManager);
        mGameRecyclerView.setItemAnimator(null);



        //---------------------Create Game Button---------------------------------------------------
        createGameButton = (Button) v.findViewById(R.id.create_game_button);

        Button mCreateGameButton = (Button) createGameButton;
        mCreateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSelectionPresenter.createGame();
            }
        });

        return v;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d("GameSelectionFragment", "onDestory");
        ClientData.getInstance().deleteObserver(gameSelectionPresenter);
    }

    @Override
    public void changeCreateGameView()
    {
        //ClientData.getInstance().deleteObserver(gameSelectionPresenter);
        Log.d("GameSelect", "change o CreateGameView");
        FragmentManager fm = getFragmentManager();
        //Fragment createGameFragment = fm.findFragmentById(R.id.fragment_create_game);
        Fragment createGameFragment = new CreateGameFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.right_side_fragment_container, createGameFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void renderGames(List<GameInfo> gameinfoList)
    {
        mAdapter.notifyItemInserted(-1);
        //mAdapter.notifyDataSetChanged();
    }

    @Override
    public void disableGame(int gameListIndex) {

    }

    @Override
    public void enableGame(int gameListIndex)
    {

    }

    @Override
    public void changeToLobbyView() {
        //ClientData.getInstance().deleteObserver(gameSelectionPresenter);//see what this does
        //Log.d("ChangeToLobbyView", "deleted an observer");
        FragmentManager fragmentManager = getFragmentManager();
        //Fragment createGameFragment = fragmentManager.findFragmentById(R.id.fragment_create_game);
        Fragment fragment = new LobbyFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.right_side_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

}
