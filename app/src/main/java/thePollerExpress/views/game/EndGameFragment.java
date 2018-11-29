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
import com.shared.models.Player;
import com.shared.models.PlayerScore;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.ChatPresenter;
import thePollerExpress.presenters.game.EndGamePresenter;
import thePollerExpress.presenters.game.interfaces.IEndGamePresenter;
import thePollerExpress.views.game.interfaces.IEndGameView;

public class EndGameFragment extends Fragment implements IEndGameView {

    TextView winnerName;
    RecyclerView recyclerView;
//    Button findNewGame;
    IEndGamePresenter presenter;
    EndGameResult endGameResult;
    RecyclerView.LayoutManager layoutManager;
    Adapter adapter;
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

        View v = inflater.inflate(R.layout.fragment_end_game, container, false);
        presenter = new EndGamePresenter(this);

        winnerName = v.findViewById(R.id.winner);
        winnerName.setText("Insert The Winner's Name");

//        findNewGame = v.findViewById(R.id.find_new_game_button);
//        findNewGame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presenter.findNewGame();
//            }
//        });

        recyclerView = v.findViewById(R.id.end_game_recycler_view);
        recyclerView.setHasFixedSize(true);

        adapter = new EndGameFragment.Adapter(endGameResult.getPlayerScores());
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }


    public class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private List<PlayerScore> playerScores;

        public Adapter(List list) {
            playerScores = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.player_end_item_view, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            PlayerScore playerScore = playerScores.get(i);
            viewHolder.bind(playerScore);

        }

        @Override
        public int getItemCount() {
            return playerScores.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView total;
        private TextView gained;
        private TextView lost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            total = (TextView) itemView.findViewById(R.id.total);
            gained = (TextView) itemView.findViewById(R.id.gained);
            lost = (TextView) itemView.findViewById(R.id.lost);
        }

        public void bind(PlayerScore playerScore) {
            username.setText(playerScore.getPlayerName());
            total.setText(playerScore.getTotalPoints());
            gained.setText(playerScore.getDestinationPoints());
            lost.setText(playerScore.getUnreachedDestinationPoints());
        }
    }
}
