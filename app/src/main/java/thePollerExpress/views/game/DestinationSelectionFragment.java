package thePollerExpress.views.game;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shared.models.DestinationCard;

import java.util.List;

import cs340.pollerexpress.R;
import thePollerExpress.models.ClientData;
import thePollerExpress.presenters.game.DestinationSelectionPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IDestinationSelectionView;

public class DestinationSelectionFragment extends Fragment implements IDestinationSelectionView {

    DestinationSelectionPresenter destinationSelectionPresenter;
    TextView destinationTextView0;
    TextView destinationTextView1;
    TextView destinationTextView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_destination_selection, container, false);

        destinationTextView0 = (TextView)v.findViewById(R.id.destination_text_view_0);
        destinationTextView1 = (TextView)v.findViewById(R.id.destination_text_view_1);
        destinationTextView2 = (TextView)v.findViewById(R.id.destination_text_view_2);


        List<DestinationCard> destCards = ClientData.getInstance().getUser().getDestCardHand().getDestinationCards();
        renderCards(destCards);

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
            destinationTextView0.setText(cards.toString());
        }
    }

    @Override
    public void displayError(String errorMessage) {

    }

    @Override
    public void changeView(IPollerExpressView view) {

    }
}
