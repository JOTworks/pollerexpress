package thePollerExpress.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.shared.models.Player;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodCallerFragment;
import thePollerExpress.views.game.interfaces.IGameView;
import thePollerExpress.views.game.interfaces.IPlayerView;
import thePollerExpress.views.setup.GameSelectionFragment;

public class PlayerFragment extends Fragment implements IPlayerView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_player, container, false);


        return v;
    }

    @Override
    public void renderPlayer(Player p) {

    }
}
