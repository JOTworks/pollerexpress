package Views;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import cs340.pollerexpress.R;

import static android.widget.Toast.LENGTH_LONG;

public class LoginFragment extends Fragment implements ILoginView {

    EditText userNameText;
    EditText passwordText;

    Button login_button;
    Button register_button;


    /*---------------OverRides----------------------*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);



        userNameText = (EditText)v.findViewById(R.id.user_name);
        passwordText = (EditText)v.findViewById(R.id.password);

        login_button = (Button) v.findViewById(R.id.login_button);
        register_button = (Button) v.findViewById(R.id.register_button);

        //add listeners to the EditText to tell the presenter if they are empty or not

        Button mlogin_button = (Button) login_button;
        mlogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tell presenter
            }
        });

        Button mregister_button = (Button) register_button;
        mregister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tell presenter
            }
        });


        return v;
    }

    public void disableButton(Button button){

    }

    @Override
    public void onResume(){
        super.onResume();
        //artifact from jacks 240 project, not sure what it does but we may need to override this function at somepoint?
        /*
        if(Data.getToken()!=null){
            ((MainActivity) getActivity()).switchToMap();
        }
        */
    }
}
