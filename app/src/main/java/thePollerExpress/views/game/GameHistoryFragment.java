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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cs340.pollerexpress.R;
import thePollerExpress.Development.MethodCallerFragment;
import thePollerExpress.presenters.game.GameHistoryPresenter;
import thePollerExpress.presenters.game.interfaces.IGameHistoryPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IGameHistoryView;

public class GameHistoryFragment extends Fragment implements IGameHistoryView {

    Button chatViewButton;
    Button gameHistoryViewButton;
    IGameHistoryPresenter GHP;
    ArrayList<String> actionList;
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
        View v = inflater.inflate(R.layout.fragment_game_history, container, false);
        GHP = new GameHistoryPresenter(this);
        // wire up the widgets
        chatViewButton = (Button) v.findViewById(R.id.chat_view_button);
        gameHistoryViewButton = (Button) v.findViewById(R.id.game_history_view_button);

        //set up the recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.game_history_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // listen for run button to be clicked
        chatViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GHP.PressedChatViewButton();
            }
        });

        // listen for run button to be clicked
        gameHistoryViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GHP.PressedGameHistoryViewButton();
            }
        });


        actionList = GHP.getHistoryItems();
        adapter = new Adapter(actionList);
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        return v;
    }

    public void changeToGameHistoryView(){
        FragmentManager fm = getFragmentManager();
        Fragment fragment = new MethodCallerFragment();

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.chat_history_fragment_container, fragment);
        ft.commit();
        fm.popBackStack();
    }

    @Override
    public void displayGameHistoryItems(String action)
    {

        // set up the adapter, which needs a list

        actionList.add(action);
        adapter.notifyDataSetChanged();

    }

    public void displayError(String message)
    {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeView(IPollerExpressView view) {
        // do nothing
    }

    public class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private ArrayList<String> itemList;

        public Adapter(ArrayList<String> item_list) {
            itemList = item_list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // create a new view
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.history_item_view, parent, false);

            //we need to create and return a result view holder
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

            // get what you want to bind and bind it.
            String item = itemList.get(i);
            viewHolder.bind(item);
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView historyItem;

        // wire up the view holder
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            historyItem = (TextView) itemView.findViewById(R.id.history_item);
        }

        // bind the view to the viewholder
        public void bind(String item)
        {
            historyItem.setText(item);
        }
    }

}
