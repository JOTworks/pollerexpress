package com.thePollerServer.Model;

import com.shared.exceptions.NoCardToDrawException;
import com.shared.models.Color;
import com.shared.models.cardsHandsDecks.TrainCard;
import com.shared.models.cardsHandsDecks.VisibleCards;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class TrainCardDeck
{
    static private final int TRAINS = 12;//8;
    static private final int LOCOMOTIVES = 9;//10;
    Set<TrainCard> cards;
    List<TrainCard> deck;
    List<TrainCard> discard;
    public VisibleCards faceUpCards =  new VisibleCards();

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
        for(int i =0; i < 5; ++i)
        {
            faceUpCards.set(i, drawCard());
        }
    }

    public TrainCard drawCard() //throws NoCardToDrawException
    {
        if(deck.size() ==0)
        {
            deck.addAll(discard);
            discard = new LinkedList<>();
            Collections.shuffle(deck);
        }
        TrainCard drawn = null;
        try
        {

            drawn = deck.remove(0);
        }
        catch(Exception e)
        {
            return null;
        }
        return drawn;
    }
    public  TrainCard drawVisible(int index)
    {
        TrainCard faceUp = faceUpCards.get(index);
        if(faceUp == null) return null;
        TrainCard drew = drawCard();
        faceUpCards.set(index, drew);
        //check if the visible cards are good enough
        if(drew == null)
        {
            return faceUp;
        }

        int rainbowcount = 0;
        int allowed= 2;
        for(TrainCard card: faceUpCards.asArray())
        {
            if(card.getColor().equals(Color.TRAIN.RAINBOW))
            {
                rainbowcount+=1;
            }
        }
        if(rainbowcount >allowed)
        {
            //discard and draw.
            for(TrainCard card: faceUpCards.asArray())
            {
                discardCard(card);
            }
            for(int i =0; i < 5; ++i)
            {
                TrainCard card = drawCard();
                faceUpCards.set(i, card);//ideally we would continue to check, but i don't want to worry about infinite loops so we won't
            }
        }
        return faceUp;
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
            for(int i =0; i < 5; ++i)
            {
                TrainCard v = faceUpCards.get(i);
                if(v == null)
                {
                    faceUpCards.set(i, card);
                    return true;
                }
            }
            discard.add(card);
            return true;
        }
        return false;
    }

    public int size()
    {
        return this.deck.size();
    }

    public boolean drawsLeft()
    {
        if(deck.size() >0) return true;

        for(TrainCard v: faceUpCards.asArray())
        {
            if(v !=null && !v.getColor().equals(Color.TRAIN.RAINBOW))
            {
                return true;
            }
        }
        return false;
    }
}
