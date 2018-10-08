package Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pollerexpress.models.GameInfo;

import cs340.pollerexpress.R;
import presenter.GameSelectionPresenter;
import presenter.IGameSelectionPresenter;
import presenter.LoginPresenter;

public class GameSelectionFragment extends Fragment implements IGameSelectionView {

    IGameSelectionPresenter gameSelectionPresenter;

    Button createGameButton;

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





        //---------------------Create Game Button---------------------------------------------------
        createGameButton = (Button) v.findViewById(R.id.create_game_button);

        Button mCreateGameButton = (Button) createGameButton;
        mCreateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: pull this to a method and give control to the presenter
                FragmentManager fragmentManager = getFragmentManager();
                //Fragment createGameFragment = fragmentManager.findFragmentById(R.id.fragment_create_game);
                Fragment createGameFragment = new CreateGameFragment();

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
}
