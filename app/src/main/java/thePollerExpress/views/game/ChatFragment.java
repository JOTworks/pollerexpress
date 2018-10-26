package thePollerExpress.views.game;

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
import android.widget.Toast;

import java.util.ArrayList;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodCaller;
import thePollerExpress.Development.MethodCallerFragment;
import thePollerExpress.presenters.game.ChatPresenter;
import thePollerExpress.presenters.game.interfaces.IChatPresenter;
import thePollerExpress.views.game.interfaces.IChatView;
import thePollerExpress.views.setup.SetupGameFragment;

/**
 * Abby
 * The class is responsible for finding client classes
 * and running methods. We are using it for testing purposes.
 * This class will have runtime dependencies but no
 * type dependencies because of reflection.
 */
public class ChatFragment extends Fragment implements IChatView {

    Button sendChatButton;
    Button chatViewButton;
    Button devViewButton;
    EditText chatMessage;
    ArrayList<String> results = new ArrayList<>();
    IChatPresenter CP = new ChatPresenter(this);
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
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        // wire up the widgets
        chatMessage = (EditText) v.findViewById(R.id.chat_message);
        sendChatButton = (Button) v.findViewById(R.id.send_chat_button);
        chatViewButton = (Button) v.findViewById(R.id.chat_view_button);
        devViewButton = (Button) v.findViewById(R.id.dev_view_button);

        //set up the recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.method_caller_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // listen for run button to be clicked
        sendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CP.PressedSendButton(chatMessage.getText().toString());
            }
        });

        // listen for run button to be clicked
        chatViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CP.PressedChatViewButton();
            }
        });

        // listen for run button to be clicked
        devViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CP.PressedDevViewButton();
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

    public void changeToDevView(){
        FragmentManager fm = getFragmentManager();
        //Fragment createGameFragment = fm.findFragmentById(R.id.fragment_create_game);
        Fragment fragment = new MethodCallerFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.chat_history_fragment_container, fragment);
        ft.commit();
        fm.popBackStack();
    }

    public void displayMessage(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
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
