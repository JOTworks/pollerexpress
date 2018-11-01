package thePollerExpress.Development;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shared.models.Command;
import com.shared.models.GameInfo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodBuilder;
import thePollerExpress.Development.MethodCaller;
import thePollerExpress.communication.ClientCommunicator;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.ChatFragment;
import thePollerExpress.views.setup.SetupViewAdapters.GameSelectAdapter;

/**
 * Abby
 * The class is responsible for finding client classes
 * and running methods. We are using it for testing purposes.
 * This class will have runtime dependencies but no
 * type dependencies because of reflection.
 */
public class MethodCallerFragment extends Fragment implements IPollerExpressView {

    Button runMethodsButton;
    Button chatViewButton;
    EditText methods;
    ArrayList<String> results = new ArrayList<>();
    MethodCaller methodCaller = new MethodCaller(this);
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_method_caller, container, false);

        // wire up the widgets
        methods = (EditText) v.findViewById(R.id.methods);
        runMethodsButton = (Button) v.findViewById(R.id.run_methods_button);
        chatViewButton = (Button) v.findViewById(R.id.chat_view_button);
        //set up the recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.method_caller_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // listen for run button to be clicked
        runMethodsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    // These are the commands that we want to run.
                    //Command[] commandList = MethodBuilder.parse(methods.getText().toString());
                    // We run the commands and get back an array list of results
                    //results = methodCaller.execute(commandList);

                    //jack is getting rid of reflection for now, its hard, instead hardcodding funtions
                    String method = methods.getText().toString();
                    String[] splitArray;
                    try {
                        splitArray = method.split("\\s+");
                    } catch (PatternSyntaxException ex) {
                        splitArray = new String[1];
                        splitArray[0] = method;
                    }
                    results = methodCaller.parse(splitArray[0],splitArray );

                    // set up the adapter, which needs a list
                    adapter = new Adapter(results);
                    recyclerView.setAdapter(adapter);

                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);


                } catch(Exception e) {

                    //display the error message
                    results.add(e.getMessage());

                    // set up the adapter, which needs a list
                    adapter = new Adapter(results);
                    recyclerView.setAdapter(adapter);

                    layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);

                }
            }
        });

        // listen for run button to be clicked
        chatViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                //Fragment createGameFragment = fm.findFragmentById(R.rotation.fragment_create_game);
                Fragment fragment = new ChatFragment();

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.chat_history_fragment_container, fragment);
                ft.commit();
                fm.popBackStack();
            }
        });


//        // set up the adapter, which needs a list
//        adapter = new Adapter(results);
//        recyclerView.setAdapter(adapter);
//
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

        return v;
    }

    @Override
    public void displayError(String errorMessage) {

    }

    @Override
    public void changeView(IPollerExpressView view) {

    }

    public class Adapter extends RecyclerView.Adapter<ResultViewHolder> {

        private ArrayList<String> results = new ArrayList<>();

        public Adapter(ArrayList<String> method_results) {
            results = method_results;
        }

        @Override
        public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.method_result_view, parent, false);

            //we need to create and return a result view holder
            ResultViewHolder viewHolder = new ResultViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ResultViewHolder resultViewHolder, int i) {

            // get what you want to bind and bind it.
            String result = results.get(i);
            resultViewHolder.bind(result);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }

    private class ResultViewHolder extends RecyclerView.ViewHolder {

        private TextView method_result;

        // wire up the view holder
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            method_result = (TextView) itemView.findViewById(R.id.method_result);
        }

        // bind the view to the viewholder
        public void bind(String result) {

            method_result.setText(result);
        }
    }
}
