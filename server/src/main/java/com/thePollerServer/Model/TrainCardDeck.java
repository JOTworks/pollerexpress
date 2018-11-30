package com.thePollerServer.Model;

import com.shared.exceptions.NoCardToDrawException;
import com.shared.models.Color;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TrainCardDeck
{
    static private final int TRAINS = 12;
    static private final int LOCOMOTIVES = 10;
    Set<TrainCard> cards;
    List<TrainCard> deck;
    List<TrainCard> discard;
    public TrainCardDeck()
    {
        cards = new HashSet<>();
        for(Color.TRAIN color: Color.TRAIN.values())
        {
            if(color.equals(Color.TRAIN.RAINBOW))
            {
                for(int i =0; i < LOCOMOTIVES; ++i)
                {
                    cards.add(new TrainCard(Color.TRAIN.RAINBOW));
                }
            }
            else
            {
                for(int i = 0; i < TRAINS; ++i)
                {
                    if (color != Color.TRAIN.BLANK)
                        cards.add(new TrainCard(color));
                }
            }
        }
        deck = new LinkedList<>(cards);
        Collections.shuffle(deck);
        discard = new LinkedList<>();
    }

    public TrainCard drawCard() //throws NoCardToDrawException
    {
        if(deck.size() ==0)
        {
            deck.addAll(discard);
            discard = new LinkedList<>();
            Collections.shuffle(deck);
        }

        TrainCard drawn= deck.remove(0);

        return drawn;
    }

    /**
     * puts a card into the discard pile
     * @param card
     * @return true it was able to discard the card.
     */
    public boolean discardCard(TrainCard card)
    {
        if(cards.contains(card))
        {
            discard.add(card);
            return true;
        }
        return false;
    }

    public int size()
    {
        return this.deck.size();
    }
}
