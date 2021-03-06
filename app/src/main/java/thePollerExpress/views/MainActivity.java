package thePollerExpress.views;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import cs340.pollerexpress.R;
import thePollerExpress.communication.PollerExpress;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.MainPresenter;
import thePollerExpress.views.game.GameFragment;
import thePollerExpress.views.setup.LobbyFragment;
import thePollerExpress.views.setup.LoginFragment;
import thePollerExpress.views.setup.SetupGameFragment;

public class MainActivity extends AppCompatActivity implements ISpecialView
{

    MainPresenter presenter;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        presenter = new MainPresenter(this);

        setContentView(R.layout.activity_main);
        ClientData.getInstance().set(new PollerExpress(this));
        fm = getSupportFragmentManager();

        Fragment fragment = new LoginFragment();
        //ClientData.getInstance().setGame(new Game(new GameInfo("something", 1) ) );
        //fragment = new MapView();
        //fragment = new SetupGameFragment();
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }


    void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }


    @Override
    public void goToGame()
    {

        GameFragment fragment = new GameFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
        fm.popBackStack();
        Toast.makeText(this,"Resynced with server",Toast.LENGTH_SHORT);
    }

    @Override
    public void goToLobby()
    {
        SetupGameFragment frag = new SetupGameFragment();

            Bundle args = new Bundle();
            args.putBoolean("lobby", true);
            frag.setArguments(args);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, frag);
            ft.commit();
            fm.popBackStack();

    }
    @Override
    public void deleteFragments()
    {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, new LoginFragment());
        ft.commit();
        fm.popBackStack();
    }
}
