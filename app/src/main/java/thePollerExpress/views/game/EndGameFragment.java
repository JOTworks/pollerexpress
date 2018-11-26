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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shared.models.EndGameResult;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.ChatPresenter;
import thePollerExpress.presenters.game.EndGamePresenter;
import thePollerExpress.presenters.game.interfaces.IEndGamePresenter;
import thePollerExpress.views.game.interfaces.IEndGameView;

public class EndGameFragment extends Fragment implements IEndGameView {

    TextView winnerName;
    RecyclerView recyclerView;
    Button findNewGame;
    IEndGamePresenter presenter;
    EndGameResult endGameResult;
    RecyclerView.LayoutManager layoutManager;
//    Adapter adapter;
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
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
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
//        recyclerView = v.findViewById(R.id.end_game_recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        adapter = new EndGameFragment.Adapter();
//        recyclerView.setAdapter(adapter);
//
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

        return v;
    }


//    public class Adapter extends RecyclerView.Adapter<ViewHolder> {
//
//        private ArrayList tempList = new ArrayList();
//
//        public Adapter(ArrayList tempList) {
//            this.tempList = tempList;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//            // create a new view
//            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
//                    .inflate(R.layout.player_end_item_view, viewGroup, false);
//
//            // create and return a view holder
//            ViewHolder viewHolder = new ViewHolder(v);
//            return viewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//            viewHolder.bind();
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return 0;
//        }
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        private TextView username;
//        private TextView total;
//        private TextView gained;
//        private TextView lost;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            username = (TextView) itemView.findViewById(R.id.username);
//            total = (TextView) itemView.findViewById(R.id.total);
//            gained = (TextView) itemView.findViewById(R.id.gained);
//            lost = (TextView) itemView.findViewById(R.id.lost);
//        }
//
//        public void bind(PlayerEndResult playerEndResult) {
//            username.setText(playerEndResult.username);
//            total.setText(playerEndResult.total);
//            gained.setText(playerEndResult.gained);
//            lost.setText(playerEndResult.lost);
//        }
//    }
//
//    public class PlayerEndResult {
//        String total;
//        String gained;
//        String lost;
//        String username;
//
//        public PlayerEndResult(String total, String gained, String lost, String username) {
//            this.total = total;
//            this.gained = gained;
//            this.lost = lost;
//            this.username = username;
//        }
//    }

}
