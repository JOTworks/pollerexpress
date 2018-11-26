package thePollerExpress.views.game;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.TrainCardHandPresenter;
import thePollerExpress.presenters.game.interfaces.ITrainCardHandPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.ITrainCardHandView;

/**
 * This class is responsible or displaying a player's train card hand.
 * It is pretty much just a LR recycler view of images.
 * Or at least, that's what it will be by the end.
 */
public class TrainCardHandFragment extends Fragment implements ITrainCardHandView {

    ImageView cardView;
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

        cardView = (ImageView) recyclerView.findViewById(R.id.train_card_image_item);

        List<TrainCard> cardList = presenter.get();
        mAdapter = new Adapter(cardList);
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        return v;
    }

    private Drawable getFromCard(TrainCard card)
    {
        switch(card.getColor())
        {
            case RED:
                return getResources().getDrawable(R.drawable.red_train_card);
            case BLUE:
                return getResources().getDrawable(R.drawable.blue_train_card);
            case BLACK:
                return getResources().getDrawable(R.drawable.black_train_card);
            case YELLOW:
                return getResources().getDrawable(R.drawable.yellow_train_card);
            case GREEN:
                return getResources().getDrawable(R.drawable.green_train_card);
            case WHITE:
                return getResources().getDrawable(R.drawable.white_train_card);
            case ORANGE:
                return getResources().getDrawable(R.drawable.orange_train_card);
            case PURPLE:
                return getResources().getDrawable(R.drawable.purple_train_card);
            case RAINBOW:
                return getResources().getDrawable(R.drawable.rainbow_train_car);
        }
        return null;//TODO replace with blank
    }
    /**
     * updates the recycler view
     */
    public void displayHand() {

        /*This line of code takes the place of making a new adapter.
        * This is much more efficient than making a new adapter since
        * every time the recycler view needs to be updated.*/
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void displayError(String errorMessage) {
        android.widget.Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeView(IPollerExpressView view) {


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

            //String trainCardStr = cardList.get(i).getColorAsString();
            //cardViewHolder.bind(trainCardStr + ":)");
            cardViewHolder.bind(cardList.get(i));
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        private TextView trainCardText;
        private ImageView trainCard;

        //wire up the view holder
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            //trainCardText = (TextView) itemView.findViewById(R.id.train_card_item);
            trainCard = (ImageView) itemView.findViewById(R.id.train_card_image_item);
        }

//        public void bind(String trainCardStr) {
//            trainCardText.setText(trainCardStr);
//        }
        public void bind(TrainCard card) { trainCard.setBackground(getFromCard(card)); }
    }


}
