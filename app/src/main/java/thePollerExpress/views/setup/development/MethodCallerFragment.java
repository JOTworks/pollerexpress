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
import android.widget.TextView;

import com.shared.models.Command;

import org.w3c.dom.Text;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodBuilder;
import thePollerExpress.Development.MethodCaller;

/**
 * The class is responsible for finding client classes
 * and running commands. We are using it for testing purposes.
 * This class will have runtime dependencies but no
 * type dependencies because of reflection.
 */
public class MethodCallerFragment extends Fragment {

    Button runMethodsButton;
    EditText methods;
    TextView results;
    MethodCaller methodCaller = new MethodCaller(this);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_methodcaller, container, false);

        methods = (EditText) v.findViewById(R.id.methods);

        runMethodsButton = (Button) v.findViewById(R.id.run_methods_button);

        results = (TextView) v.findViewById(R.id.results);

        runMethodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    //returns commands, not strings
                    Command[] commandList = MethodBuilder.parse(methods.getText().toString());

                    // for now, returns a string
//                    results.setData(methodCaller.execute(commandList));
                    results.setText(methodCaller.execute(commandList));

                } catch(Exception e) {
//                    results.setData(e.getMessage());
                    results.setText(e.getMessage());
                }
            }
        });



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
