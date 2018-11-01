package thePollerExpress.views.game;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.TrainCardHandPresenter;
import thePollerExpress.presenters.game.interfaces.ITrainCardHandPresenter;
import thePollerExpress.views.game.interfaces.ITrainCardHandView;

/**
 * This class is responsible or displaying the player's train card hand.
 * It is pretty much just a LR recycler view of images.
 * Or at least, that's what it will be by the end.
 */
public class TrainCardHandFragment extends Fragment implements ITrainCardHandView {

    TextView cardView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ITrainCardHandPresenter presenter = new TrainCardHandPresenter(this);
    Adapter mAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_train_card_hand, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.train_card_recycler_view);

        recyclerView.setHasFixedSize(true);

        cardView = (TextView) recyclerView.findViewById(R.id.train_card_item);

        List<TrainCard> cardList = presenter.get();
        mAdapter = new Adapter(cardList);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }

    /**
     * updates the recycler view
     */
    public void displayHand() {

        mAdapter.notifyDataSetChanged();
    }

    public class Adapter extends RecyclerView.Adapter<CardViewHolder> {
        List<TrainCard> cardList;

        public Adapter(List<TrainCard> list) {
            cardList = list;
        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int i) {

            // create a new view
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.train_card_view, parent, false);

            CardViewHolder viewHolder = new CardViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {

            String trainCardStr = cardList.get(i).getColorAsString();
            cardViewHolder.bind(trainCardStr);
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView trainCard;

        //wire up the view holder
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            trainCard = (TextView) itemView.findViewById(R.id.train_card_item);
        }

        public void bind(String trainCardStr) {
            trainCard.setText(trainCardStr);
        }
    }


}
