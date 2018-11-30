package thePollerExpress.views.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shared.models.EndGameResult;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.models.PlayerEndResult;
import thePollerExpress.presenters.game.ChatPresenter;
import thePollerExpress.presenters.game.EndGamePresenter;
import thePollerExpress.presenters.game.interfaces.IEndGamePresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IEndGameView;

public class EndGameFragment extends Fragment implements IEndGameView
{

    TextView winnerName;
    ListView recyclerView;
    Button findNewGame;
    IEndGamePresenter presenter;
    EndGameResult endGameResult;
    //ListVie.LayoutManager layoutManager;
    MyAdapter adapter;
    EndGameResult gameResult;

    public void setGameResult(EndGameResult gameResult) {
        this.gameResult = gameResult;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_end_game, container, false);
        presenter = new EndGamePresenter(this);
        // wire up the widgets
        winnerName = v.findViewById(R.id.winner);

        findNewGame = v.findViewById(R.id.find_new_game_button);

        // listen for find new game button to be clicked
        findNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.findNewGame();
            }
        });

        //set up the recycler view
        recyclerView = v.findViewById( R.id.end_game_recycler_view );
        adapter = new EndGameFragment.MyAdapter(presenter.getEndGameResult());
        recyclerView.setAdapter(adapter);



        return v;
    }

    @Override
    public void displayError(String errorMessage)
    {

    }

    @Override
    public void changeView(IPollerExpressView view)
    {

    }


    private class MyAdapter extends BaseAdapter
    {
        private List<PlayerEndResult> tempList;
        public MyAdapter(List<PlayerEndResult> results)
        {
            tempList = results;
        }
        @Override
        public int getCount()
        {
            return tempList.size();
        }

        @Override
        public Object getItem(int i)
        {
            return tempList.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        // override other abstract methods here

        @Override
        public View getView(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.player_end_item_view, container, false);
            }

            View itemView = convertView;
            TextView username;
            TextView total;
            TextView destination;
            TextView lost;
            TextView longest;
            TextView bonus;

            PlayerEndResult playerEndResult = (PlayerEndResult) getItem(position);
            username = (TextView) itemView.findViewById(R.id.user_name);
            total = (TextView) itemView.findViewById(R.id.total);
            destination = (TextView) itemView.findViewById(R.id.destination_points);
            lost = (TextView) itemView.findViewById(R.id.destination_mallus);
            lost.setTextColor(getContext().getColor(R.color.garishPink));
            longest = itemView.findViewById(R.id.longest_route);
            bonus = itemView.findViewById(R.id.bonus);

            username.setText(playerEndResult.username);
            total.setText(playerEndResult.total);
            destination.setText(playerEndResult.completedDestination);
            lost.setText(playerEndResult.incompleteDestination);
            longest.setText(playerEndResult.longestRoute);
            bonus.setText(playerEndResult.bonus);

            return convertView;
        }
    }



}
