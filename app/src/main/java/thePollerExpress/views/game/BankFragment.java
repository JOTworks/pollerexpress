package thePollerExpress.views.game;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shared.models.cardsHandsDecks.TrainCard;

import cs340.pollerexpress.R;
import thePollerExpress.presenters.game.BankPresenter;
import thePollerExpress.presenters.game.interfaces.IBankPresenter;
import thePollerExpress.views.IPollerExpressView;
import thePollerExpress.views.game.interfaces.IBankView;

public class BankFragment extends Fragment implements IBankView
{

    Button trainCardDeck;
    Button destinationCardDeck;

    BankPresenter bankPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankPresenter = new BankPresenter(this);
    }
    private Button mVisible[];

   ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mVisible = new Button[5];//TODO remove magic
        View v = inflater.inflate(R.layout.fragment_bank, container, false);
        mVisible[0] = v.findViewById(R.id.bank_0);
        mVisible[1] = v.findViewById(R.id.bank_1);
        mVisible[2] = v.findViewById(R.id.bank_2);
        mVisible[3] = v.findViewById(R.id.bank_3);
        mVisible[4] = v.findViewById(R.id.bank_4);
        trainCardDeck = (Button) v.findViewById(R.id.train_card_deck);
        destinationCardDeck = (Button)  v.findViewById(R.id.destination_card_deck);



        Button mVisible0 = (Button) mVisible[0];
        mVisible0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawFaceupCard(0);

            }
        });
        Button mVisible1 = (Button) mVisible[1];
        mVisible1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawFaceupCard(1);

            }
        });
        Button mVisible2 = (Button) mVisible[2];
        mVisible2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawFaceupCard(2);

            }
        });
        Button mVisible3 = (Button) mVisible[3];
        mVisible3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawFaceupCard(3);

            }
        });
        Button mVisible4 = (Button) mVisible[4];
        mVisible4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawFaceupCard(4);

            }
        });

        Button TrainCardDeck = (Button) trainCardDeck;
        TrainCardDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawTrainCardFromDeck();

            }
        });
        Button DestinationCardDeck = (Button) destinationCardDeck;
        DestinationCardDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bankPresenter.drawDestinationCards();

            }
        });

        update();
        return v;
    }



    @Override
    public void update(int i, TrainCard card)
    {
        if(card == null)
        {
            mVisible[i].setClickable(false);
        }
        else
        {
            mVisible[i].setClickable(true);
            mVisible[i].setBackground(getFromCard(card));
        }
    }

    @Override
    public void update()
    {
        trainCardDeck.setText( String.format("%d", bankPresenter.getTrainDeckSize()) );
        destinationCardDeck.setText(String.format("%d", bankPresenter.getDestinationDeckSize()));
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

    public void displayError(String errorMessage) {
        android.widget.Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeView(IPollerExpressView view) {

    }

}
