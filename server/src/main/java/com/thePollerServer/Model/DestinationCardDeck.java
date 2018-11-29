package com.thePollerServer.Model;

import com.shared.models.City;
import com.shared.models.Color;
import com.shared.models.Map;
import com.shared.models.cardsHandsDecks.DestinationCard;
import com.shared.models.cardsHandsDecks.TrainCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import sun.security.krb5.internal.crypto.Des;

public class DestinationCardDeck
{

    static private final int TRAINS = 12;
    static private final int LOCOMOTIVES = 10;
    Set<DestinationCard> cards;
    List<DestinationCard> deck;
    List<DestinationCard> discard;
    public DestinationCardDeck(Map map)
    {
        cards = new HashSet<>();
        List<City> cities = new ArrayList<>(map.getCities());
        Random rand = new Random();
        for(int i =0; i < 30; ++i)
        {
            int out = Math.floorMod(rand.nextInt(), cities.size());
            int next = Math.floorMod(rand.nextInt(), cities.size());

            while(next == out)
            {
                next = Math.floorMod(rand.nextInt(), cities.size());
            }
            //System.out.println(out);
            //System.out.println(next);
            City city1 =cities.get(out);
            City city2 = cities.get(next);
            cards.add(new DestinationCard( city1, city2,  map.getShortestDistanceBetweenCities(city1.getName(), city2.getName())));
        }
        //TODO create them all.
        deck = new LinkedList<>(cards);
        Collections.shuffle(deck);
        discard = new LinkedList<>();
    }

    public DestinationCard drawCard()
    {
        DestinationCard drawn= deck.remove(0);
        if(deck.size() ==0)
        {
            deck.addAll(discard);
            discard = new LinkedList<>();
            Collections.shuffle(deck);
        }
        return drawn;
    }

    /**
     * puts a card into the discard pile
     * @param card
     * @return true it was able to discard the card.
     */
    public boolean discardCard(DestinationCard card)
    {
        if(cards.contains(card))
        {
            discard.add(card);
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public int size()
    {
        return this.deck.size();
    }
}
