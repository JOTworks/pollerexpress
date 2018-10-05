package Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pollerexpress.models.GameInfo;

import cs340.pollerexpress.R;
import presenter.GameSelectionPresenter;
import presenter.IGameSelectionPresenter;
import presenter.LoginPresenter;

public class GameSelectionFragment extends Fragment implements IGameSelectionView {

    IGameSelectionPresenter gameSelectionPresenter;

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
}
