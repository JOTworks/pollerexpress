package Views;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import cs340.pollerexpress.R;
import presenter.ILoginPresenter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: make all fragment managers fm for consistency
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragment = new LoginFragment();
        //fragment = new SetupGameFragment();
        fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }


    void startActivity(Class aClass) {
        Intent intent = new Intent(this, aClass);
        startActivity(intent);
    }

}
