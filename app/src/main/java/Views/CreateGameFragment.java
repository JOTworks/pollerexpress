package Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

        //----------------------------number of players spinner-------------------------------------
        Spinner numPlayerSpinner = (Spinner) v.findViewById(R.id.num_players_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.num_players_spinner_array, R.layout.spinner_poller_theme);
        adapter.setDropDownViewResource(R.layout.spinner_poller_theme);
        numPlayerSpinner.setAdapter(adapter);

        numPlayerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createGamePresenter.setNumOfPlayers(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO: the create game button is going to need to be unclickable unless selections have been made in the spinners, unless there is a defaul...
            }
        });
        //----------------------------User Color Spinner--------------------------------------------

        Spinner playerColorSpinner = (Spinner) v.findViewById(R.id.player_color_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.player_color_spinner_array, R.layout.spinner_poller_theme);
        adapter2.setDropDownViewResource(R.layout.spinner_poller_theme);
        playerColorSpinner.setAdapter(adapter2);

        playerColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                createGamePresenter.setUserColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO: the create game button is going to need to be unclickable unless selections have been made in the spinners, unless there is a defaul...
            }
        });

        //--------------------------------------------------------------------------------------------------


        //TODO: onclick listener for button


        return v;
    }
}
