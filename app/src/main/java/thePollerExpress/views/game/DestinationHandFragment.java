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

import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.DestinationPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IDestinationPresenter;
import thePollerExpress.views.game.interfaces.IDestinationView;

public class DestinationHandFragment extends Fragment implements IDestinationView
{


        //**private GamePlayPresenter gamePlayPresenter;

        private RecyclerView mGameRecyclerView;
        private RecyclerView.LayoutManager mLayoutManager;
        private Adapter mAdapter;
        private IDestinationPresenter presenter;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //**gamePlayPresenter = new gamePlayPresenter(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            this.presenter = new DestinationPresenter(this);
            View v = inflater.inflate(R.layout.fragment_destination_hand, container, false);


            mGameRecyclerView = (RecyclerView) v.findViewById(R.id.player_recycler_view);
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            mGameRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mGameRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new Adapter(presenter.get());
            mGameRecyclerView.setAdapter( mAdapter);
            // specify an adapter (see also next example)
            //**Game game = lobbyPresenter.getGame();
            //**mAdapter = new CardAdapter(game.));
            //**mGameRecyclerView.setAdapter(mAdapter);
            //---------------------Start Game Button---------------------------------------------------
            return v;
        }


        @Override
        public void displayError(String errorMessage) {

        }

        @Override
        public void changeView(IPollerExpressView view) {

        }


    public class Adapter extends RecyclerView.Adapter<CardViewHolder> {

        List<DestinationCard> cardList;

        public Adapter(List<DestinationCard> list) {
            cardList = list;
        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int i) {

            // create a new view
            ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.destination_card_reycle_view_item, parent, false);

            CardViewHolder viewHolder = new CardViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int i) {

            String trainCardStr = cardList.get(i).print();
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
            trainCard = (TextView) itemView.findViewById(R.id.destination_hand_item);
        }

        public void bind(String trainCardStr) {
            trainCard.setText(trainCardStr);
        }
    }

}
