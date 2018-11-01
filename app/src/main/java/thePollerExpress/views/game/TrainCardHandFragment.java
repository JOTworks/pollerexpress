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

import java.util.ArrayList;

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

        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("blue");
        colorList.add("yellow");
        colorList.add("pink");
        colorList.add("orange");

        displayHand(colorList);

        return v;
    }

    /**
     * updates the recycler view
     * @param arrayList new strings to display.
     */
    public void displayHand(ArrayList<String> arrayList) {

        ArrayList<String> cardList = arrayList;
        Adapter adapter = new Adapter(cardList);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }

    public class Adapter extends RecyclerView.Adapter<CardViewHolder> {

        ArrayList<String> cardList;

        public Adapter(ArrayList<String> list) {
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

            String trainCardStr = cardList.get(i);
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
