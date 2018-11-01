package thePollerExpress.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.BankPresenter;

public class BankFragment extends Fragment {


    TextView faceup0;
    TextView faceup1;
    TextView faceup2;
    TextView faceup3;
    TextView faceup4;
    TextView trainCardDeck;
    TextView destinationCardDeck;

    BankPresenter bankPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankPresenter = new BankPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bank, container, false);



        faceup0 = (TextView) v.findViewById(R.id.faceup_card_0);
        faceup1 = (TextView) v.findViewById(R.id.faceup_card_1);
        faceup2 = (TextView) v.findViewById(R.id.faceup_card_2);
        faceup3 = (TextView) v.findViewById(R.id.faceup_card_3);
        faceup4 = (TextView) v.findViewById(R.id.faceup_card_4);
        trainCardDeck =  (TextView) v.findViewById(R.id.train_card_deck);
        destinationCardDeck =  (TextView) v.findViewById(R.id.destination_card_deck);
        return v;
    }

    public void render(){
        Toast.makeText(getContext(), "updated!", Toast.LENGTH_LONG).show();
    }
}
