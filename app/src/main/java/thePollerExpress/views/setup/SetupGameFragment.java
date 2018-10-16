package thePollerExpress.views.setup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cs340.pollerexpress.R;

public class SetupGameFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: create a presenter for setupGameFragment
        //loginPresenter = new LoginPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_setup_game, container, false);

        FragmentManager fm = getFragmentManager();

        Fragment fragment = new GameSelectionFragment();
        fm.beginTransaction()
                .add(R.id.right_side_fragment_container, fragment)
                .commit();

        return v;
    }
}
