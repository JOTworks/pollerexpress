package Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cs340.pollerexpress.R;
import presenter.ILoginPresenter;
import presenter.LoginPresenter;

import static android.widget.Toast.LENGTH_LONG;

public class LoginFragment extends Fragment implements ILoginView {

    EditText userNameText;
    EditText passwordText;
    EditText confirmText;

    Button login_button;
    Button register_button;

    ILoginPresenter loginPresenter;
    /*---------------OverRides----------------------*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);



        userNameText = (EditText)v.findViewById(R.id.user_name);
        passwordText = (EditText)v.findViewById(R.id.password);
        confirmText = (EditText)v.findViewById(R.id.confirm);

        login_button = (Button) v.findViewById(R.id.login_button);
        register_button = (Button) v.findViewById(R.id.register_button);

        //add listeners to the EditText to tell the presenter if they are empty or not


        Button mlogin_button = (Button) login_button;
        mlogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.logIn(); }
        });

        Button mregister_button = (Button) register_button;
        mregister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                loginPresenter.register();
            }
        });

        userNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                loginPresenter.updateLogin(userNameText.getText().toString(),passwordText.getText().toString());
                loginPresenter.updateRegister(userNameText.getText().toString(),passwordText.getText().toString(),confirmText.getText().toString());
            }
        });

        passwordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                loginPresenter.updateLogin(userNameText.getText().toString(),passwordText.getText().toString());
                loginPresenter.updateRegister(userNameText.getText().toString(),passwordText.getText().toString(),confirmText.getText().toString());
            }
        });

        confirmText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                loginPresenter.updateRegister(userNameText.getText().toString(),passwordText.getText().toString(),confirmText.getText().toString());
            }
        });

        return v;
    }

    @Override
    public void disableLogin() {
        login_button.setEnabled(false);
    }

    @Override
    public void enableLogin() {
        login_button.setEnabled(true);
    }

    @Override
    public void disableRegister() {
        register_button.setEnabled(false);
    }

    @Override
    public void enableRegister() {
        register_button.setEnabled(true);
    }

    @Override
    public void changeToGameSelectionView() {
        // TODO: 10/1/2018 figure how to change fravements
    }

    @Override
    public void displayError(String errorMessage) {

    }
}
