package com.shared.models;

import com.shared.exceptions.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class EndGameResult extends Observable implements Serializable {
    private static final Integer BONUS_POINTS = 10;
    private List<PlayerScore> playerScores;

    public List<PlayerScore> getPlayerScores() {
        return playerScores;
    }

    public EndGameResult() {
        playerScores = new ArrayList<>();
    }

    public void addScore(PlayerScore ps) {
        playerScores.add(ps);
    }

    /**
     * determines who should get the bonusPoints for longestRoute
     */
    public void addBonusPoints() {
        String bonusWinner = playerScores.get(0).getPlayerName();
        int longestRoute = playerScores.get(0).getLongestRouteScore();
        boolean isTie = true;
        // loop and check if a player has a longer set of connected routes than the previous player
        for (PlayerScore score : playerScores) {
            if (score.getLongestRouteScore() > longestRoute) {
                bonusWinner = score.getPlayerName();
                longestRoute = score.getLongestRouteScore();
                isTie = false;
            }
        }
        // award bonus points if there is not a tie
        if (!isTie) {
            for (PlayerScore score : playerScores) {
                if (score.getPlayerName().equals(bonusWinner)) {
                    score.setBonusPoints(BONUS_POINTS);
                }
            }
        }
    }

    public void sortByScore() {
        throw new NotImplementedException("EndGameResult.sortByScore");
    }

    /**
     * Totals all of the points up for each player
     */
    public void totalPoints() {
        for (PlayerScore score : playerScores) {
            score.setTotalPoints();
        }
    }

    public void setPlayerScores(List<PlayerScore> playerScores) {
        this.playerScores = playerScores;
        synchronized (this)
        {
            this.setChanged();
            notifyObservers(this);
        }
    }

}
