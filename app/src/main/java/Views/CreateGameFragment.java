package Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs340.pollerexpress.R;
import presenter.CreateGamePresenter;
import presenter.ICreateGamePresenter;

public class CreateGameFragment extends Fragment implements ICreateGameView {

    ICreateGamePresenter createGamePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       createGamePresenter = new CreateGamePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_create_game, container, false);

        return v;
    }
}
