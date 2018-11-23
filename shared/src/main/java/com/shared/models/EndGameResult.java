package com.shared.models;

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
}
