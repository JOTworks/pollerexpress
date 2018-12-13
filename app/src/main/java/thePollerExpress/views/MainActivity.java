package thePollerExpress.views;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shared.models.Game;
import com.shared.models.GameInfo;

import cs340.pollerexpress.R;
import thePollerExpress.communication.PollerExpress;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.MainPresenter;
import thePollerExpress.views.game.GameFragment;
import thePollerExpress.views.game.MapView;
import thePollerExpress.views.setup.LoginFragment;

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

        Fragment fragment = new GameFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
        fm.popBackStack();
    }
}
