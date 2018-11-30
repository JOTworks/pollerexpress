package com.shared.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;

/**
 * Abby
 * This class represents the game history for a single game.
 * This class should be observed by the game history presenter.
 */
public class GameHistory extends Observable implements Serializable
{

    ArrayList<HistoryItem> historyItems;

    public GameHistory(ArrayList<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    public GameHistory() {
        historyItems = new ArrayList<>();
    }

    public ArrayList<HistoryItem> getHistoryItems() {
        return historyItems;
    }

    public void setHistoryItems(ArrayList<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    /**
     * Adds a history item to the list of history items,
     * sorts the items, and
     * alerts observers of the change.
     * @post a history item was added to the list
     * @post the observers of GameHistory were notified of the change
     * @param historyItem The history item to be added to the history
     */
    public void addHistoryItem(HistoryItem historyItem) {

        historyItems.add(historyItem);
        sortItems(historyItems);
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(historyItem);
        }
    }

    /**
     * This method sorts the history items by their timestamps,
     * putting the earlier items at the front of the list.
     * @pre historyItems is nonempty
     * @return a list of HistoryItem objects, sorted by their timestamps
     * @param historyItems
     */
    private ArrayList<HistoryItem> sortItems(ArrayList<HistoryItem> historyItems) {

        Comparator<HistoryItem> comparator = new Comparator<HistoryItem>() {

            /* Returns a value less than 0 if this item1's timestamp
            is before the timestamp of item2. Returns a value greater
            than zero if item1's timestamp is after the timestamp of item2.
            @pre the two HistoryItem objects do not have identical timestamps.*/
            @Override
            public int compare(HistoryItem item1, HistoryItem item2) {
                Timestamp thisTimestamp = item1.getTimestamp();
                Timestamp givenTimestamp = item2.getTimestamp();
                if(thisTimestamp.compareTo(givenTimestamp) < 0) {
                    return -1;
                }
                else return 1;
            }
        };

        Collections.sort(historyItems, comparator);

        return historyItems;
    }

    /**
     * returns history items as strings
     * @return An arraylist of sorted, formatted strings.
     */
    public ArrayList<String> getItemsAsStrings() {

        ArrayList<String> itemsAsStrings = new ArrayList<>();

        historyItems = sortItems(historyItems);

        for(int i = 0; i < historyItems.size(); i++) {

            HistoryItem item = historyItems.get(i);

            itemsAsStrings.add(item.toString());
        }

        return itemsAsStrings;
    }
}
