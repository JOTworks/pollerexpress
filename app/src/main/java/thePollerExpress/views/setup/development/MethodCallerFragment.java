package thePollerExpress.views.setup.development;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import cs340.pollerexpress.R;

/**
 * The class is responsible for finding client classes
 * and running commands. We are using it for testing purposes.
 * This class will have runtime dependencies but no
 * type dependencies because of reflection.
 */
public class MethodCallerFragment extends Fragment {

    Button runMethodsButton;
    EditText methods;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_methodcaller, container, false);

        runMethodsButton = (Button) v.findViewById(R.id.run_methods_button);
        runMethodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                } catch(Exception e) {

                }
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
