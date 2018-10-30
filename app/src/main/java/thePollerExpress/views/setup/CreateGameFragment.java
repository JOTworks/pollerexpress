package thePollerExpress.views.setup;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Observer;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.setup.CreateGamePresenter;
import thePollerExpress.presenters.setup.ICreateGamePresenter;
import thePollerExpress.views.IPollerExpressView;

public class CreateGameFragment extends Fragment implements ICreateGameView
{

    ICreateGamePresenter createGamePresenter;

    Button createGameButton;
    EditText gameName;
    //-------------------data for create game--------------------------
    String numOfPlayers = "2";
    String userColor = "red";

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
                numOfPlayers = parent.getItemAtPosition(position).toString();
                createGamePresenter.setNumOfPlayers(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO: the create game button is going to need to be unclickable unless selections have been made in the spinners, unless there is a default...
            }
        });
        //----------------------------User Color Spinner--------------------------------------------

        final Spinner playerColorSpinner = (Spinner) v.findViewById(R.id.player_color_spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.player_color_spinner_array, R.layout.spinner_poller_theme);
        adapter2.setDropDownViewResource(R.layout.spinner_poller_theme);
        playerColorSpinner.setAdapter(adapter2);

        playerColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userColor = parent.getItemAtPosition(position).toString();
                createGamePresenter.setUserColor(userColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                //TODO: the create game button is going to need to be unclickable unless selections have been made in the spinners, unless there is a defaul...
            }
        });

        //--------------------------------------------------------------------------------------------------
        createGameButton = (Button) v.findViewById(R.id.create_game_button);

        createGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGamePresenter.onCreateGameClicked(numOfPlayers, userColor);
                //Toast.makeText(getContext(), "It's working!", Toast.LENGTH_LONG).show(); //TODO: remove toast
            }
        });

        //TODO: onclick listener for button
        //---------------------------------------------------------------------------------------------------
        //input listeners...
        gameName = (EditText) v.findViewById(R.id.game_name);
        createGamePresenter.setGameName(gameName.getText().toString());
        gameName.addTextChangedListener(new TextWatcher() {
            private boolean _ignore = false;
           @Override
           public void afterTextChanged(Editable s)
           {
               if(_ignore) return;

               _ignore = true;
               createGamePresenter.setGameName(gameName.getText().toString());
               _ignore = false;
           }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //pass validation to Create Game Fragment
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }
        });

        return v;
    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeToSetupGameView() {

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = new SetupGameFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        fragmentManager.popBackStack();

    }

    @Override
    public void changeToLobbyView()
    {

        FragmentManager fragmentManager = getFragmentManager();
        //Fragment createGameFragment = fragmentManager.findFragmentById(R.id.fragment_create_game);
        Fragment fragment = new LobbyFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.right_side_fragment_container, fragment);
        fragmentTransaction.commit();



    }

    @Override
    public void changeView(IPollerExpressView view)
    {
        //changeToLobbyView();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.right_side_fragment_container, (Fragment) view);
        fragmentTransaction.commit();//*/
    }
}
