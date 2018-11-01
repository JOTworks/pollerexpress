package thePollerExpress.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.shared.models.cardsHandsDecks.DestinationCard;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.DestinationSelectionPresenter;
import thePollerExpress.presenters.game.interfaces.IDestinationSelectionPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IDestinationSelectionView;

public class DestinationSelectionFragment extends Fragment implements IDestinationSelectionView, IPollerExpressView{

    IDestinationSelectionPresenter destinationSelectionPresenter;
    TextView destinationTextView0;
    TextView destinationTextView1;
    TextView destinationTextView2;
    Button discardButton;
    Button viewButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        destinationSelectionPresenter = new DestinationSelectionPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_destination_selection, container, false);

        destinationTextView0 = (TextView)v.findViewById(R.id.destination_text_view_0);
        destinationTextView1 = (TextView)v.findViewById(R.id.destination_text_view_1);
        destinationTextView2 = (TextView)v.findViewById(R.id.destination_text_view_2);
        discardButton = (Button)v.findViewById(R.id.destination_discard_button);
        viewButton = (Button)v.findViewById(R.id.destination_view_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderCards(ClientData.getInstance().getUser().getDestCardOptions().getDestinationCards());
            }
        });

        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationSelectionPresenter.discardButtonPressed();
            }
        });

        return v;
    }

    @Override
    public void renderCards(List<DestinationCard> cards) {
        //todo: not done, in progreessssss
        if(cards==null){
            destinationTextView0.setText("destCard is null");
            //}//else if(destCards.get(0)==null) {
            //destinationTextView0.setText("Card 0 is null");
        } else {
            if(cards.size()==3) {
                destinationTextView0.setText(cards.get(0).print());
                destinationTextView1.setText(cards.get(1).print());
                destinationTextView2.setText(cards.get(2).print());
            } else{
                destinationTextView0.setText("there are not 3 cards");
            }
        }
    }

    @Override
    public void displayError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show(); //TODO: remove toast
    }

    @Override
    public void changeView(IPollerExpressView view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.destination_fragment_container, (Fragment) view);
        fragmentTransaction.commit();
    }

}
