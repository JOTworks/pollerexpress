package com.shared.models;

import com.shared.exceptions.NotImplementedException;

import java.io.Serializable;
import java.util.List;

public class EndGameResult implements Serializable {
    private List<PlayerScore> playerScores;

    public List<PlayerScore> getPlayerScores() {
        return playerScores;
    }

    public void addScore(PlayerScore ps) {
        playerScores.add(ps);
    }

    public void addBonusPoints() {
        throw new NotImplementedException("GameService.addBonusPoints not implemented");
    }

    /**
     * Totals all of the points up for each player
     */
    public void totalPoints() {
        for (PlayerScore score : playerScores) {
            score.setTotalPoints();
        }
    }
}
